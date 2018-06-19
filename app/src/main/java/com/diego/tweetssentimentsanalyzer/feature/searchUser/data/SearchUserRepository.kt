package com.diego.tweetssentimentsanalyzer.feature.searchUser.data

import com.twitter.sdk.android.core.models.User
import io.reactivex.Observable

class SearchUserRepository(private val searchUserRemoteDataSource: SearchUserRemoteDataSource) {
    fun getUser(userName: String): Observable<User> {
        return searchUserRemoteDataSource.getUser(userName)
    }
}