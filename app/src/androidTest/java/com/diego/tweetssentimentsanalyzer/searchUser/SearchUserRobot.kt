package com.diego.tweetssentimentsanalyzer.searchUser

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import com.diego.tweetssentimentsanalyzer.R
import com.diego.tweetssentimentsanalyzer.base.BaseRobot
import com.diego.tweetssentimentsanalyzer.base.INVALID_USER
import com.diego.tweetssentimentsanalyzer.base.VALID_USER

class SearchUserRobot: BaseRobot() {

    fun typeValidUser() {
        typeUser(VALID_USER)
    }

    fun typeInvalidUser() {
        typeUser(INVALID_USER)
    }

    fun typeNoUser() {
        typeUser("")
    }

    fun getUserInput(): ViewInteraction {
        return onView(ViewMatchers.withId(R.id.userName))
    }

    fun verifyError(error: String) {
        getUserInput().check(ViewAssertions.matches(ViewMatchers.hasErrorText(error)))
    }
}