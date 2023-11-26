package com.example.kotlinprac.pastcampus.security_keypad

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.example.kotlinprac.BaseActivity
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivityVerifyOtpBinding
import com.example.kotlinprac.pastcampus.security_keypad.util.ViewUtil.setOnEditorActionListener
import com.example.kotlinprac.pastcampus.security_keypad.util.ViewUtil.showKeyboardDelay
import com.google.android.gms.auth.api.phone.SmsRetriever
import timber.log.Timber

class VerifyOtpActivity : BaseActivity<ActivityVerifyOtpBinding>(R.layout.activity_verify_otp),
    AuthOtpReceiver.OtpReceiveListener {

    // 타이머 시간은 3분, 1초마다 찍히게
    private var smsReceiver: AuthOtpReceiver? = null
    private var timer: CountDownTimer? = object : CountDownTimer((3 * 60 * 1000).toLong() , 1000) {
        @SuppressLint("SetTextI18n")
        override fun onTick(millisUntilFinished: Long) {
            val min = (millisUntilFinished / 1000) / 60
            val sec = (millisUntilFinished / 1000) % 60
            binding.timerTextView.text = "$min:${String.format("%02d", sec)}"
        }

        override fun onFinish() {
            binding.timerTextView.text = ""
            Toast.makeText(
                this@VerifyOtpActivity,
                "입력 제한시간을 초과하였습니다.\n다시 시도해주세요.",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind {
            view = this@VerifyOtpActivity
            initView()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.otpCodeEdit.showKeyboardDelay()
        startSmsRetriever()
    }

    override fun onDestroy() {
        clearTimer()
        stopSmsRetriever()
        super.onDestroy()
    }

    private fun initView() {
        startTimer()
        binding.view = this
        with(binding) {
            otpCodeEdit.doAfterTextChanged {
                if (otpCodeEdit.length() >= 6) {
                    stopTimer()
                    Toast.makeText(this@VerifyOtpActivity, "인증이 완료 되었습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            otpCodeEdit.setOnEditorActionListener(EditorInfo.IME_ACTION_DONE) {
                // do not use
            }
        }
    }

    override fun onOtpReceived(otp: String) {
        binding.otpCodeEdit.setText(otp)
    }

    private fun startTimer() {
        timer?.start()
    }

    private fun stopTimer() {
        timer?.cancel()
    }

    private fun clearTimer() {
        stopTimer()
        timer = null
    }

    private fun startSmsRetriever() {
        SmsRetriever.getClient(this).startSmsRetriever().also { task ->
            task.addOnSuccessListener {
                if (smsReceiver == null) {
                    smsReceiver = AuthOtpReceiver().apply {
                        setOtpListener(this@VerifyOtpActivity)
                    }
                }
                registerReceiver(smsReceiver, smsReceiver!!.doFilter())
            }

            task.addOnFailureListener {
                stopSmsRetriever()
            }
        }
    }

    private fun stopSmsRetriever() {
        if (smsReceiver != null) {
            unregisterReceiver(smsReceiver)
            smsReceiver = null
        }
    }
}