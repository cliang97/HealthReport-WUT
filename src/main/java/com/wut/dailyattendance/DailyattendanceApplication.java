package com.wut.dailyattendance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync //开启异步注解功能
@EnableScheduling //开启基于注解的定时任务
@SpringBootApplication
public class DailyattendanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DailyattendanceApplication.class, args);
    }

}
