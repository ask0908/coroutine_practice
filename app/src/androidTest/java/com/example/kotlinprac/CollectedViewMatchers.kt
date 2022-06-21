package com.example.kotlinprac

import android.view.View
import android.widget.TextView
import androidx.test.espresso.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.equalToIgnoringCase

interface CollectedViewMatchers: BaseViewMatchers {
    override fun findElementByIdWithParent(
        resourceId: Int,
        parentResourceId: Int
    ) = onView(allOf(withId(resourceId), isDescendantOfA(allOf(withId(parentResourceId)))))

    override fun findElementByContentDescription(text: String) =
        onView(withContentDescription(text))

    fun isElementByIdEnabled(resourceId: Int): ViewInteraction =
        findElementById(resourceId).check(isEnabled)

    fun isElementByIdAppeared(resourceId: Int): ViewInteraction =
        findElementById(resourceId).check(isDisplayed)

    fun isElementByIdCompletelyDisplayed(resourceId: Int): ViewInteraction =
        findElementById(resourceId).check(isCompletelyDisplayed)

    fun isElementByIdWithParentDisplayed(resourceId: Int, parentResourceId: Int): ViewInteraction =
        findElementByIdWithParent(resourceId = resourceId, parentResourceId = parentResourceId).check(isDisplayed)

    fun isElementByIdHaveSiblingWithId(resourceId: Int, siblingResourceId: Int) =
        runCatching {
            onView(allOf(withId(resourceId), isCompletelyDisplayed()))
                .check(matches(hasSibling(allOf(withId(siblingResourceId), isCompletelyDisplayed()))))
        }.isSuccess

    fun isElementByIdAppearedAtLeast(resourceId: Int, elementAreaPercentage: Int) =
        runCatching {
            onView(allOf(withId(resourceId), isDisplayingAtLeast(elementAreaPercentage)))
                .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        }.isSuccess

    fun isElementByIdHasText(
        resourceId: Int,
        text: String
    ) {
        runCatching {
            findElementById(resourceId).check(matches(withText(equalToIgnoringCase(text))))
        }.onFailure {
            onData(allOf(withId(resourceId))).atPosition(0)
                .check(matches(withText(equalToIgnoringCase(text))))
        }
    }

    fun isElementByIdDisappeared(resourceId: Int) {
        with(findElementById(resourceId)) {
            runCatching {
                check(doesNotExist())
            }.onFailure {
                runCatching {
                    check(isInvisible)
                }.onFailure {
                    check(isGone)
                }
            }
        }
    }

    fun getTextFromElementByID(resourceId: Int): String {
        var text: String? = null
        onView(withId(resourceId)).perform(object : ViewAction {
            override fun getConstraints() = isAssignableFrom(TextView::class.java)

            override fun getDescription() = "getting text from a TextView"

            override fun perform(uiController: UiController, view: View) {
                text = (view as TextView).text.toString()
            }
        })
        return text!!
    }
}