@file:OptIn(ExperimentalContracts::class)

package cn.me.ppx.infrastructure.common.digest.kt

import kotlin.concurrent.thread
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract


/**
 * ============================== ApplyIf =====================================
 */
inline fun <T> T.applyIf(validate: Boolean, block: T.() -> Unit): T {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }
    if (validate) {
        block()
    }
    return this
}

inline fun <T> T.applyIf(validate: T.() -> Boolean, block: T.() -> Unit): T {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }
    if (validate()) {
        block()
    }
    return this
}

inline fun <T> T.applyIfNotNull(block: T.() -> Unit): T {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }
    if (this != null) {
        block()
    }
    return this
}

/**
 * ============================== ApplyWith =====================================
 */
inline fun <T, E> T.applyWith(receiver: E, block: T.(E) -> Unit): T {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    block(receiver)
    return this
}


inline fun <T, E> T.applyWithNotNull(receiver: E?, block: T.(E) -> Unit): T {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    if (receiver != null) {
        block(receiver)
    }
    return this
}

/**
 * ============================== letIf =====================================
 */


inline fun <T> T.letIf(validate: Boolean, block: T.() -> T): T {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }
    if (validate) {
        return block()
    }
    return this
}

inline fun <T> T.letIf(validate: T.() -> Boolean, block: T.() -> T): T {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }
    if (validate()) {
        return block()
    }
    return this
}

inline fun <T> T.letIfNotNull(block: T.() -> T): T {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }
    if (this != null) {
        return block()
    }
    return this
}

/**
 * ============================== letWith =====================================
 */
inline fun <T, E, R> T.letWith(receiver: E, block: T.(E) -> R): R {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    return block(receiver)
}


/**
 * ============================== withIf =====================================
 */

inline fun <T> withIf(validate: Boolean, receiver: T, block: T.() -> Unit) {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }
    if (validate) {
        block(receiver)
    }
}


inline fun <T> withIfNotNull(receiver: T?, block: T.() -> Unit) {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }
    if (receiver != null) {
        block(receiver)
    }
}

/**
 * ============================== cast =====================================
 */

fun <T> Any.cast(): T {
    return this as T
}

inline fun <reified T> Any?.castOrNull(): T? {
    return if (this is T) return this
    else this as? T
}


/**
 * ============================== into =====================================
 */

inline fun <T, R> T.into(mapper: ((T) -> R)): R {
    contract {
        callsInPlace(mapper, InvocationKind.EXACTLY_ONCE)
    }
    return mapper(this)
}