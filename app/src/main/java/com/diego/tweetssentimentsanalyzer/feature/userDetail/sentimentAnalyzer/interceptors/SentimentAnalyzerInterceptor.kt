package com.diego.tweetssentimentsanalyzer.feature.userDetail.sentimentAnalyzer.interceptors

import com.diego.tweetssentimentsanalyzer.util.GOOGLE_NATURAL_LANGUAGE_KEY
import okhttp3.Interceptor
import okhttp3.Response

class SentimentAnalyzerInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url()

        val urlWithApiKey = originalUrl.newBuilder()
                .addQueryParameter("key", GOOGLE_NATURAL_LANGUAGE_KEY)
                .build()

        val modifiedRequest = originalRequest.newBuilder()
                .url(urlWithApiKey)
                .build()

        return chain.proceed(modifiedRequest)
    }

}