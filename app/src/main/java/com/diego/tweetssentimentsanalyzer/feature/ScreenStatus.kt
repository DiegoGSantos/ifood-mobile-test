package com.diego.tweetssentimentsanalyzer.feature

enum class ScreenStatus(val status: Int) {
    OK(200),
    LOADING(0),
    ERROR(500),
    NO_TWEETS_FOUND(404),
    NORMAL(1)
}