package com.zzrq.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.zzrq.base.utils.NumberUtil;
import com.zzrq.base.utils.RSAUtil;
import com.zzrq.user.dto.User;
import com.zzrq.user.mapper.UserMapper;
import com.zzrq.user.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${user.publicKey}")
    private String publicKey;

    @Value("${user.privateKey}")
    private String privateKey;

    private static final String KEY_PREFIX = "user:code:phone:";

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Boolean checkData(String data, Integer type) {
        User user = new User();
        switch (type) {
            case 1:
                user.setName(data);
                break;
            case 2:
                user.setEmail(data);
                break;
            case 3:
                user.setPhone(data);
                break;
            default:
                user.setName(data);
        }
        int count = userMapper.selectCount(user);
        return count != 0;
    }

    public Boolean sendVerifyCode(String phone) {
        // 生成验证码
        String code = NumberUtil.generateCode(6);
        try {
            // 发送短信
            Map<String, String> msg = new HashMap<>();
            msg.put("phone", phone);
            msg.put("code", code);
            this.amqpTemplate.convertAndSend("zzrq.message.exchange", "message.verify.code", msg);
            // 将code存入redis
            this.redisTemplate.opsForValue().set(KEY_PREFIX + phone, code, 5, TimeUnit.MINUTES);
            return true;
        } catch (Exception e) {
            logger.error("发送短信失败。phone：{}， code：{}", phone, code);
            return false;
        }
    }

    @Override
    public Boolean register(User user, String code) {
        String key = KEY_PREFIX + user.getPhone();
        // 从redis取出验证码
        String codeCache = this.redisTemplate.opsForValue().get(key);
        // 检查验证码是否正确
        if (!code.equals(codeCache)) {
            // 不正确，返回
            return false;
        }
        user.setId(null);
        user.setCreateDate(new Date());
        user.setLastUpdateDate(new Date());

        try {
            user.setPassword(RSAUtil.encrypt(user.getPassword(), publicKey));
        } catch (Exception e) {
            logger.error("密码加密失败，password：{}", user.getPassword(), e);
            return false;
        }
        // 写入数据库
        boolean boo = this.userMapper.insertSelective(user) == 1;

        // 如果注册成功，删除redis中的code
        if (boo) {
            try {
                this.redisTemplate.delete(key);
            } catch (Exception e) {
                logger.error("删除缓存验证码失败，code：{}", code, e);
            }
        }
        return boo;
    }

    @Override
    public String checkPassword(User user) {
        String msg = "";
        if (StringUtils.isEmpty(user.getName()) && StringUtils.isEmpty(user.getPhone())) {
            logger.error("用户名或手机号为空");
            msg += "用户名或手机号为空";
        } else {

            User queryUser = new User();
            queryUser.setName(user.getName());
            queryUser.setPhone(user.getPhone());
            queryUser = this.userMapper.selectOne(queryUser);
            if(queryUser != null) {
                if (StringUtils.isEmpty(user.getPassword())) {
                    logger.error("密码为空，name：{}", user.getName());
                    msg += "密码为空";
                } else {
                    if (!StringUtils.isEmpty(queryUser.getPassword())) {
                        try {
                            if (!user.getPassword().equals(RSAUtil.decrypt(queryUser.getPassword(), privateKey))) {
                                msg += "账号或密码错误";
                            }
                        } catch (Exception e) {
                            logger.error("密码解密失败，name：{}", queryUser.getName(), e);
                            msg += "密码解密失败，name：" + queryUser.getName();
                            return msg;
                        }
                    }
                }

            } else {
                msg += "账号或密码错误";
            }
        }
        return msg;
    }

    @Override
    public String changPassword(User user) {
        String msg = this.checkPassword(user);
        if (StringUtils.isEmpty(msg)) {
            User queryUser = this.userMapper.selectOne(user);
            try {
                user.setPassword(RSAUtil.encrypt(user.getNewPassword(), publicKey));
            } catch (Exception e) {
                logger.error("密码加密失败，password：{}", user.getPassword(), e);
                msg += "密码加密失败";
            }
            user.setLastUpdateDate(new Date());
            userMapper.updateByPrimaryKeySelective(queryUser);
        }
        return msg;
    }

    @Override
    public List<User> queryUser(String data, int page, int pageSize) {
        PageHelper.startPage(page,pageSize);
        Example example = new Example(User.class);
        if(!StringUtils.isEmpty(data)) {
            data = "%" + data + "%";
            example.createCriteria().orLike("name", data)
                    .orLike("phone", data);
        }
        return this.userMapper.selectByExample(example);
    }

    @Override
    public String checkVerifyCode(String phone, String code) {
        String msg = "";
        String key = KEY_PREFIX + phone;
        // 从redis取出验证码
        String codeCache = this.redisTemplate.opsForValue().get(key);
        if(StringUtils.isEmpty(codeCache)) {
            msg += "验证码未生成或者已过期";
        } else {
            // 检查验证码是否正确
            if (!code.equals(codeCache)) {
                // 不正确，返回
                msg += "验证码错误";
            }
        }

        return msg;
    }
}
