package com.weatheraanalyzerrrr.weatheranalyzer.ui.theme.screens.main


import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.weatheraanalyzerrrr.weatheranalyzer.MainActivity
import com.weatheraanalyzerrrr.weatheranalyzer.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Timer
import kotlin.concurrent.schedule

@HiltAndroidTest
class MainScreenKtTest {

    //Initialize hiltRule
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    //Initialize composeTestRule
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun init() {
        hiltRule.inject()  //Inject
        composeTestRule.activity.setContent {
            MainScreen()
        }
    }

    @Test
    fun getLocation_ShowDateOfTheDay() = runTest {

        //Get The Date Of Today
        val date = java.util.Date()
        val sfd = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val dateFormat = sfd.format(date)

        //Delay 6 Sec
        asyncTimer(6000)

        //Check If The Date If The Date Displayed
        composeTestRule.onNode(hasTestTag("Date") and hasText(dateFormat)).assertIsDisplayed()
    }

    @Test
    fun getSpecialCityWeather_ShowTheNameOfCity() = runTest {

        //Write The City Name In The Search TextField
        composeTestRule.onNodeWithTag("searchTextFieldTag").performTextInput("Paris")

        //Delay 6 Sec
        asyncTimer(6000)

        //Check If The City Name Displayed
        composeTestRule.onNode(hasTestTag("cityName_main") and hasText("Paris")).assertIsDisplayed()

    }


    @Test
    fun searchForInCorrectCity_ShowCityNotFound() = runTest {

        //Write InCorrect City Name In The Search TextField
        composeTestRule.onNodeWithTag("searchTextFieldTag").performTextInput("sssSss")

        //Delay 3 Sec
        asyncTimer(3000)

        //Check If The Error Message Displayed
        composeTestRule.onNodeWithText("city not found").assertIsDisplayed()

    }


    @Test
    fun clickOnCalenderIcon_RemoveSearchText() = runTest {

        //Write The City Name In The Search TextField
        composeTestRule.onNodeWithTag("searchTextFieldTag").performTextInput("Paris")

        //Delay 6 Sec
        asyncTimer(6000)

        //Click On Calender Icon
        composeTestRule.onNode(
            hasTestTag("calenderIcon") and hasContentDescription(
                composeTestRule.activity.getString(
                    R.string.date_range_icon
                )
            )
        ).performClick()

        //Check The Text Of Search TextField is Empty
        composeTestRule.onNode(hasTestTag("searchTextFieldTag") and hasText("")).assertIsDisplayed()

    }


    private fun asyncTimer(delay: Long = 1000) {
        AsyncTimer.start(delay)
        composeTestRule.waitUntil(
            condition = { AsyncTimer.expired },
            timeoutMillis = delay + 1000
        )
    }

    object AsyncTimer {
        var expired = false
        fun start(delay: Long = 1000) {
            expired = false
            Timer().schedule(delay) {
                expired = true
            }
        }
    }

}