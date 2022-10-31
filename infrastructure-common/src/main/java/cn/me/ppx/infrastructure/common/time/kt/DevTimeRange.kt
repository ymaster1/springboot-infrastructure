package cn.me.ppx.infrastructure.common.time.kt

import java.time.temporal.ChronoUnit


class DevTimeRange(private val before: Long, private val after: Long) {

    fun toIntervalString(): String {
        return "${before},${after}"
    }

}

fun DevTime.rangeTo(time: DevTime): DevTimeRange {
    return if (time.seconds() > seconds())
        DevTimeRange(seconds(), time.seconds())
    else
        DevTimeRange(time.seconds(), seconds())
}

fun DevTime.rangeToStartOfDay(): DevTimeRange {
    return rangeTo(startOfDay())
}

fun DevTime.rangeToDaysBefore(days: Int): DevTimeRange {
    return rangeTo(minusTime(days, ChronoUnit.DAYS))
}

fun DevTime.rangeToMonthBefore(month: Int): DevTimeRange {
    return rangeTo(minusTime(month, ChronoUnit.MONTHS))
}

fun DevTime.rangeToYearsBefore(years: Int): DevTimeRange {
    return rangeTo(minusTime(years, ChronoUnit.YEARS))
}

