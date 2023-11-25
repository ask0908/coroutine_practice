package com.example.kotlinprac.pastcampus.security_keypad

import android.os.Bundle
import androidx.activity.viewModels
import com.example.kotlinprac.BaseActivity
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivityPinBinding
import com.example.kotlinprac.pastcampus.security_keypad.widget.ShuffleNumberKeyboard

class PinActivity: BaseActivity<ActivityPinBinding>(R.layout.activity_pin), ShuffleNumberKeyboard.KeyPadListener {

    private val viewModel: PinViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind {
            viewModel = this@PinActivity.viewModel
            shuffleKeyBoard.setKeypadListener(this@PinActivity)
        }
    }

    override fun onClickNum(num: String) = viewModel.input(num)

    override fun onClickDelete() = viewModel.delete()

    override fun onClickDone() = viewModel.done()
}