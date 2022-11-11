package cn.me.ppx.infrastructure.common.math

import java.math.BigDecimal
import java.math.BigInteger

fun <T : Number> T.toBigDecimal(): BigDecimal {
    return when (this) {
        is Double -> BigDecimal(this)
        is Int -> BigDecimal(this)
        is Long -> BigDecimal(this)
        is Float -> BigDecimal(this.toDouble())
        is BigInteger -> BigDecimal(this)
        is Short -> BigDecimal(this.toInt())
        is Byte -> BigDecimal(this.toInt())
        is BigDecimal -> this
        else -> {
            throw RuntimeException("Unknown Number Type ${this::class}")
        }
    }
}

fun String.toBigDecimal(): BigDecimal {
    return BigDecimal(this)
}

