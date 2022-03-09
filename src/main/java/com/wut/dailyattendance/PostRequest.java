package com.wut.dailyattendance;

import com.alibaba.fastjson.JSONObject;
import com.wut.dailyattendance.config.DailyattendanceConfig;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * @author clds
 * @create 2022-03-07-下午12:58
 */
public class PostRequest {

    /**
     * 根据自己的身份证号和密码获取SessionId
     * @param jsonObject
     * @param userAgent
     * @return
     */
    public String getSessionId(HashMap jsonObject,String userAgent) {

        StringBuilder urlPath = new StringBuilder(DailyattendanceConfig.getSessionIdPath);
        // 使用Restemplate来发送HTTP请求
        RestTemplate restTemplate = new RestTemplate();

        // 设置请求header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate, br");
        headers.set(HttpHeaders.REFERER, "https://servicewechat.com/wx225eb50c34f6f98e/13/page-frame.html");
        headers.set("X-Tag", "flyio");//存疑
//        headers.set(HttpHeaders.CONTENT_LENGTH, "100");
//        headers.set(HttpHeaders.ACCEPT_LANGUAGE, "zh-cn");
        headers.set(HttpHeaders.CONNECTION, "keep-alive");
        headers.set(HttpHeaders.HOST, "yjsxx.whut.edu.cn");
        headers.set(HttpHeaders.USER_AGENT, userAgent);

        HttpEntity httpEntity = new HttpEntity(jsonObject, headers);

        try {
            // 使用 exchange 发送请求，以String的类型接收返回的数据
            // ps，我请求的数据，其返回是一个json
            String url = urlPath.toString();
            ResponseEntity<String> strbody = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
            // 解析返回的数据
            JSONObject jsTemp = JSONObject.parseObject(strbody.getBody());
            System.out.println("getSessionId"+jsTemp);
            JSONObject data = jsTemp.getJSONObject("data");
            String sessionId = data.getString("sessionId");
            return sessionId;

        } catch (Exception e) {
            System.out.println(e);
        }

        return "";
    }

    /**
     * 绑定用户信息
     * @param sessionId
     * @param jsonObject
     * @param userAgent
     */
    public void bindUserInfo(String sessionId, HashMap jsonObject,String userAgent) {
        // 使用Restemplate来发送HTTP请求
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder urlPath = new StringBuilder(DailyattendanceConfig.bindUserInfoPath);

        // 设置请求header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate, br");
        headers.set(HttpHeaders.REFERER, "https://servicewechat.com/wx225eb50c34f6f98e/13/page-frame.html");
        headers.set("X-Tag", "flyio");//存疑
//        headers.set(HttpHeaders.CONTENT_LENGTH, "100");
//        headers.set(HttpHeaders.ACCEPT_LANGUAGE, "zh-cn");
        headers.set(HttpHeaders.CONNECTION, "keep-alive");
        headers.set(HttpHeaders.HOST, "yjsxx.whut.edu.cn");
        headers.set(HttpHeaders.USER_AGENT, userAgent);
        headers.set(HttpHeaders.COOKIE, "JSESSIONID=" + sessionId);

        HttpEntity httpEntity = new HttpEntity(jsonObject, headers);

        try {
            // 使用 exchange 发送请求，以String的类型接收返回的数据
            // ps，我请求的数据，其返回是一个json
            String url = urlPath.toString();
            ResponseEntity<String> strbody = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
            // 解析返回的数据
            JSONObject jsTemp = JSONObject.parseObject(strbody.getBody());
            System.out.println("bindUserInfo"+jsTemp);


        } catch (Exception e) {
            System.out.println(e);
        }

    }

    /**
     * 填报体温
     * @param sessionId
     * @param province
     * @param city
     * @param country
     * @param street
     * @param userAgent
     */
    public void monitorRegister(String sessionId, String province, String city, String country, String street,String userAgent) {
        String currentAddress = province + city + country + street;
        // 使用Restemplate来发送HTTP请求
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder urlPath = new StringBuilder(DailyattendanceConfig.monitorRegisterPath);

        // 设置请求header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate, br");
        headers.set(HttpHeaders.REFERER, "https://servicewechat.com/wx225eb50c34f6f98e/13/page-frame.html");
        headers.set("X-Tag", "flyio");//存疑
        headers.set(HttpHeaders.CONTENT_LENGTH, "100");
        headers.set(HttpHeaders.ACCEPT_LANGUAGE, "zh-cn");
        headers.set(HttpHeaders.CONNECTION, "keep-alive");
        headers.set(HttpHeaders.HOST, "yjsxx.whut.edu.cn");
        headers.set(HttpHeaders.USER_AGENT, userAgent);
        headers.set(HttpHeaders.COOKIE, "JSESSIONID=" + sessionId);

//        // 请求体，包括请求数据 body 和 请求头 headers
        HashMap<String, String> body = new HashMap<>();
        body.put("diagnosisName", "");
        body.put("relationWithOwn", "");
        body.put("currentAddress", currentAddress);
        body.put("remark", "");
        body.put("healthInfo", "正常");
        body.put("isDiagnosis", "0");
        body.put("isFever", "0");
        body.put("isInSchool", "1");
        body.put("isLeaveChengdu", "0");
        body.put("isSymptom", "0");
        body.put("temperature", DailyattendanceConfig.temperatures[(int) (Math.random() * (DailyattendanceConfig.temperatures.length))]);
        body.put("noonTemperature", DailyattendanceConfig.temperatures[(int) (Math.random() * (DailyattendanceConfig.temperatures.length))]);
        body.put("eveningTemperature", DailyattendanceConfig.temperatures[(int) (Math.random() * (DailyattendanceConfig.temperatures.length))]);
        body.put("province", province);
        body.put("city", city);
        body.put("county", country);//注意这个国家的键是county
        HttpEntity httpEntity = new HttpEntity(body, headers);

        try {
            // 使用 exchange 发送请求，以String的类型接收返回的数据
            // ps，我请求的数据，其返回是一个json
            String url = urlPath.toString();
            ResponseEntity<String> strbody = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
            // 解析返回的数据
            JSONObject jsTemp = JSONObject.parseObject(strbody.getBody());
            System.out.println("monitorRegister"+jsTemp);


        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * 解绑
     * @param sessionId
     * @param userAgent
     */
    public JSONObject cancelBind(String sessionId,String userAgent) {
        StringBuilder urlPath = new StringBuilder(DailyattendanceConfig.cancelBindPath);
        // 使用RestTemplate来发送HTTP请求
        RestTemplate restTemplate = new RestTemplate();

        // 设置请求header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate, br");
        headers.set(HttpHeaders.REFERER, "https://servicewechat.com/wx225eb50c34f6f98e/13/page-frame.html");
        headers.set("X-Tag", "flyio");//存疑
        headers.set(HttpHeaders.CONTENT_LENGTH, "100");
        headers.set(HttpHeaders.ACCEPT_LANGUAGE, "zh-cn");
        headers.set(HttpHeaders.CONNECTION, "keep-alive");
        headers.set(HttpHeaders.HOST, "yjsxx.whut.edu.cn");
        headers.set(HttpHeaders.COOKIE, "JSESSIONID=" + sessionId);
        headers.set(HttpHeaders.USER_AGENT, userAgent);

        HttpEntity httpEntity = new HttpEntity(headers);

        try {
            // 使用 exchange 发送请求，以String的类型接收返回的数据
            // ps，我请求的数据，其返回是一个json
            String url = urlPath.toString();
            ResponseEntity<String> strbody = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
            // 解析返回的数据
            JSONObject jsTemp = JSONObject.parseObject(strbody.getBody());
            System.out.println("cancelBind"+jsTemp);
            return jsTemp;

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }


}
