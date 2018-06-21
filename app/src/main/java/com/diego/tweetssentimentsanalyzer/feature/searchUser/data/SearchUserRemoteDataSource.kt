package com.diego.tweetssentimentsanalyzer.feature.searchUser.data

import com.diego.tweetssentimentsanalyzer.twitterRestClient.CustomTwitterApiClient
import com.twitter.sdk.android.core.models.User
import io.reactivex.Observable

class SearchUserRemoteDataSource(private val customTwitterApiClient: CustomTwitterApiClient) {
    fun getUser(userName: String): Observable<User> {
        return customTwitterApiClient.findUser(userName)
    }
}