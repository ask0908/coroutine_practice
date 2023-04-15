package com.example.kotlinprac.espressoWeb

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.kotlinprac.BaseWebViewTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EspressoWebTestActivityTest: BaseWebViewTest() {
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(EspressoWebTestActivity::class.java)

    @Test
    fun `왼쪽_상단_햄버거_메뉴_클릭_테스트`() {
        clickWebViewElement(
            cssSelector = "#welcom_wrap > div.aladin-gnb.main > div.drawer > a > img",
            waitTime = 2000L
        )
    }

    @Test
    fun `글자_입력_테스트`() {
        clickWebViewElement(
            cssSelector = "#SearchWordBanner",
        )

        inputTextToWebViewElement(
            cssSelector = "#SearchWord",
            text = "컴퓨터\n"
        )

        clickWebViewElement(
            cssSelector = "#welcom_wrap > div.aladin-gnb.main > div.sch-wrap > div > div > a > img",
            waitTime = 3000L
        )
    }
}