package com.example.kotlinprac

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.kotlinprac.tablayout.TabActivity
import com.google.android.material.tabs.TabLayout
import junit.framework.Assert.assertTrue
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.regex.Pattern.matches

@RunWith(AndroidJUnit4::class)
class TabLayoutWithViewPagerTest: CollectedViewMatchers, ActionViewMatchers {
    @get:Rule
    val activityTestRule = ActivityScenarioRule(TabActivity::class.java)

    private fun selectTabAtPosition(tabIndex: Int): ViewAction {
        return object : ViewAction {
            override fun getDescription() = "with tab at index $tabIndex"

            override fun getConstraints() = allOf(isDisplayed(), isAssignableFrom(TabLayout::class.java))

            override fun perform(uiController: UiController, view: View) {
                val tabLayout = view as TabLayout
                val tabAtIndex: TabLayout.Tab = tabLayout.getTabAt(tabIndex)
                    ?: throw PerformException.Builder()
                        .withCause(Throwable("No tab at index $tabIndex"))
                        .build()

                tabAtIndex.select()
            }
        }
    }

    fun matchCurrentTabTitle(tabTitle: String): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description?) {
                description?.appendText("unable to match title of current selected tab with $tabTitle")
            }

            override fun matchesSafely(item: View?): Boolean {
                val tabLayout = item as TabLayout
                val tabAtIndex: TabLayout.Tab = tabLayout.getTabAt(tabLayout.selectedTabPosition)
                    ?: throw PerformException.Builder()
                        .withCause(Throwable("No tab at index ${tabLayout.selectedTabPosition}"))
                        .build()

                return tabAtIndex.text.toString().contains(tabTitle, true)
            }
        }
    }

    fun matchTabTitleAtPosition(tabTitle: String, tabIndex: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description?) {
                description?.appendText("unable to select tab at index $tabIndex and match title with $tabTitle")
            }

            override fun matchesSafely(item: View?): Boolean {
                val tabLayout = item as TabLayout
                val tabAtIndex: TabLayout.Tab = tabLayout.getTabAt(tabIndex)
                    ?: throw PerformException.Builder()
                        .withCause(Throwable("No tab at index $tabIndex"))
                        .build()

                return tabAtIndex.text.toString().contains(tabTitle, true)
            }
        }
    }

    fun withTab(title: String): Matcher<View> = withTab(equalTo(title))

    fun withTab(title: Matcher<String>): Matcher<View> {
        return object : BoundedMatcher<View, TabLayout.TabView>(TabLayout.TabView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("with tab: ")
                title.describeTo(description)
            }

            override fun matchesSafely(item: TabLayout.TabView): Boolean {
                return title.matches(item.tab?.text)
            }
        }
    }

    @Test
    fun `탭_레이아웃_테스트`() {
        onView(withId(R.id.tab_layout))
            .perform(selectTabAtPosition(0))
    }

    @Test
    fun `matchTabTitleAtPosition_함수_테스트`() {
//        tapByIdAndTextWithParent(R.id.tab_first, "탭 2", R.id.tab_layout)
//        tapById(R.id.tab_first)
        tapByContentDescription("탭 3")
    }
}