package com.example.kotlinprac

import android.view.InputDevice
import android.view.MotionEvent
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matchers.equalToIgnoringCase

interface ActionViewMatchers: BaseViewMatchers {
    private fun safeClick(viewInteraction: ViewInteraction) {
        with(viewInteraction) {
            runCatching {
                perform(scrollTo(), click())
            }.onFailure {
                perform(click())
            }
        }
    }

    fun tapById(resourceId: Int) = safeClick(findElementById(resourceId))

    fun tapByIdAndText(
        resourceId: Int,
        text: String
    ) = safeClick(findElementById(resourceId).check(matches(withText(equalToIgnoringCase(text)))))

    fun tapByIdWithParent(
        resourceId: Int,
        parentResourceId: Int
    ) = safeClick(findElementByIdWithParent(resourceId, parentResourceId))

    fun tapByIdAndTextWithParent(
        resourceId: Int,
        text: String,
        parentResourceId: Int
    ) = safeClick(
        findElementByIdWithParent(resourceId, parentResourceId)
            .check(matches(withText(equalToIgnoringCase(text))))
    )

    fun tapByText(text: String) = safeClick(findElementByText(text))

    fun tapByContentDescription(contentDescription: String) =
        safeClick(findElementByContentDescription(contentDescription))

    fun tapByLocation(x: Int, y: Int) =
        GeneralClickAction(
            Tap.SINGLE,
            CoordinatesProvider { view ->
                val screenPosition = IntArray(2).apply {
                    view?.getLocationOnScreen(this)
                }

                val screenX = (screenPosition[0] + x).toFloat()
                val screenY = (screenPosition[1] + y).toFloat()

                floatArrayOf(screenX, screenY)
            },
            Press.FINGER,
            InputDevice.SOURCE_MOUSE,
            MotionEvent.BUTTON_PRIMARY
        )

    fun tapByLocationPercentage(xPercentage: Double, yPercentage: Double) =
        GeneralClickAction(
            Tap.SINGLE,
            CoordinatesProvider { view ->
                val screenPosition = IntArray(2).apply {
                    view?.getLocationOnScreen(this)
                }

                val x = view.width * xPercentage
                val y = view.height * yPercentage

                val screenX = (screenPosition[0] + x).toFloat()
                val screenY = (screenPosition[1] + y).toFloat()

                floatArrayOf(screenX, screenY)
            },
            Press.FINGER,
            InputDevice.SOURCE_MOUSE,
            MotionEvent.BUTTON_PRIMARY
        )

    fun swipeUp(speed: Swipe = Swipe.FAST) = GeneralSwipeAction(
        speed,
        GeneralLocation.CENTER,
        GeneralLocation.TOP_CENTER,
        Press.FINGER
    )

    fun swipeDown(speed: Swipe = Swipe.FAST) = GeneralSwipeAction(
        speed,
        GeneralLocation.CENTER,
        GeneralLocation.BOTTOM_CENTER,
        Press.FINGER
    )

    fun swipeRight(speed: Swipe = Swipe.FAST) = GeneralSwipeAction(
        speed,
        GeneralLocation.CENTER_LEFT,
        GeneralLocation.CENTER_RIGHT,
        Press.FINGER
    )

    fun swipeLeft(speed: Swipe = Swipe.FAST) = GeneralSwipeAction(
        speed,
        GeneralLocation.CENTER_RIGHT,
        GeneralLocation.CENTER_LEFT,
        Press.FINGER
    )
}