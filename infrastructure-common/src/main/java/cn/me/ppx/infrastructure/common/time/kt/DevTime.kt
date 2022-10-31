package cn.me.ppx.infrastructure.common.time.kt

import java.sql.Timestamp
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters

/**
 * time 可以是 秒 或者是 毫秒
 *
 * 但注意时间操作都会以 秒 为单位，比如 endOfDay 将返回这一天最后一秒000毫秒, 毫秒时间戳将为 1664553599000L
 */
class DevTime(time: Long) {

    private val timestamp: Long =
        if (time.toString().length > 10) time / 1000 else time

    private val milliSeconds: Int =
        if (time.toString().length > 10) (time % 1000).toInt() else 0

    companion object {
        const val DEFAULT_CHINA_OFFSET = 8
    }

    fun plusTime(time: Number, unit: ChronoUnit, offsetHours: Int = DEFAULT_CHINA_OFFSET): DevTime {
        return localDateTime(offsetHours).plus(time.toLong(), unit).devTime(offsetHours)
    }

    fun minusTime(time: Number, unit: ChronoUnit, offsetHours: Int = DEFAULT_CHINA_OFFSET): DevTime {
        return localDateTime(offsetHours).minus(time.toLong(), unit).devTime(offsetHours)
    }

    fun startOfDay(offsetHours: Int = DEFAULT_CHINA_OFFSET): DevTime {
        return localDateTime(offsetHours)
            .toLocalDate()
            .atStartOfDay()
            .devTime(offsetHours)
    }

    fun middleOfDay(offsetHours: Int = DEFAULT_CHINA_OFFSET): DevTime {
        return localDateTime(offsetHours)
            .toLocalDate()
            .atStartOfDay()
            .plusHours(12)
            .devTime(offsetHours)
    }

    fun endOfDay(offsetHours: Int = DEFAULT_CHINA_OFFSET): DevTime {
        return localDateTime(offsetHours)
            .toLocalDate()
            .plusDays(1)
            .atStartOfDay()
            .minusSeconds(1)
            .devTime(offsetHours)
    }

    fun startOfWeek(offsetHours: Int = DEFAULT_CHINA_OFFSET): DevTime {
        return localDateTime(offsetHours)
            .toLocalDate()
            .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
            .devTime(offsetHours)
    }

    fun endOfWeek(offsetHours: Int = DEFAULT_CHINA_OFFSET): DevTime {
        return localDateTime(offsetHours)
            .toLocalDate()
            .with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
            .devTime(offsetHours)
            .endOfDay()
    }

    fun startOfMonth(offsetHours: Int = DEFAULT_CHINA_OFFSET): DevTime {
        return localDateTime(offsetHours)
            .toLocalDate()
            .withDayOfMonth(1)
            .devTime(offsetHours)
    }

    fun endOfMonth(offsetHours: Int = DEFAULT_CHINA_OFFSET): DevTime {
        return localDateTime(offsetHours)
            .toLocalDate()
            .plusMonths(1)
            .withDayOfMonth(1)
            .devTime(offsetHours)
            .minusTime(1, ChronoUnit.SECONDS)
    }


    /**
     * 以下方法是输出
     */

    fun milliSeconds(): Long {
        return timestamp * 1000 + milliSeconds
    }

    fun seconds(): Long {
        return timestamp
    }

    fun timestamp(): Timestamp {
        return Timestamp.from(Instant.ofEpochSecond(seconds()))
    }

    fun localDateTime(offsetHours: Int = DEFAULT_CHINA_OFFSET): LocalDateTime {
        return LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.ofHours(offsetHours))
    }

    fun zonedDateTime(offsetHours: Int = DEFAULT_CHINA_OFFSET): ZonedDateTime {
        return ZonedDateTime.ofInstant(Instant.ofEpochSecond(seconds()), ZoneOffset.ofHours(offsetHours).normalized())
    }

    override fun hashCode(): Int {
        return this.milliSeconds().hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return if (other is DevTime) this.milliSeconds() == other.milliSeconds()
        else false
    }

    override fun toString(): String {
        return zonedDateTime().format(DateTimeFormatter.ISO_ZONED_DATE_TIME)
    }

    fun toString(formatter: DateTimeFormatter): String {
        return zonedDateTime().format(formatter)
    }

}