package cn.me.ppx.infrastructure.rbac.config

/**
 * @author  ym
 * @date  2022/11/16 10:03
 *
 */
enum class AuthHeaderEnum(val code: String, val desc: String) {
    BACKEND("auth_backend", "后台认证"),
    FRONT("auth_token", "前台认证")
}