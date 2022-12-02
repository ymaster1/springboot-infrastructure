package cn.me.ppx.infrastructure.common.leetcode.arr.kt

import java.util.*

/**
 * @author  ym
 * @date  2022/11/29 11:39
 *
 */
object Q_1 {
    fun sum(arr: Array<Int>, target: Int): Array<Int> {
        val map: MutableMap<Int, Int> = hashMapOf()
        for (i in 0..arr.size) {
            if (map.containsKey(target - arr[i])) {
                return arrayOf(map[target - arr[i]]!!,i)
            }
            map[arr[i]] = i
        }
        return arrayOf()
    }
}

fun main() {
    println(Q_1.sum(arrayOf(2, 7, 11, 15), 22).contentToString())
}