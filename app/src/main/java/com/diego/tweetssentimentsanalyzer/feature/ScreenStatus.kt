package com.diego.tweetssentimentsanalyzer.feature

enum class ScreenStatus(val status: Int) {
    OK(200),
    LOADING(0),
    ERROR(500),
    NORMAL(1)
}