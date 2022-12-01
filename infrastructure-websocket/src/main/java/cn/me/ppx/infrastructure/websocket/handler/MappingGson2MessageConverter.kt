package cn.me.ppx.infrastructure.websocket.handler

import com.alibaba.fastjson.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.Message
import org.springframework.messaging.MessageHeaders
import org.springframework.messaging.converter.AbstractMessageConverter
import org.springframework.util.MimeType

class MappingGson2MessageConverter: AbstractMessageConverter(MimeType("application", "json")) {


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
//        JSONObject.toJSON(payload)
        return gson.toJson(payload).encodeToByteArray()
    }
}