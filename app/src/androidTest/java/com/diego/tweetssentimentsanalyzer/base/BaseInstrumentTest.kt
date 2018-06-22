package com.diego.tweetssentimentsanalyzer.base

import android.content.Intent
import android.support.test.rule.ActivityTestRule
import com.diego.tweetssentimentsanalyzer.feature.searchUser.view.SearchUserActivity
import com.diego.tweetssentimentsanalyzer.util.IS_UNDER_TEST
import org.junit.Before
import org.junit.Rule

open class BaseInstrumentTest {
    @get:Rule
    private val activityRule = ActivityTestRule(SearchUserActivity::class.java, true, false)

    @Before
    fun setUp() {
        val grouchyIntent = Intent()
        activityRule.launchActivity(grouchyIntent)

        IS_UNDER_TEST = true
    }
}