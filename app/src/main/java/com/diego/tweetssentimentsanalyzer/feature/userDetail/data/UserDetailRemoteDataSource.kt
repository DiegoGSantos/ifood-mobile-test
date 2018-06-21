package com.diego.tweetssentimentsanalyzer.feature.userDetail.data

import com.diego.tweetssentimentsanalyzer.twitterRestClient.CustomTwitterApiClient
import io.reactivex.Observable

class UserDetailRemoteDataSource(private val customTwitterApiClient: CustomTwitterApiClient) {
    fun getUserTweets(userName: String): Observable<List<Tweet>> {
        return customTwitterApiClient.getUserTweets(userName)
    }
}