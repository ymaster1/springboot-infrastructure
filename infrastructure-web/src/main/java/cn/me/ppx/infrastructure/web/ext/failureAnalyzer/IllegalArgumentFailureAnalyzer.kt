package cn.me.ppx.infrastructure.web.ext.failureAnalyzer

import org.springframework.boot.diagnostics.AbstractFailureAnalyzer
import org.springframework.boot.diagnostics.FailureAnalysis

/**
 * @author  ym
 * @date  2022/10/29 17:33
 * 监控启动时的非法参数异常
 */
class IllegalArgumentFailureAnalyzer : AbstractFailureAnalyzer<IllegalArgumentException>() {
    override fun analyze(rootFailure: Throwable?, cause: IllegalArgumentException?): FailureAnalysis {
        return FailureAnalysis(cause?.message, "Please check injection param!", cause)
    }
}