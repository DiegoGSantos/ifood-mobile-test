package com.diego.tweetssentimentsanalyzer.userDetail

import com.diego.tweetssentimentsanalyzer.base.BaseInstrumentTest
import org.junit.Test

class UserDetailInstrumentedTest: BaseInstrumentTest() {
    private val robot = UserDetailRobot()

    @Test
    fun userInfoLoadedProperly() {
        robot.apply {
            openUserDetail()
            checkUserInfo()
            checkIfItIsShowingTheTweetsList()
        }
    }

    @Test
    fun analyzeTweet_displayNeutralSentiment(){
        robot.apply {
            openUserDetail()
            clickToAnalyseFirstItem()
            checkDispayedSadSentiment()
        }
    }


}