package cn.me.ppx.infrastructure.web.ext.failureAnalyzer

import org.springframework.boot.diagnostics.AbstractFailureAnalyzer
import org.springframework.boot.diagnostics.FailureAnalysis
import org.springframework.core.env.MissingRequiredPropertiesException
import java.lang.IllegalStateException

/**
 * @author  ym
 * @date  2022/12/6 10:21
 *
 */
class IllegalStateFailureAnalyzer: AbstractFailureAnalyzer<MissingRequiredPropertiesException>() {
    override fun analyze(rootFailure: Throwable?, cause: MissingRequiredPropertiesException?): FailureAnalysis {
        return FailureAnalysis(cause?.message, "Please check start require param!", cause)
    }
}