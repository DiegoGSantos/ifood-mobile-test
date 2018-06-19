package com.diego.tweetssentimentsanalyzer.feature.searchUser.view

enum class SearchUserScreenStatus(val status: Int) {
    SHOULD_PROVIDE_USER(204),
    INVALID_USER(400),
    USER_NOT_FOUND(404),
}