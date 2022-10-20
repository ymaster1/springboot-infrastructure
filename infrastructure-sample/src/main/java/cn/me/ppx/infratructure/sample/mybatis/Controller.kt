package cn.me.ppx.infratructure.sample.mybatis

import cn.me.ppx.infrastructure.common.dto.BaseResponse
import cn.me.ppx.infrastructure.mybatis.model.PageInfoModel
import cn.me.ppx.infrastructure.web.framework.web.BaseController
import cn.me.ppx.infratructure.sample.mybatis.entity.SysApp
import cn.me.ppx.infratructure.sample.mybatis.entity.Test
import cn.me.ppx.infratructure.sample.mybatis.service.AppService
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

    @GetMapping("/test")
    @ApiOperation("返回SysApp")
    fun test(): BaseResponse<SysApp> {
        return build(appService.test())
    }

    @GetMapping("/page")
    @ApiOperation("返回页")
    fun page(): BaseResponse<PageInfoModel<Test>> {
        return build(PageInfoModel.valueOf(listOf()))
    }
}