package edu.bu.homies

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import edu.bu.homies.activities.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class HomeEditNavigationTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)
    val orgTitle = "Weather Forecast"
    val orgDescription = "Weather Forcast is an app ..."

    val title = "Homies"
    val description = "this is homies"

    @Test
    fun testEditFragmentNavigation() {
        //click edit button
        onView(withId(R.id.editHome)).perform(click())

        // verify navigate to edit fragment
        onView(withId(R.id.editFragment))
            .check(matches(isDisplayed()))

        pressBack()

        // verify navigate back to detail fragment
        onView(withId(R.id.detailFragment))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testCancelEditHome() {
        //click edit button
        onView(withId(R.id.editHome)).perform(click())
        //modify home title
        onView(withId(R.id.homeTitleEdit)).perform(replaceText(title))
        // modify home description
        onView(withId(R.id.homeDescEdit)).perform(replaceText(description))
        // click cancel button
        onView(withId(R.id.cancel)).perform(click())

        //verify information changed in detail page
        onView(withId(R.id.detailFragment))
            .check(matches(isDisplayed()))

        onView(withId(R.id.homeTitle))
            .check(matches(withText(orgTitle)))

        onView(withId(R.id.homeDesc))
            .check(matches(withText(orgDescription)))

    }

    @Test
    fun testEditHome() {
        //click edit button
        onView(withId(R.id.editHome)).perform(click())
        //modify home title
        onView(withId(R.id.homeTitleEdit)).perform(replaceText(title))
        // modify home description
        onView(withId(R.id.homeDescEdit)).perform(replaceText(description))
        // click submit button
        onView(withId(R.id.submit)).perform(click())

        //verify information changed in detail page
        onView(withId(R.id.detailFragment))
            .check(matches(isDisplayed()))
        onView(withId(R.id.homeTitle))
            .check(matches(withText(title)))
        onView(withId(R.id.homeDesc))
            .check(matches(withText(description)))
    }

}