package cn.me.ppx.infrastructure.common.math

import cn.me.ppx.infrastructure.common.digest.kt.cast
import kotlin.random.*

/**
 * 加盐随机
 * @param seed 随机数种子
 * @param salt 盐
 */
inline fun <reified T : Comparable<T>> saltRandom(seed: Int, salt: String): T {
    return when (T::class) {
        Int::class -> Random(seed + salt.hashCode()).nextInt()
        Long::class -> Random(seed + salt.hashCode()).nextLong()
        Float::class -> Random(seed + salt.hashCode()).nextFloat()
        Double::class -> Random(seed + salt.hashCode()).nextDouble()
        Boolean::class -> Random(seed + salt.hashCode()).nextBoolean()
        else -> throw IllegalArgumentException("Unsupported type: ${T::class}")
    }.cast()
}


/**
 * 加盐随机
 * @param seed 随机数种子
 * @param salt 盐
 * @param range 随机数范围
 */
inline fun <reified T : Comparable<T>> saltRandom(seed: Int, salt: String, range: ClosedRange<T>): T {
    return when (range) {
        is IntRange -> Random(seed + salt.hashCode()).nextInt(range) as T
        is LongRange -> Random(seed + salt.hashCode()).nextLong(range) as T
        is ClosedFloatingPointRange ->
            when (T::class) {
                Double::class -> Random(seed + salt.hashCode()).nextDouble(
                    range.start.cast(),
                    range.endInclusive.cast()
                ) as T
                Float::class -> Random(seed + salt.hashCode()).nextDouble(
                    range.start.cast<Float>().toDouble(),
                    range.endInclusive.cast<Float>().toDouble()
                ).toFloat() as T
                else -> throw IllegalArgumentException("Unsupported type: ${T::class}")
            }
        else -> throw IllegalArgumentException("Unsupported type: ${range::class}")
    }
}


/**
 * 加盐随机
 * @param seed 随机数种子
 * @param salt 盐
 * @param range 随机数范围
 */
inline fun <reified T : Comparable<T>> ClosedRange<T>.saltRandom(seed: Int, salt: String): T =
    saltRandom(seed, salt, this)

/**
 * 加盐随机
 * @param seed 随机数种子
 * @param salt 盐
 * @param list 随机对象List
 */
fun <T> saltRandom(seed: Int, salt: String, list: List<T>): T {
    return list[saltRandom(seed, salt, list.indices)]
}

/**
 * 加盐随机
 * @param seed 随机数种子
 * @param salt 盐
 * @param this 随机对象List
 */
fun <T> List<T>.saltRandom(seed: Int, salt: String): T = saltRandom(seed, salt, this)


/**
 * 加盐随机
 * @param seed 随机数种子
 * @param salt 盐
 * @param this 随机枚举
 */
inline fun <reified T : Enum<T>> T.saltRandom(seed: Int, salt: String): T =
    saltRandom(seed, salt, enumValues<T>().toList())


/**
 * 加盐加权随机
 * @param seed 随机数种子
 * @param salt 盐
 * @param keyWeights 随机对象 to 权重
 */
fun <T> saltRandomWeighted(seed: Int, salt: String, keyWeights: Map<T, Int>): T {
    val randomTarget = saltRandom(seed, salt, 0 until keyWeights.values.sum())
    val weightList = keyWeights.entries.toList()
    weightList.map { it.value }.reduceIndexed { index, acc, i ->
        val newAcc = acc + i
        if (acc > randomTarget) {
            return weightList[index - 1].key
        } else return@reduceIndexed newAcc
    }
    return weightList.last().key
}

/**
 * 加盐加权随机
 * @param seed 随机数种子
 * @param salt 盐
 * @param this 随机对象 to 权重
 */
fun <T> Map<T, Int>.saltRandomWeighted(seed: Int, salt: String): T = saltRandomWeighted(seed, salt, this)


/**
 * 加盐随机多个随机数
 * @param seed 随机数种子
 * @param salt 盐
 * @param range 随机数范围
 * @param count 结果随机数数量
 * @param repeatable 结果是否可重复
 */
fun saltRandomMultiple(seed: Int, salt: String, range: IntRange, count: Int, repeatable: Boolean = false): List<Int> {
    return if (repeatable) {
        (0 until count).map { saltRandom(seed, salt + it, range) }
    } else {
        val result = mutableSetOf<Int>()
        var it = 0
        while (result.size < count) {
            result += saltRandom(seed, salt + it, range)
            it++
        }
        result.toList()
    }
}


/**
 * 加盐随机多个结果
 * @param seed 随机数种子
 * @param salt 盐
 * @param list 随机对象List
 * @param count 结果随机数数量
 * @param repeatable 结果是否可重复
 */
fun <T> saltRandomMultiple(seed: Int, salt: String, list: List<T>, count: Int, repeatable: Boolean = false): List<T> {
    return if (repeatable) {
        (0 until count).map {
            saltRandom(seed, salt + it, list)
        }
    } else {
        val pool = list.toMutableList()
        (0 until count).map {
            pool.removeAt(saltRandom(seed, salt + it, 0 until pool.size))
        }
    }
}

/**
 * 加盐随机多个结果
 * @param seed 随机数种子
 * @param salt 盐
 * @param this 随机对象List
 * @param count 结果随机数数量
 * @param repeatable 结果是否可重复
 */
fun <T> List<T>.saltRandomMultiple(seed: Int, salt: String, count: Int, repeatable: Boolean = false): List<T> =
    saltRandomMultiple(seed, salt, this, count, repeatable)

/**
 * 加盐随机多个结果
 * @param seed 随机数种子
 * @param salt 盐
 * @param this 随机枚举
 * @param count 结果随机数数量
 * @param repeatable 结果是否可重复
 */
inline fun <reified T : Enum<T>> T.saltRandomMultiple(
    seed: Int,
    salt: String,
    count: Int,
    repeatable: Boolean = false,
): List<T> =
    saltRandomMultiple(seed, salt, enumValues<T>().toList(), count, repeatable)

/**
 * 加盐随机多个结果
 * @param seed 随机数种子
 * @param salt 盐
 * @param keyWeights 随机对象权重
 * @param count 结果随机数数量
 * @param repeatable 结果是否可重复
 */
fun <T> saltRandomWeightedMultiple(
    seed: Int,
    salt: String,
    keyWeights: Map<T, Int>,
    count: Int,
    repeatable: Boolean = false,
): List<T> {
    return if (repeatable) {
        (0 until count).map { saltRandomWeighted(seed, salt + it, keyWeights) }
    } else {
        val result = mutableListOf<T>()
        val iteratorKeyWeights = keyWeights.toMutableMap()
        repeat(count) {
            val one = saltRandomWeighted(seed, salt, iteratorKeyWeights)
            result += one
            iteratorKeyWeights.remove(one)
        }
        result
    }

}


/**
 * 加盐随机多个结果
 * @param seed 随机数种子
 * @param salt 盐
 *  [this] 随机对象权重
 * @param count 结果随机数数量
 * @param repeatable 结果是否可重复
 */
fun <T> Map<T, Int>.saltRandomWeightedMultiple(
    seed: Int,
    salt: String,
    count: Int,
    repeatable: Boolean = false,
): List<T> = saltRandomWeightedMultiple(seed, salt, this, count, repeatable)


/**
 * 是否满足概率
 * @param seed 种子
 * @param salt 盐
 * @param rate 需要满足的概率，0-1
 */
fun saltRandomHit(seed: Int, salt: String, rate: Double): Boolean {
    return saltRandom<Double>(seed, salt) < rate
}
