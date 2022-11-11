package cn.me.ppx.infrastructure.common.cache

/**
 * @author  ym
 * @date  2022/11/11 17:36
 *
 */
enum class LocalType(val code:String) {
    caffeine_local("caffeine_local"),
    caffeine_loading_local("caffeine_loading_local");
}