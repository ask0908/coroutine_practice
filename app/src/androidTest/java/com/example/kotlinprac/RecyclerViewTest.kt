package com.example.kotlinprac

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.kotlinprac.recyclerview.uitest.RecyclerViewUiTestActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class RecyclerViewTest {
    @JvmField
    @Rule
    val activityScenarioRule: ActivityScenarioRule<RecyclerViewUiTestActivity> =
        ActivityScenarioRule(RecyclerViewUiTestActivity::class.java)

    companion object {
        const val ITEM_BELOW_THE_FOLD = 5;
    }

    @Test
    fun recyclerView_isDisplayed() {
        onView(withId(R.id.rv_test)).check(matches(isDisplayed()))
    }

    @Test
    fun itemWithText_doesNotExist() {
        onView(withId(R.id.rv_test))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(ITEM_BELOW_THE_FOLD, click()))

        val itemText = "글자 5"
        onView(withText(itemText)).check(matches(isDisplayed()))
    }

    @Test
    fun allItemsInRecyclerView_checkAllDisplayed() {
        var itemCount = 0
        activityScenarioRule.scenario.onActivity { activity ->
            val recyclerView: RecyclerView = activity.findViewById(R.id.rv_test)
            itemCount = recyclerView.adapter?.itemCount ?: 0
        }

        if (itemCount > 0) {
            onView(withId(R.id.rv_test)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
            onView(withId(R.id.rv_test)).check(matches(atPosition(0, isDisplayed())))

            onView(withId(R.id.rv_test)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(itemCount - 1))
            onView(withId(R.id.rv_test)).check(matches(atPosition(itemCount - 1, isDisplayed())))
        }
    }

    private fun atPosition(position: Int, itemMatcher: Matcher<View>): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("has item at position $position: ")
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(view: View): Boolean {
                val viewHolder = (view as RecyclerView).findViewHolderForAdapterPosition(position)
                return itemMatcher.matches(viewHolder?.itemView)
            }
        }
    }
}