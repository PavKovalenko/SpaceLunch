package com.stein.spacelunch.ui

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.stein.spacelunch.data.fakeUpcomings
import com.stein.spacelunch.ui.upcoming_list.ui.UpcomingListScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class UpcomingListScreenTest {

    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltTestRule.inject()
        composeTestRule.activity.setContent {
            UpcomingListScreen()
        }
    }


    @Test
    fun verifyThatAllReceivedItemsAreDisplayed() {
        fakeUpcomings.forEach { item ->
            composeTestRule.onNodeWithText(item).assertExists()
        }
    }
}