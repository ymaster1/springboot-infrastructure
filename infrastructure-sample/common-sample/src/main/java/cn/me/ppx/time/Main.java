package cn.me.ppx.time;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @author ym
 * @date 2022/10/31 10:36
 */
public class Main {
    public static void main(String[] args) {
        Date date = new Date();
        DateTime dateTime = DateUtil.date(date);
        LocalDateTime localDateTime = dateTime.toLocalDateTime();
        System.out.println(localDateTime);

        System.out.println(LocalDateTime.now());
        LocalDateTime offset = LocalDateTimeUtil.offset(localDateTime, -1, ChronoUnit.DAYS);
        LocalDateTime time = offset.withHour(0).withMinute(0)
                .withSecond(0).withNano(0);
        Date yesterday = Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
        System.out.println(yesterday);
        System.out.println(DateUtil.yesterday());
    }
}
