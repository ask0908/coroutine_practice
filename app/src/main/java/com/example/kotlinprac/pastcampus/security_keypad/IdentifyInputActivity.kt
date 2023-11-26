package com.example.kotlinprac.pastcampus.security_keypad

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.example.kotlinprac.BaseActivity
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivityIdentifyInputBinding
import com.example.kotlinprac.pastcampus.security_keypad.util.ViewUtil.hideKeyboard
import com.example.kotlinprac.pastcampus.security_keypad.util.ViewUtil.setOnEditorActionListener
import com.example.kotlinprac.pastcampus.security_keypad.util.ViewUtil.showKeyboard
import com.example.kotlinprac.pastcampus.security_keypad.util.ViewUtil.showKeyboardDelay

class IdentifyInputActivity :
    BaseActivity<ActivityIdentifyInputBinding>(R.layout.activity_identify_input) {

    companion object {
        private const val REGEX_NAME = "^[가-힣]{2,}\$" // 2글자 이상 한글만 허용
        private const val REGEX_BIRTHDAY = "^(19|20)[0-9]{2}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])"
        private const val REGEX_PHONE = "^01([016789])([0-9]{3,4})([0-9]{4})"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind {
            view = this@IdentifyInputActivity
            initView()
            binding.nameEdit.showKeyboardDelay()
        }
    }

    private fun initView() = binding.run {
        nameEdit.setOnEditorActionListener(EditorInfo.IME_ACTION_NEXT) {
            if (validName()) {
                nameLayout.error = null
                if (phoneLayout.isVisible) {
                    confirmButton.isVisible = true
                } else {
                    birthdayLayout.isVisible = true
                    birthdayEdit.showKeyboard()
                }
            } else {
                confirmButton.isVisible = false
                nameLayout.error = "1자 이상의 한글을 입력해 주세요"
            }
        }

        birthdayEdit.doAfterTextChanged {
            // 입력이 될 때마다
            if (birthdayEdit.length() > 7) {
                if (validBirthday()) {
                    birthdayLayout.error = null
                    if (phoneLayout.isVisible) {
                        confirmButton.isVisible = true
                    } else {
                        genderLayout.isVisible = true
                        birthdayEdit.hideKeyboard()
                    }
                } else {
                    confirmButton.isVisible = false
                    birthdayLayout.error = "생년월일 형식이 다릅니다"
                }
            }
        }

        birthdayEdit.setOnEditorActionListener(EditorInfo.IME_ACTION_DONE) {
            val isValid = validBirthday() && birthdayEdit.length() > 7
            if (isValid) {
                // phoneLayout이 보일 때만 표시
                confirmButton.isVisible = phoneLayout.isVisible
                birthdayLayout.error = null
            } else {
                birthdayLayout.error = "생년월일 형식이 다릅니다"
            }

            birthdayEdit.hideKeyboard()
        }

        genderChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            if (!telecomLayout.isVisible) {
                telecomLayout.isVisible = true
            }
        }

        telecomChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            if (!phoneLayout.isVisible) {
                phoneLayout.isVisible = true
                phoneEdit.showKeyboard()
            }
        }

        phoneEdit.doAfterTextChanged {
            if (phoneEdit.length() > 10) {
                if (validPhone()) {
                    phoneLayout.error = null
                    confirmButton.isVisible = true
                    phoneEdit.hideKeyboard()
                } else {
                    confirmButton.isVisible = false
                    phoneLayout.error = "전화번호 형식이 다릅니다"
                }
            }
        }

        phoneEdit.setOnEditorActionListener(EditorInfo.IME_ACTION_DONE) {
            confirmButton.isVisible = phoneEdit.length() > 9 && validPhone()
            phoneEdit.hideKeyboard()
        }
    }

    fun onClickDone() {
        if (!validName()) {
            binding.nameLayout.error = "1자 이상의 한글을 입력해 주세요"
            return
        }

        if (!validBirthday()) {
            binding.birthdayLayout.error = "생년월일 형식이 다릅니다"
            return
        }

        if (!validPhone()) {
            binding.phoneLayout.error = "전화번호 형식이 다릅니다"
            return
        }

        startActivity(Intent(this, VerifyOtpActivity::class.java))
    }

    private fun validName() = !binding.nameEdit.text.isNullOrBlank() &&
            REGEX_NAME.toRegex().matches(binding.nameEdit.text!!)

    private fun validBirthday() = !binding.birthdayEdit.text.isNullOrBlank() &&
            REGEX_BIRTHDAY.toRegex().matches(binding.birthdayEdit.text!!)

    private fun validPhone() = !binding.phoneEdit.text.isNullOrBlank() &&
            REGEX_PHONE.toRegex().matches(binding.phoneEdit.text!!)
}