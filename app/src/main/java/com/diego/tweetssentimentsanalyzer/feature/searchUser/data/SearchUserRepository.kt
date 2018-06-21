package com.diego.tweetssentimentsanalyzer.feature.searchUser.data

import com.diego.tweetssentimentsanalyzer.feature.searchUser.model.User
import io.reactivex.Observable

class SearchUserRepository(private val searchUserRemoteDataSource: SearchUserRemoteDataSource) {
    fun getUser(userName: String): Observable<User> {
        return searchUserRemoteDataSource.getUser(userName)
    }
}