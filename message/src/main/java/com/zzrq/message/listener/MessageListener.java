package com.zzrq.message.listener;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.zzrq.message.properties.MessageProperties;
import com.zzrq.message.utils.MessageUtil;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;

@Component
@EnableConfigurationProperties(MessageProperties.class)
public class MessageListener {

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    private MessageProperties properties;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "zzrq.message.queue", durable = "true"),
            exchange = @Exchange(value = "zzrq.message.exchange",
                    ignoreDeclarationExceptions = "true"),
            key = {"message.verify.code"}))
    public void listenMessage(Map<String, String> msg) throws Exception {
        if (msg == null || msg.size() <= 0) {
            // 放弃处理
            return;
        }
        String phone = msg.get("phone");
        String code = msg.get("code");
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(code)) {
            // 放弃处理
            return;
        }
        // 发送消息
        SendSmsResponse resp = this.messageUtil.sendSms(phone, code,
                properties.getSignName(),
                properties.getVerifyCodeTemplate());
        // 发送失败
        throw new RuntimeException();
    }
}
