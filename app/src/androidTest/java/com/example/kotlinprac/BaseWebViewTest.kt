package com.example.kotlinprac

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.web.sugar.Web.onWebView
import androidx.test.espresso.web.webdriver.DriverAtoms.*
import androidx.test.espresso.web.webdriver.Locator
import org.hamcrest.Matcher

open class BaseWebViewTest {
    fun clickWebViewElement(cssSelector: String, waitTime: Long = 1L) {
        onWebView()
            .withElement(findElement(Locator.CSS_SELECTOR, cssSelector))
            .perform(webClick())

        wait(waitTime)
    }

    fun inputTextToWebViewElement(cssSelector: String, waitTime: Long = 1L, text: String) {
        onWebView()
            .withElement(findElement(Locator.CSS_SELECTOR, cssSelector))
            .perform(webKeys(text))

        wait(waitTime)
    }

    private fun wait(millis: Long): ViewInteraction = onView(isRoot()).perform(waitFor(millis))

    private fun waitFor(millis: Long): ViewAction = object : ViewAction {
        override fun getDescription(): String = "${millis}초 기다림"

        override fun getConstraints(): Matcher<View> = isRoot()

        override fun perform(uiController: UiController, view: View?) =
            uiController.loopMainThreadForAtLeast(millis)
    }
}