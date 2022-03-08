package com.wut.dailyattendance;

import com.alibaba.fastjson.JSONObject;
import com.wut.dailyattendance.config.DailyattendanceConfig;
import com.wut.dailyattendance.config.WeatherConfig;
import com.wut.dailyattendance.utils.SendEmailUtil;
import com.wut.dailyattendance.utils.WeatherUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

/**
 * 主程序：定时任务
 *
 * @author clds
 * @create 2022-03-07-下午9:07
 */
@Component
public class ScheduledClock {

    @Autowired
    SendEmailUtil sendEmailUtil;

    //早上8点
    @Scheduled(cron = "0 0 8 * * ?")
    public void Clock1() {

        PostRequest postRequest = new PostRequest();
        //用户信息
        HashMap<String, String> map = new HashMap<>();
        map.put("sn", DailyattendanceConfig.sn);//值是身份证号
        map.put("idCard", DailyattendanceConfig.idCard);//值是身份证号后六位

        //随机一个体温填报的手机类型
        int random_index = (int) (Math.random() * (DailyattendanceConfig.HttpHeadersUserAgent.length));
        String userAgent = DailyattendanceConfig.HttpHeadersUserAgent[random_index];
        //获取sessionId
        String sessionId = postRequest.getSessionId(map, userAgent);
        //绑定sessionId
        postRequest.bindUserInfo(sessionId, map, userAgent);
        //体温填报
        //这个地址可以自己搞个随机字符串
        postRequest.monitorRegister(sessionId, "湖北省", "武汉市", "洪山区", "雄楚大道221号", userAgent);
        //获取解绑返回值，为发送邮件做准备
        JSONObject cancelBind = postRequest.cancelBind(sessionId, userAgent);

        int code = (int) cancelBind.get("code");

        //以下就是发送邮件了，属于拓展部分，可加可不加，有兴趣可以看看
        //获取天气数据
        WeatherUtil weatherUtil = new WeatherUtil();
        String weatherInfo = weatherUtil.getWeatherInfo(WeatherConfig.city2,WeatherConfig.cityName2);
        if (code == 0) {
            //可以自己diy
            sendEmailUtil.sendMailToBox(weatherInfo,"体温填报成功","体温填报成功啦,",
                                "88888888@qq.com","88888888@qq.com");
        } else {
            System.out.println("填报失败");
        }
    }


}
