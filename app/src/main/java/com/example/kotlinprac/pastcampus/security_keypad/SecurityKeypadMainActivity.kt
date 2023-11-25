package com.example.kotlinprac.pastcampus.security_keypad

import android.content.Intent
import android.os.Bundle
import com.example.kotlinprac.BaseActivity
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivitySecurityKeypadMainBinding

class SecurityKeypadMainActivity :
    BaseActivity<ActivitySecurityKeypadMainBinding>(R.layout.activity_security_keypad_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.view = this
    }

    fun openShuffle() {
        startActivity(Intent(this, PinActivity::class.java))
    }

    fun openVerifyOtp() {
        //
    }
}