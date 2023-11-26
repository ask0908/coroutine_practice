package com.example.kotlinprac.pastcampus.security_keypad

import android.content.Intent
import android.os.Bundle
import com.example.kotlinprac.BaseActivity
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivitySecurityKeypadMainBinding
import timber.log.Timber

class SecurityKeypadMainActivity :
    BaseActivity<ActivitySecurityKeypadMainBinding>(R.layout.activity_security_keypad_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val helper = AppSignatureHelper(this)
        val hash = helper.getAppSignatures()?.get(0)
        /* 이 값을 Running Devices 화면의 옵션 버튼 > Phone > SMS message 란에 입력한다
        * <#> [Sample] 본인확인 인증번호 [123456] 입니다. <여기 입력> */
        Timber.e("## hash : $hash")
        binding.view = this
    }

    fun openShuffle() {
        startActivity(Intent(this, PinActivity::class.java))
    }

    fun openVerifyOtp() {
        startActivity(Intent(this, IdentifyInputActivity::class.java))
    }
}