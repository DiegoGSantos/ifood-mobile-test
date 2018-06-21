package com.diego.tweetssentimentsanalyzer.twitterRestClient

import okhttp3.*
import java.io.IOException


internal class MockInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val response = chain.proceed(request)

        val mimeType = "application/json"

        return Response.Builder()
                .addHeader("content-type", mimeType)
                .body(ResponseBody.create(MediaType.parse(mimeType), "{" + request + "}"))
                .code(200)
                .message("Mock response from res/raw/")
                .protocol(Protocol.HTTP_1_0)
                .request(chain.request())
                .build()

//        return response
    }
}