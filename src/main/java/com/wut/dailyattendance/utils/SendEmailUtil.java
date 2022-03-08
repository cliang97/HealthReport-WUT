package com.wut.dailyattendance.utils;

import com.alibaba.fastjson.JSONObject;
import com.wut.dailyattendance.config.DailyattendanceConfig;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * 一个邮件任务
 *
 * @author clds
 * @create 2022-03-07-下午5:45
 */
@Component
public class SendEmailUtil {

    @Autowired
    JavaMailSenderImpl mailSender;

    public void sendMailToBox(String weatherInfo, String setSubject, String setText, String setTo, String setFrom) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject(setSubject);
            message.setText(setText + weatherInfo);
            message.setTo(setTo);
            message.setFrom(setFrom);
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("邮件发送失败");
        }

    }


}
