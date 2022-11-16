package cn.me.ppx

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author  ym
 * @date  2022/11/15 10:29
 *
 */
@RestController
class WebController {
    @RequestMapping("/test")
    fun test():String{
        return "hello world!!"
    }

}