package cn.me.ppx.mybatis

import cn.me.ppx.infrastructure.common.dto.BaseResponse
import cn.me.ppx.infrastructure.web.framework.web.BaseController
import cn.me.ppx.mybatis.entity.Test
import cn.me.ppx.mybatis.service.AppService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author  ym
 * @date  2022/10/14 9:51
 *
 */
@Api(tags = ["测试"])
@RestController
class Controller : BaseController() {
    @Autowired
    private lateinit var appService: AppService

    @GetMapping("/test1")
    @ApiOperation("返回Test")
    fun test1(): BaseResponse<Test> {
        return build(appService.test1())


    }
    @GetMapping("/test" )
    @ApiOperation("返回SysApp")
    fun testSysApp(): String {
        return "fuck"
    }
    @GetMapping("/void")
    @ApiOperation("返回null")
    fun testNull(): Unit {
    }

}