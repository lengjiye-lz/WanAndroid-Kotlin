package com.lengjiye.network.interceptor

import com.lengjiye.code.baseparameter.application.MasterApplication
import com.lengjiye.tools.log.logD
import com.lengjiye.tools.log.logE
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException

class SignInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest = chain.request()
        val requestBuilder = originRequest.newBuilder()
        // 可以添加公共请求参数
        val request = requestBuilder.build()
        val response = chain.proceed(request)
        val body = response.body()
        if (body != null) {
            val mediaType = body.contentType()
            logD("mediaType=$mediaType")
            val content = body.string()
            if ("debug" == MasterApplication.getInstance().buildType()) {
                logE("#############################################################")
                logE("request.url()=" + request.url())
                logE("response.body()=$content")
                logE("#############################################################")
            }
            return response.newBuilder().body(ResponseBody.create(mediaType, content)).build()
        }
        return response
    }
}