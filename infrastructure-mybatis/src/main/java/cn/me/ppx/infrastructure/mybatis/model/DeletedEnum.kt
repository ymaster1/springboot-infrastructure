package com.ovo.platform.mybatis.model

/**
 * @author  ym
 * @date  2022/10/14 16:23
 *
 */
enum class DeletedEnum(val code:Byte,val desc:String) {
    TRUE(1,"已删除"),
    FALSE(0,"未删除")
}