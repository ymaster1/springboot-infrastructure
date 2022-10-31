package cn.me.ppx.infrastructure.common.time.kt

import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField

fun devTime(): DevTime {
    return DevTime(System.currentTimeMillis())
}

/**
 * 时间戳转为 DevTime
 * 可以是 秒为单位，也可以是毫秒为单位.
 */
fun Long.devTime(): DevTime {
    return DevTime(this)
}

fun ZonedDateTime.devTime(): DevTime {
    return DevTime(this.toEpochSecond())
}

@Deprecated("避免使用 LocalDateTime，因为它是不带时区的本地时间，使用时也应该明确指定 时区偏移，这里默认 +8 中国", ReplaceWith("devTime()"), DeprecationLevel.WARNING)
fun LocalDateTime.devTime(offsetHours: Int = 8): DevTime {
    return DevTime(this.toEpochSecond(ZoneOffset.ofHours(offsetHours)))
}

@Deprecated("避免使用 LocalDateTime，因为它是不带时区的本地时间，使用时也应该明确指定 时区偏移，这里默认 +8 中国", ReplaceWith("devTime()"), DeprecationLevel.WARNING)
fun LocalDate.devTime(offsetHours: Int = 8): DevTime {
    return this.atStartOfDay().devTime(offsetHours)
}

fun Timestamp.devTime(): DevTime {
    return this.toLocalDateTime().devTime()
}

fun String.devTime(formatter: DateTimeFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME): DevTime {
    val supportOffset = formatter.parse(this)
    return if (supportOffset.isSupported(ChronoField.OFFSET_SECONDS)){
        ZonedDateTime.parse(this, formatter).devTime()
    }else{
        if (supportOffset.isSupported(ChronoField.HOUR_OF_DAY)){
            LocalDateTime.parse(this, formatter).devTime()
        }else {
            LocalDate.parse(this, formatter).devTime()
        }
    }
}