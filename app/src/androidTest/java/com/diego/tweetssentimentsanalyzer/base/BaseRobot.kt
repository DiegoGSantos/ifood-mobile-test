package com.diego.tweetssentimentsanalyzer.base

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import com.diego.tweetssentimentsanalyzer.R
import com.diego.tweetssentimentsanalyzer.base.util.RecyclerViewMatcher
import org.hamcrest.Matchers

open class BaseRobot {
    fun checkText(id: Int, text: String) {
        onView(ViewMatchers.withId(id)).check(ViewAssertions.matches(ViewMatchers.withText(text)))
    }

    fun typeUser(user: String) {
        onView(ViewMatchers.withId(R.id.userName))
                .perform(ViewActions.typeText(user))
                .perform(ViewActions.closeSoftKeyboard())
    }

    fun searchUser() {
        onView(ViewMatchers.withId(R.id.findUser)).perform(ViewActions.click())
    }

    fun waitForAWhile(time: Long) {
        Thread.sleep(time)
    }

    fun checkIfDetailIsOpened() {
        onView(ViewMatchers.withId(R.id.collapsingToolbar)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    fun isItemVisible(itemId: Int) {
        onView(ViewMatchers.withId(itemId)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    fun isItemInVisible(itemId: Int) {
        onView(ViewMatchers.withId(itemId)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())))
    }

    fun clickRecyclerViewItem(listId: Int, itemPosition: Int, itemId: Int) {
        onView(RecyclerViewMatcher(listId).atPositionOnView(itemPosition,itemId)).perform(ViewActions.click())
    }

    fun checkTextFromRecyclerViewItem(listId: Int, itemPosition: Int, text: String) {
        onView(RecyclerViewMatcher(listId).atPosition(itemPosition))
                .check(ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.withText(text))))
    }
}
