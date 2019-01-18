package com.zzrq.user.controllers;

import com.zzrq.base.dto.BaseDto;
import com.zzrq.base.dto.ResponseData;
import com.zzrq.user.dto.User;
import com.zzrq.user.service.IUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    /**
     * 查询用户
     * @param data
     * @return
     */
    @ApiOperation(value = "查询用户", notes = "根据用户名或手机号查询用户")
    @ApiImplicitParams({@ApiImplicitParam(name = "data", value = "用户名或手机号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "显示个数", required = true, dataType = "int"),})
    @GetMapping("get/user")
    public ResponseData queryUser(String data, int pageNum, int pageSize) {
        List<User> users = this.userService.queryUser(data,pageNum,pageSize);
        return new ResponseData(users);
    }

    /**
     * 校验用户是否存在
     * @param data
     * @param type
     * @return
     */
    @ApiOperation(value = "校验用户是否存在", notes = "根据用户名，email，手机号校验用户是否存在")
    @ApiImplicitParams({@ApiImplicitParam(name = "data", value = "用户名，email，手机号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "type", value = "类型，1为用户名，2为email，3为手机号", required = true, dataType = "int")})
    @GetMapping("check/user/{data}/{type}")
    public ResponseData checkUserData(@PathVariable("data") String data, @PathVariable(value = "type") int type) {
        Boolean boo = this.userService.checkData(data, type);
        return new ResponseData(boo);
    }

    /**
     * 校验用户密码是否正确
     * @param user
     * @return
     */
    @ApiOperation(value = "校验用户密码是否正确", notes = "根据用户名，手机号校验用户密码是否正确")
    @ApiImplicitParams({@ApiImplicitParam(name = "user", value = "用户dto", required = true, dataType = "User")})
    @PostMapping("/check/password")
    public ResponseData checkUserPassword(User user) {
        String msg = this.userService.checkPassword(user);
        return new ResponseData(msg);
    }

    /**
     * 修改密码
     * @param user
     * @return
     */
    @ApiOperation(value = "修改密码", notes = "根据用户名，手机号校验用户密码是否正确，然后修改修改密码")
    @ApiImplicitParams({@ApiImplicitParam(name = "user", value = "用户dto", required = true, dataType = "User")})
    @PutMapping("/change/password")
    public ResponseData changPassword(User user) {
        String msg = this.userService.changPassword(user);
        return new ResponseData(msg);
    }

    /**
     * 发送手机验证码
     * @param phone
     * @return
     */
    @ApiOperation(value = "发送手机验证码", notes = "发送手机验证码，只保存五分钟")
    @ApiImplicitParams({@ApiImplicitParam(name = "phone", value = "手机号", required = true, dataType = "String")})
    @PostMapping("/code")
    public ResponseData sendVerifyCode(String phone) {
        Boolean boo = this.userService.sendVerifyCode(phone);
        return new ResponseData(boo);
    }

    /**
     * 注册
     * @param user
     * @param code
     * @return
     */
    @ApiOperation(value = "注册", notes = "注册用户")
    @ApiImplicitParams({@ApiImplicitParam(name = "user", value = "用户dto", required = true, dataType = "User"),
            @ApiImplicitParam(name = "code", value = "验证码", required = true, dataType = "String")})
    @PostMapping("/register")
    public ResponseData register(@Valid User user, @RequestParam("code") String code) {
        Boolean boo = this.userService.register(user, code);
        return new ResponseData(boo);
    }

    /**
     * 检查校验码
     * @param phone
     * @param code
     * @return
     */
    @ApiOperation(value = "检查校验码", notes = "检查校验码")
    @ApiImplicitParams({@ApiImplicitParam(name = "phone", value = "手机号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "code", value = "验证码", required = true, dataType = "String")})
    @GetMapping("check/code/{phone}/{code}")
    public ResponseData checkVerifyCode(@PathVariable("phone") String phone, @PathVariable(value = "code") String code) {
        String msg = this.userService.checkVerifyCode(phone, code);

        new BaseDto();
        return new ResponseData(msg);
    }
}
