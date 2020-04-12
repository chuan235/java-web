package com.gc.java8;

import com.gc.baseutil.Person;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Joda-Time：一个可替换标准日期/时间处理且功能强大的JAVA API诞生，JAVA8中的Date-Time API在很大程度上，受到Joda-Time的影响，并且吸收了其中的精髓
 */
public class TimeJava8 {

    public static void main(String[] args) {

//        LocalDateTest();
//        LocalTimeTest();
//        localDateTimeTest();
//        zoneDateTimeTest();
//        clockTest();
        //durationTest();

//        UseStream.streamMapTest();
//        UseStream.filterTest();
//        UseStream.limitTest();

//        ReflectJava.getParams();

        BeanGroup.groupTest();
    }


    public static void optionalTest(Person person){
        Optional.ofNullable(person).map(person1 -> person1.getFirstName()).orElse("default FirstName");
    }



    public static void LocalDateTest() {
        // 当前日期 2019-10-25 增加/减少年月日
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate.toString());
        // 增加一天 2019-10-26
        localDate = localDate.plusDays(1);
        // 增加一月 2019-11-26
        localDate = localDate.plusMonths(1);
        System.out.println(localDate.toString());
        // 减少一月
        localDate = localDate.minusMonths(1);
        // 减少一天
        localDate = localDate.minusDays(1);
        // 当前日期 2019-10-25
        System.out.println(localDate);
    }

    public static void LocalTimeTest() {
        // 获取当前时间
        LocalTime time = LocalTime.now();
        System.out.println(time);
        // 增加毫秒 秒 分 时
        time = time.plusMinutes(5);
        time = time.plusHours(1);
        System.out.println(time);
        // 减少时分秒 毫秒
        time = time.minusHours(1);
        time = time.minusMinutes(5);
        System.out.println(time);
    }

    public static void localDateTimeTest() {
        // 获取当前时间   2019-10-25T22:51:48.462  UTC格式
        LocalDateTime dateTime = LocalDateTime.now();
        System.out.println(dateTime);
        // 格式化时间    2019-10-25 22:53:27
        System.out.println(dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    public static void zoneDateTimeTest() {
        // 2019-10-25T22:57:36.446+08:00[Asia/Shanghai]  指定特定的时区 ISO-8601格式
        final ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Shanghai"));
        System.out.println(zonedDateTime);
    }

    public static void clockTest() {
        // 通过指定一个时区，可以获取到当前的时刻、日期和时间
        // Clock可以替换System.currentTimeMillis()与TimeZone.getDefault()。
        // 世界标准时间、世界统一时间
        Clock utc = Clock.systemUTC();
        Clock shanghaiClock = Clock.system(ZoneId.of("Asia/Shanghai"));

        System.out.println(LocalDateTime.now(utc));
        System.out.println(LocalDateTime.now(shanghaiClock));
    }

    public static void durationTest() {
        // Duration类计算两个日期
        LocalDateTime from = LocalDateTime.parse("2018-12-17 18:50:50", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime to = LocalDateTime.parse("2018-12-19 21:10:50", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Duration duration = Duration.between(from, to);
        // 2
        System.out.println(duration.toDays());
        // 50
        System.out.println(duration.toHours());

    }
}
