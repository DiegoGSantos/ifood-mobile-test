package com.diego.tweetssentimentsanalyzer.twitterRestClient

import com.diego.tweetssentimentsanalyzer.feature.userDetail.data.Tweet
import com.diego.tweetssentimentsanalyzer.util.TWITTER_TWEETS_URL
import com.diego.tweetssentimentsanalyzer.util.TWITTER_USER_URL
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterApiClient
import com.twitter.sdk.android.core.TwitterException
import com.diego.tweetssentimentsanalyzer.feature.searchUser.model.User
import io.reactivex.Observable
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
    @retrofit2.http.GET(TWITTER_USER_URL)
    fun show(@retrofit2.http.Query("screen_name") screenName: String): Call<User>

    @retrofit2.http.GET(TWITTER_TWEETS_URL)
    fun getUserTweets(@retrofit2.http.Query("screen_name") screenName: String): Call<List<Tweet>>
}