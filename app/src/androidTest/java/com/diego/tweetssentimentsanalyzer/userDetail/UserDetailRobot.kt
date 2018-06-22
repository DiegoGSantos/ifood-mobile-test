package com.diego.tweetssentimentsanalyzer.userDetail

import android.support.test.espresso.Espresso
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import com.diego.tweetssentimentsanalyzer.App.Companion.appContext
import com.diego.tweetssentimentsanalyzer.R
import com.diego.tweetssentimentsanalyzer.base.BaseRobot
import com.diego.tweetssentimentsanalyzer.base.USER_NAME
import com.diego.tweetssentimentsanalyzer.base.USER_TWITTER
import com.diego.tweetssentimentsanalyzer.base.VALID_USER
import kotlinx.android.synthetic.main.activity_user_detail.view.*

class UserDetailRobot: BaseRobot() {
    fun checkUserInfo() {
        checkText(R.id.name, USER_NAME)
        checkText(R.id.userName, USER_TWITTER)
    }

    fun openUserDetail() {
        typeUser(VALID_USER)
        searchUser()
    }

    fun checkIfItIsShowingTheTweetsList() {
        isItemInVisible(R.id.progress)
        isItemVisible(R.id.tweets)
        isItemInVisible(R.id.error)
    }

    fun clickToAnalyseFirstItem() {
        clickRecyclerViewItem(R.id.tweets, 0, R.id.analyzeSentiment)
        waitForAWhile(3000)
    }

    fun checkDisplayedSentiment(position: Int, text: String, sentiment: String) {
        checkTextFromRecyclerViewItem(R.id.tweets, position, text)
        checkTextFromRecyclerViewItem(R.id.tweets, position, sentiment)
    }

    fun checkDispayedSadSentiment() {
        checkDisplayedSentiment(0, appContext.getString(R.string.sad_emoji_text),  appContext.getString(R.string.sad_emoji))
    }


}