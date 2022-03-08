package com.wut.dailyattendance.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wut.dailyattendance.config.WeatherConfig;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;

/**
 * 根据城市编号可以获取天气信息
 * 加了一点自己的东西，用来发邮件，可以自己diy发邮件
 *
 * @author clds
 * @create 2022-03-07-下午7:00
 */
public class WeatherUtil {


    public JSONObject getWeather(String weatherKey, String weatherCity, String weatherExtensions) {

        StringBuilder urlPath = new StringBuilder("https://restapi.amap.com/v3/weather/weatherInfo?key=" + weatherKey + "&city=" + weatherCity + "&extensions=" + weatherExtensions);
        // 使用Restemplate来发送HTTP请求
        RestTemplate restTemplate = new RestTemplate();

        // 设置请求header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity(headers);

        try {
            // 使用 exchange 发送请求，以String的类型接收返回的数据
            // ps，我请求的数据，其返回是一个json
            String url = urlPath.toString();
            ResponseEntity<String> strbody = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
            // 解析返回的数据
            JSONObject jsTemp = JSONObject.parseObject(strbody.getBody());
            JSONArray forecasts = jsTemp.getJSONArray("forecasts");
            JSONObject tmp = forecasts.getJSONObject(0);
            JSONArray casts = tmp.getJSONArray("casts");
            JSONObject forecast = casts.getJSONObject(0);
            return forecast;

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public String getWeatherInfo(String city, String cityName) {
        WeatherUtil weatherUtil = new WeatherUtil();
        JSONObject forecast = weatherUtil.getWeather(WeatherConfig.key, city, WeatherConfig.extensions);
        StringBuilder weatherInformation = new StringBuilder();

        //晴雨
        String dayweather = forecast.getString("dayweather");
        String nightweather = forecast.getString("nightweather");

        if (dayweather.equals(nightweather)) {
            weatherInformation.append("今天" + cityName + "的天气是" + dayweather + ",");
        } else {
            weatherInformation.append("今天" + cityName + "的天气是" + dayweather + "转" + nightweather + ",");
        }

        //判断是否下雨
        boolean isRain = false;
        char[] dayweatherChars = dayweather.toCharArray();
        char[] nightweatherChars = nightweather.toCharArray();
        HashSet<Character> set1 = new HashSet<>();
        //白天天气
        for (char dayweatherChar : dayweatherChars) {

            if (set1.contains(dayweatherChar)) {
                isRain = true;
            }
            set1.add(dayweatherChar);
        }
        //晚上天气
        if (!isRain) {
            HashSet<Character> set2 = new HashSet<>();
            for (char nightweatherChar : nightweatherChars) {
                if (set2.contains(nightweatherChar)) {
                    isRain = true;
                }
                set2.add(nightweatherChar);
            }
        }
        if (isRain) {
            weatherInformation.append("雨天记得带伞。");
        }

        String daytemp = forecast.getString("daytemp");
        String nighttemp = forecast.getString("nighttemp");
        weatherInformation.append("温度是" + daytemp + "°C-" + nighttemp + "°C,");

        String date = forecast.getString("week");
        switch (date) {
            case "1":
                weatherInformation.append("新的一周加油哦！");
                break;
            case "2":
                weatherInformation.append("周二了，冲冲冲冲！");
                break;
            case "3":
                weatherInformation.append("周三了，革命尚未成功，同志仍需努力！");
                break;
            case "4":
                weatherInformation.append("周四啦，明天就要放假了！加油加油！");
                break;
            case "5":
                weatherInformation.append("最后一天，晚上放假啦！");
                break;
            case "6":
                weatherInformation.append("假期愉快！");
                break;
            case "7":
                weatherInformation.append("enjoy enjoy enjoy！");
                break;

        }
        return weatherInformation.toString();
    }
}
