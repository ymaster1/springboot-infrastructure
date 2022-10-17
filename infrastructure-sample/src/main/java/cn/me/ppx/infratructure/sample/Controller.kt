package cn.me.ppx.infratructure.sample

import cn.me.ppx.infrastructure.common.dto.BaseResponse
import cn.me.ppx.infrastructure.mybatis.model.PageInfoModel
import cn.me.ppx.infrastructure.web.framework.web.BaseController
import cn.me.ppx.infratructure.sample.entity.SysApp
import cn.me.ppx.infratructure.sample.entity.Test
import cn.me.ppx.infratructure.sample.service.AppService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author  ym
 * @date  2022/10/14 9:51
 *
 */

@RestController
class Controller : BaseController() {
    @Autowired
    private lateinit var appService: AppService

    @RequestMapping("/test1")
    fun test1(): BaseResponse<Test> {
        return build(appService.test1())
    }

    @RequestMapping("/test")
    fun test(): BaseResponse<SysApp> {
        return build(appService.test())
    }

    fun page(): BaseResponse<PageInfoModel<Test>> {
        return build(PageInfoModel.valueOf(listOf()))
    }
}