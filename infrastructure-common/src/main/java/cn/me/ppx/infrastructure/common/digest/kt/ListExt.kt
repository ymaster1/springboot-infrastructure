package cn.me.ppx.infrastructure.common.digest.kt

import kotlin.collections.ArrayList

fun <T> T.inList(): List<T> {
    return listOf(this)
}

fun <T> emptyMutableList(): MutableList<T> {
    return ArrayList()
}

fun <T> MutableList<T>.link(item: T): MutableList<T> {
    add(item)
    return this
}

fun <T> MutableList<T>.linkIf(validate: Boolean, item: T): MutableList<T> {
    if (validate) {
        add(item)
    }
    return this
}

fun <T> MutableList<T>.linkIfNotNull(validateObject: Any?, item: T): MutableList<T> {
    if (validateObject != null) {
        add(item)
    }
    return this
}

fun <T> MutableList<T>.linkIfNotNull(item: T): MutableList<T> {
    if (item != null) {
        add(item)
    }
    return this
}

fun <T> fillListBy(size: Int, defaultValue: T): List<T> {
    return (0 until size).toList().map { defaultValue }
}

fun <T> fillListBy(size: Int, defaultValue: (index: Int) -> T): List<T> {
    return (0 until size).toList().map { defaultValue(it) }
}