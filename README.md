# HealthReport-WUT
武汉理工大学健康填报

近期在空间看到一个同学发了一个用python写的体温填报的脚本，链接在[这](https://github.com/happy2h/Health-Report-WHUT)，恰逢自己在学Java，就想用Java语言也写一个

<h2>实现功能：</h2>
体温每日自动打卡+邮件+天气提醒
<p>1.健康日报每日早8点自动填报，
<p>2.发送一条邮件到你的邮箱里


<h2>使用教程</h2>
<h2>注意使用前请先取消小程序的关联</h2>
分两种类型
<h4>一、不用邮件任务</h4>
1.修改DailyattendanceConfig里面的sn和idCard属性，sn为身份证号，idCard为身份证后六位
<p>2.运行DailyattendanceApplicationTests的测试文件
<h4>二、所有功能</h4>
1.修改ScheduledClock的@Scheduled(cron = "0 0 8 * * ?")注解可以改定时的时间
2.修改ScheduledClock中的邮件的sendTo和sendFrom可以修改邮箱，sendFrom的邮箱需要申请一个授权码，
在application.properties里配置。

