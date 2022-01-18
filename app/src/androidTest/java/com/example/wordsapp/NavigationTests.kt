package com.example.wordsapp

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import de.mannodermaus.junit5.ActivityScenarioExtension
import org.hamcrest.Matchers.equalToIgnoringCase
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

class NavigationTests {

    @JvmField
    @RegisterExtension
    val scenarioExtension = ActivityScenarioExtension.launch<MainActivity>()

    /**
     * First approach: find the view which has the text "C" on it. This will work, because "C" is always visible, but
     * the test will fail if we would replace "C" with "Z", because "Z" is initially not visible.
     */
    @Test
    fun navigate_to_word() {
        // arrange / act
        onView(withText("C")).perform(click())

        // assert
        onView(withText(equalToIgnoringCase("words that start with c"))).check(matches(isDisplayed()))
    }

    /**
     * Second approach: more resilient. We interact with the RecyclerView and click the last item; the method handles
     * the scrolling.
     */
    @Test
    fun navigate_to_word_2() {
        // arrange / act
        onView(withId(R.id.recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(25, click())
        )

        // assert
        onView(withText(equalToIgnoringCase("words that start with z"))).check(matches(isDisplayed()))
    }
}