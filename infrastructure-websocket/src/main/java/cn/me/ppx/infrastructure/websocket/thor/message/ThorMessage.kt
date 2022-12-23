package me.jinuo.imf.thor.message

@Suppress("MemberVisibilityCanBePrivate")
data class ThorMessage<T>(
    val code: Int,
    val description: String? = null,
    val data: T? = null
){
    companion object{
        const val CODE_SUCCESS = 200
        const val CODE_FAILED = 400
        const val CODE_DESTINATION_NO_FOUND = 404
        const val CODE_EXCEPTION = 500

        fun success(): ThorMessage<Any>{
            return ThorMessage(CODE_SUCCESS, "success")
        }

        fun<T> success(data: T?): ThorMessage<T>{
            return ThorMessage(CODE_SUCCESS, "success", data)
        }

        fun<T> failed(error: T?): ThorMessage<T>{
            return ThorMessage(CODE_FAILED, "client exception", error)
        }

        fun notFound(): ThorMessage<Any>{
            return ThorMessage(CODE_DESTINATION_NO_FOUND, "destination not found")
        }

        fun exception(): ThorMessage<Any>{
            return ThorMessage(CODE_EXCEPTION, "server exception")
        }

    }
}