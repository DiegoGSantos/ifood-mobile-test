package com.diego.tweetssentimentsanalyzer.feature.searchUser.data

import com.diego.tweetssentimentsanalyzer.twitterRestClient.CustomTwitterApiClient
import com.diego.tweetssentimentsanalyzer.feature.searchUser.model.User
import io.reactivex.Observable

class SearchUserRemoteDataSource(private val customTwitterApiClient: CustomTwitterApiClient) {
    fun getUser(userName: String): Observable<User> {
        return customTwitterApiClient.findUser(userName)
    }
}