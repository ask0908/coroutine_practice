package com.example.kotlinprac.pastcampus.security_keypad

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import timber.log.Timber

/**
 * 0QaSrUKkEGq
 * 1. 문자 내용이 140byte를 초과하면 안 된다.
 * 2. SMS 맨 앞에 <#>가 반드시 포함되어야 한다.
 * 3. SMS 맨 마지막에 앱을 식별하는 11글자 해시 문자열을 포함해야 한다.
 */
class AuthOtpReceiver : BroadcastReceiver() {

    private var otpReceiver: OtpReceiveListener? = null

    override fun onReceive(context: Context, intent: Intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
            intent.extras?.let { bundle ->
                Log.e(this::class.simpleName, "bundle : $bundle")
                val status = bundle.get(SmsRetriever.EXTRA_STATUS) as Status
                Log.e(this::class.java.simpleName, "status : $status")
                when (status.statusCode) {
                    CommonStatusCodes.SUCCESS -> {
                        val otpSms = bundle.getString(SmsRetriever.EXTRA_SMS_MESSAGE, "")
                        if (otpReceiver != null && otpSms.isNotEmpty()) {
                            val otp = PATTERN.toRegex().find(otpSms)?.destructured?.component1()
                            if (!otp.isNullOrEmpty()) {
                                otpReceiver!!.onOtpReceived(otp)
                            }
                        }
                    }
                }
            }
        }
    }

    fun setOtpListener(receiver: OtpReceiveListener) {
        this.otpReceiver = receiver
    }

    fun doFilter() = IntentFilter().apply {
        addAction(SmsRetriever.SMS_RETRIEVED_ACTION)
    }

    interface OtpReceiveListener {
        fun onOtpReceived(otp: String)
    }

    companion object {
        private const val PATTERN = "^<#>.*\\[Sample\\].+\\[(\\d{6})\\].+\$"
    }
}