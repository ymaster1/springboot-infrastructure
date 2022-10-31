package cn.me.ppx.infrastructure.common.digest.kt

fun StringBuilder.appendFixed(content: String, maxLength: Int, holder: Char = ' '): StringBuilder {
    append(content).applyIf(content.length < maxLength) {
        for (i in content.length until maxLength) {
            append(holder)
        }
    }
    return this
}