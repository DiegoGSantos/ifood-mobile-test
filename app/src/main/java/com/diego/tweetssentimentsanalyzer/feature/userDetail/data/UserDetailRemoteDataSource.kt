package com.diego.tweetssentimentsanalyzer.feature.userDetail.data

import com.diego.tweetssentimentsanalyzer.twitterRestClient.CustomTwitterApiClient
import com.diego.tweetssentimentsanalyzer.twitterRestClient.MockInterceptor
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.core.models.User
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class UserDetailRemoteDataSource {
    fun getUserTweets(userName: String): Observable<List<Tweet>> {
        val interceptor = MockInterceptor()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val customClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(interceptor)
                .build()

        return CustomTwitterApiClient(customClient).getUserTweets(userName)
    }
}