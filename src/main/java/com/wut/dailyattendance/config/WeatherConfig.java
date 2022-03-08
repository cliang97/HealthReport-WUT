package com.wut.dailyattendance.config;

/**
 * 天气配置类，可以自己申请一个高德天气api玩玩
 * @author clds
 * @create 2022-03-07-下午7:04
 */
public class WeatherConfig {


    //高德地图api授权
    public static final String key = "831cef746bdde5f8516c8088fe4692bc";

    //城市编号
    public static final String city1 = "340104";
    public static final String city2 = "420111";

    public static final String cityName1 = "合肥";
    public static final String cityName2 = "武汉";

    //base:返回实况天气 all:返回预报天气
    public static final String extensions = "all";


}
