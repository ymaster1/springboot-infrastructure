package cn.me.ppx.infrastructure.web.ext.failureAnalyzer

import org.springframework.boot.diagnostics.AbstractFailureAnalyzer
import org.springframework.boot.diagnostics.FailureAnalysis

/**
 * @author  ym
 * @date  2022/10/29 17:29
 *  监控启动时的空指针异常
 */
class NullPointerFailureAnalyzer : AbstractFailureAnalyzer<NullPointerException>() {


    override fun analyze(rootFailure: Throwable?, cause: NullPointerException?): FailureAnalysis {
        return FailureAnalysis(cause?.message, "Please check null pointer!", cause)
    }


}