package cn.me.ppx.infrastructure.common.time.java;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

/**
 * @author ym
 * @date 2022/10/31 11:37
 */
public class DevTime {
    private long time;
    private static final int DEFAULT_CHINA_OFFSET = 8;

    private DevTime() {
    }

    public static DevTime of(long time) {
        DevTime devTime = new DevTime();
        devTime.setTime(time);
        return devTime;
    }
    public static DevTime now() {
        DevTime devTime = new DevTime();
        devTime.setTime(System.currentTimeMillis());
        return devTime;
    }
    public DevTime plusTime(long time, ChronoUnit unit) {
        LocalDateTime plus = localDateTime().plus(time, unit);
        return of(plus.toEpochSecond(ZoneOffset.ofHours(DEFAULT_CHINA_OFFSET)));
    }

    public DevTime minusTime(long time, ChronoUnit unit) {
        LocalDateTime plus = localDateTime().minus(time, unit);
        return of(plus.toEpochSecond(ZoneOffset.ofHours(DEFAULT_CHINA_OFFSET)));
    }

    public DevTime startOfDay() {
        LocalDateTime localDateTime = localDateTime()
                .toLocalDate()
                .atStartOfDay();
        return of(localDateTime.toEpochSecond(ZoneOffset.ofHours(DEFAULT_CHINA_OFFSET)));
    }

    public DevTime endOfDay() {
        return of(localDateTime()
                .toLocalDate()
                .plusDays(1)
                .atStartOfDay()
                .minusSeconds(1).toEpochSecond(ZoneOffset.ofHours(DEFAULT_CHINA_OFFSET)));
    }

    public DevTime startOfWeek() {
        LocalDateTime localDateTime = localDateTime()
                .toLocalDate()
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                .atStartOfDay();
        return of(localDateTime.toEpochSecond(ZoneOffset.ofHours(DEFAULT_CHINA_OFFSET)));

    }

    public DevTime endOfWeek() {
        LocalDateTime localDateTime = localDateTime()
                .toLocalDate()
                .with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).atStartOfDay();
        return of(localDateTime.toEpochSecond(ZoneOffset.ofHours(DEFAULT_CHINA_OFFSET))).endOfDay();

    }

    public DevTime startOfMonth() {
        LocalDateTime localDateTime = localDateTime()
                .toLocalDate()
                .withDayOfMonth(1).atStartOfDay();
        return of(localDateTime.toEpochSecond(ZoneOffset.ofHours(DEFAULT_CHINA_OFFSET)));

    }

    public DevTime endOfMonth() {
        LocalDateTime localDateTime = localDateTime()
                .toLocalDate()
                .plusMonths(1)
                .withDayOfMonth(1)
                .atStartOfDay();
        return of(localDateTime.toEpochSecond(ZoneOffset.ofHours(DEFAULT_CHINA_OFFSET))).minusTime(1, ChronoUnit.SECONDS);

    }

    public ZonedDateTime zonedDateTime() {
        return ZonedDateTime.ofInstant(Instant.ofEpochSecond(seconds()), ZoneOffset.ofHours(DEFAULT_CHINA_OFFSET).normalized());
    }

    public LocalDateTime localDateTime() {
        return LocalDateTime.ofEpochSecond(seconds(), 0, ZoneOffset.ofHours(DEFAULT_CHINA_OFFSET));
    }


    public String toString(DateTimeFormatter formatter) {
        return zonedDateTime().format(formatter);
    }


    @Override
    public String toString() {
        return zonedDateTime().format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }


    public void setTime(long time) {
        this.time = time;
    }

    /**
     * 获取时间戳（秒）
     *
     * @return
     */
    public long seconds() {
        if (String.valueOf(time).length() > 10) {
            return time / 1000;
        }
        return time;
    }

    /**
     * 获取时间戳（毫秒）
     *
     * @return
     */
    public long milliSeconds() {
        if (String.valueOf(time).length() > 10) {
            return time % 1000 + seconds() * 1000;
        }
        return 0;

    }
}
