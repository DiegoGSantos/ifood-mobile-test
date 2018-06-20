package com.diego.tweetssentimentsanalyzer.twitterRestClient

import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterApiClient
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.core.models.User
import io.reactivex.Observable
import okhttp3.Interceptor
import retrofit2.Call
import okhttp3.OkHttpClient




class CustomTwitterApiClient(okHttpClient: OkHttpClient) : TwitterApiClient(okHttpClient) {
    private fun provideTwitterService(): Service {
        return getService<Service>(Service::class.java)
    }

    fun findUser(userName: String): Observable<User> {
        return Observable.create<User> { subscriber ->
            val callback = object : Callback<User>() {
                override fun success(result: Result<User>) {
                    subscriber.onNext(result.data)
                }

                override fun failure(e: TwitterException) {
                    subscriber.onError(e)
                }
            }

            provideTwitterService().show(userName).enqueue(callback)
        }
    }

    fun getUserTweets(userName: String): Observable<List<Tweet>> {
        return Observable.create<List<Tweet>> { subscriber ->
            val callback = object : Callback<List<Tweet>>() {
                override fun success(result: Result<List<Tweet>>) {
                    subscriber.onNext(result.data)
                }

                override fun failure(e: TwitterException) {
                    subscriber.onError(e)
                }
            }

            provideTwitterService().getUserTweets(userName).enqueue(callback)
        }
    }
}

interface Service {
    @retrofit2.http.GET("/1.1/users/show.json")
    fun show(@retrofit2.http.Query("screen_name") screenName: String): Call<User>

    @retrofit2.http.GET("/1.1/statuses/user_timeline.json")
    fun getUserTweets(@retrofit2.http.Query("screen_name") screenName: String): Call<List<Tweet>>
}