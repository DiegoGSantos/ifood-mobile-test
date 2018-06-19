package com.diego.tweetssentimentsanalyzer.twitterRestClient

import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterApiClient
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.User
import io.reactivex.Observable
import retrofit2.Call


class CustomTwitterApiClient : TwitterApiClient() {
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
}

interface Service {
    @retrofit2.http.GET("/1.1/users/show.json")
    fun show(@retrofit2.http.Query("screen_name") screenName: String): Call<User>
}