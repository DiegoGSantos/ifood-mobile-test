package com.diego.tweetssentimentsanalyzer.searchUser

import android.support.test.runner.AndroidJUnit4
import com.diego.tweetssentimentsanalyzer.App.Companion.appContext
import com.diego.tweetssentimentsanalyzer.R
import com.diego.tweetssentimentsanalyzer.base.BaseInstrumentTest
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchUserInstrumentedTest: BaseInstrumentTest() {
    private val robot = SearchUserRobot()

    @Test
    fun openValidUser() {
        robot.apply {
            typeValidUser()
            searchUser()
            checkIfDetailIsOpened()
        }
    }

    @Test
    fun trySubmitNoUser() {
        robot.apply {
            typeNoUser()
            searchUser()
            verifyError(appContext.getString(R.string.user_not_provided))
        }
    }

    @Test
    fun trySubmitUsInvalidUser() {
        robot.apply {
            typeInvalidUser()
            searchUser()
            verifyError(appContext.getString(R.string.user_invalid))
        }
    }
}
