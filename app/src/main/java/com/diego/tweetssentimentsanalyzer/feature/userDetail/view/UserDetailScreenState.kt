package com.diego.tweetssentimentsanalyzer.feature.userDetail.view

import com.diego.tweetssentimentsanalyzer.feature.ScreenState
import com.diego.tweetssentimentsanalyzer.feature.userDetail.data.Tweet

class UserDetailScreenState(status: Int, message: String, val tweets: List<Tweet>?) : ScreenState(status, message) {
    fun isThereNoTweets(): Boolean = status == UserDetailScreenStatus.NO_TWEETS_FOUND.status
}