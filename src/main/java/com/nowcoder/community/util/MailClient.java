package com.nowcoder.community.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

// 加入 Spring 容器
@Component
public class MailClient {

    private static final Logger logger = LoggerFactory.getLogger(MailClient.class);

    // 邮件发送器
    @Autowired
    private JavaMailSender mailSender;

    // 发信人
    @Value("${spring.mail.username}")
    private String from;

    /**
     * 发送邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    public void sendMail(String to, String subject, String content) {
        // 创建 MimeMessage 对象
        MimeMessage message = mailSender.createMimeMessage();
        try {
            // 创建 MimeMessageHelper 对象(MimeMessage 帮助器)
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            // 设置发件人
            helper.setFrom(from);
            // 设置收件人
            helper.setTo(to);
            // 设置主题
            helper.setSubject(subject);
            // 设置内容
            helper.setText(content, true);
            // 发生信息
            mailSender.send(helper.getMimeMessage());
        } catch (MessagingException e) {
            // 打印错误日志
            logger.error("发送邮件失败：" + e.getMessage());
        }
    }
}
