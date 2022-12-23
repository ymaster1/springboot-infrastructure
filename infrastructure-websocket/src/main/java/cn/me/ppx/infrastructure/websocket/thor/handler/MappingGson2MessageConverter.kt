package cn.me.ppx.infrastructure.websocket.thor.handler

import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.Message
import org.springframework.messaging.MessageHeaders
import org.springframework.messaging.converter.AbstractMessageConverter
import org.springframework.stereotype.Component
import org.springframework.util.MimeType

class MappingGson2MessageConverter: AbstractMessageConverter(MimeType("application", "json")) {
    @Autowired
    private lateinit var gson: Gson

    override fun supports(clazz: Class<*>): Boolean {
        //没用的
        return true
    }

    override fun canConvertFrom(message: Message<*>, targetClass: Class<*>): Boolean {
        return false
    }

    override fun canConvertTo(payload: Any, headers: MessageHeaders?): Boolean {
        return supportsMimeType(headers)
    }

    override fun convertToInternal(payload: Any, headers: MessageHeaders?, conversionHint: Any?): Any? {
        return gson.toJson(payload).encodeToByteArray()
    }
}