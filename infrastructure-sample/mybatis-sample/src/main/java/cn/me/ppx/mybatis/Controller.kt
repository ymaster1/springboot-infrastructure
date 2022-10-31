//package cn.me.ppx.mybatis
//
//import cn.me.ppx.infrastructure.common.dto.BaseResponse
//import cn.me.ppx.infrastructure.mybatis.model.PageInfoModel
//import cn.me.ppx.mybatis.entity.Test
//import org.springframework.web.bind.annotation.GetMapping
//import org.springframework.web.bind.annotation.RestController
//
///**
// * @author  ym
// * @date  2022/10/14 9:51
// *
// */
//@RestController
//class Controller  {
//
//    @GetMapping("/page")
//    fun page(): BaseResponse<PageInfoModel<Test>> {
//        return BaseResponse.build(PageInfoModel.valueOf(listOf<Test>()))
//    }
//}