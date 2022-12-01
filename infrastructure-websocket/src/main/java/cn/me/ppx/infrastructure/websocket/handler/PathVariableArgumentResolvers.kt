package cn.me.ppx.infrastructure.websocket.handler

import cn.me.ppx.infrastructure.websocket.annotation.PathVariable
import org.springframework.core.MethodParameter
import org.springframework.core.convert.ConversionService
import org.springframework.messaging.Message
import org.springframework.messaging.MessageHandlingException
import org.springframework.messaging.handler.annotation.ValueConstants
import org.springframework.messaging.handler.annotation.support.AbstractNamedValueMethodArgumentResolver
import org.springframework.messaging.handler.annotation.support.DestinationVariableMethodArgumentResolver
import org.springframework.util.Assert


class PathVariableArgumentResolvers(conversionService: ConversionService) : AbstractNamedValueMethodArgumentResolver(conversionService, null) {
    override fun resolveArgumentInternal(parameter: MethodParameter, message: Message<*>, name: String): Any? {
        val headers = message.headers
        val vars = headers[DestinationVariableMethodArgumentResolver.DESTINATION_TEMPLATE_VARIABLES_HEADER] as Map<String, String>?
        return vars?.get(name)
    }

    override fun createNamedValueInfo(parameter: MethodParameter): NamedValueInfo {
        val annot = parameter.getParameterAnnotation(PathVariable::class.java)
        Assert.state(annot != null, "No DestinationVariable annotation")
        return PathVariableNamedValueInfo(annot!!)
    }

    override fun handleMissingValue(name: String, parameter: MethodParameter, message: Message<*>) {
        throw MessageHandlingException(message, "Missing path template variable '" + name + "' " +
                "for method parameter type [" + parameter.parameterType + "] and parameter name [${parameter.parameterName}]")
    }

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(PathVariable::class.java)
    }

    private class PathVariableNamedValueInfo(annotation: PathVariable) : NamedValueInfo(annotation.value, true, ValueConstants.DEFAULT_NONE)


}
