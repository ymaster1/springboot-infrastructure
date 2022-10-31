package cn.me.ppx.infrastructure.common.digest.kt

import java.io.InputStream
import java.security.MessageDigest

fun String.md5(salt: String? = null): String = this.toByteArray().md5(salt?.encodeToByteArray())
fun String.sha1(salt: String? = null): String = this.toByteArray().sha1(salt?.encodeToByteArray())
fun String.sha256(salt: String? = null): String = this.toByteArray().sha256(salt?.encodeToByteArray())

fun InputStream.md5(salt: String? = null): String = readBytes().md5(salt?.encodeToByteArray())
fun InputStream.sha1(salt: String? = null): String = readBytes().sha1(salt?.encodeToByteArray())
fun InputStream.sha256(salt: String? = null): String = readBytes().sha256(salt?.encodeToByteArray())

/**
 * 32位小写md5
 */
fun ByteArray.md5(salt: ByteArray? = null): String = MessageDigest.getInstance("MD5")
    .applyIf(salt != null) { update(salt) }
    .digest(this)
    .joinToString("") { "%02x".format(it) }

fun ByteArray.sha1(salt: ByteArray? = null): String = MessageDigest.getInstance("SHA-1")
    .applyIf(salt != null) { update(salt) }
    .digest(this)
    .joinToString("") { "%02x".format(it) }

fun ByteArray.sha256(salt: ByteArray? = null): String = MessageDigest.getInstance("SHA-256")
    .applyIf(salt != null) { update(salt) }
    .digest(this)
    .joinToString("") { "%02x".format(it) }


