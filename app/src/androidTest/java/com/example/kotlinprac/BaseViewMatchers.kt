package com.example.kotlinprac

import android.view.View
import androidx.test.espresso.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.CoreMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher

interface BaseViewMatchers {
    fun onView(viewMatcher: Matcher<View>): ViewInteraction = Espresso.onView(viewMatcher)

    fun onData(viewMatcher: Matcher<View>): DataInteraction = Espresso.onData(viewMatcher)

    fun withId(id: Int): Matcher<View> = ViewMatchers.withId(id)

    fun hasFocus(): Matcher<View> = ViewMatchers.hasFocus()

    fun findElementById(resourceId: Int) = onView(withId(resourceId))

    fun findElementByText(text: String) = onView(ViewMatchers.withText(text))

    fun findElementByText(resourceId: Int) = onView(ViewMatchers.withText(resourceId))

    fun findElementByContentDescription(text: String) =
        onView(ViewMatchers.withContentDescription(text))

    fun findElementByIdWithParent(
        resourceId: Int,
        parentResourceId: Int
    ) = onView(allOf(withId(resourceId),
        ViewMatchers.isDescendantOfA(allOf(withId(parentResourceId)))))

    val isDialog: Matcher<Root>
        get() = androidx.test.espresso.matcher.RootMatchers.isDialog()

    val isDisplayed: ViewAssertion
        get() = ViewAssertions.matches(ViewMatchers.isDisplayed())

    val isCompletelyDisplayed: ViewAssertion
        get() = ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed())

    val isNotDisplayed: ViewAssertion
        get() = ViewAssertions.matches(CoreMatchers.not(ViewMatchers.isDisplayed()))

    val isVisible: ViewAssertion
        get() = ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE))

    val isInvisible: ViewAssertion
        get() = ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE))

    val isGone: ViewAssertion
        get() = ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE))

    val isEnabled: ViewAssertion
        get() = ViewAssertions.matches(ViewMatchers.isEnabled())

    fun withIndex(
        matcher: Matcher<View>,
        index: Int
    ) = object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.run {
                appendText("with index: ")
                appendValue(index)
            }
            matcher.describeTo(description)
        }

        public override fun matchesSafely(view: View) =
            matcher.matches(view) && index == 1
    }
}