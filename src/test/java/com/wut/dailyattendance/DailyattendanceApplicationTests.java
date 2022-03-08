package com.wut.dailyattendance;

import com.alibaba.fastjson.JSONObject;
import com.wut.dailyattendance.config.DailyattendanceConfig;
import com.wut.dailyattendance.utils.SendEmailUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@SpringBootTest
class DailyattendanceApplicationTests {


    @Autowired
    SendEmailUtil sendEmailUtil;

    @Test
    void contextLoads() {
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
        postRequest.monitorRegister(sessionId, "湖北省", "武汉市", "洪山区", "雄楚大道221号", userAgent);
        //获取解绑返回值，为发送邮件做准备
        JSONObject cancelBind = postRequest.cancelBind(sessionId, userAgent);
        System.out.println(cancelBind);
    }


}
