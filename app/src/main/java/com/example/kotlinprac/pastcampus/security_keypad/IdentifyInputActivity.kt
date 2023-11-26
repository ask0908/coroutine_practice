package com.example.kotlinprac.pastcampus.security_keypad

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
            birthdayLayout.isVisible = true
            birthdayEdit.showKeyboard()
        }

        birthdayEdit.doAfterTextChanged {
            // 입력이 될 때마다
            if (birthdayEdit.length() > 7) {
                genderLayout.isVisible = true
                birthdayEdit.hideKeyboard()
            }
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
                confirmButton.isVisible = true
                phoneEdit.hideKeyboard()
            }
        }

        phoneEdit.setOnEditorActionListener(EditorInfo.IME_ACTION_DONE) {
            if (phoneEdit.length() > 9) {
                confirmButton.isVisible = true
                phoneEdit.hideKeyboard()
            }
        }
    }
}