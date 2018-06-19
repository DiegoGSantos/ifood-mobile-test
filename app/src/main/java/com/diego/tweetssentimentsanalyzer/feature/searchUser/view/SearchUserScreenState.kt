package com.diego.tweetssentimentsanalyzer.feature.searchUser.view

import com.diego.tweetssentimentsanalyzer.feature.ScreenState
import com.twitter.sdk.android.core.models.User

class SearchUserScreenState(status: Int, message: String, val user: User?) : ScreenState(status, message) {
    fun isUserInvalid(): Boolean = status == SearchUserScreenStatus.INVALID_USER.status

    fun isUserNotProvided(): Boolean = status == SearchUserScreenStatus.SHOULD_PROVIDE_USER.status

    fun isUserNotFound(): Boolean = status == SearchUserScreenStatus.USER_NOT_FOUND.status
}