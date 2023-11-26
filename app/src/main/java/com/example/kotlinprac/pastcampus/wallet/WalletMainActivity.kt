package com.example.kotlinprac.pastcampus.wallet

import android.os.Bundle
import android.view.View.OnClickListener
import androidx.annotation.IdRes
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import com.example.kotlinprac.BaseActivity
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivityWalletMainBinding

class WalletMainActivity : BaseActivity<ActivityWalletMainBinding>(R.layout.activity_wallet_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind {
            thirdCard.setOnClickListener(getCardClickListener(R.id.thirdCardOnTop))
            secondCard.setOnClickListener(getCardClickListener(R.id.secondCardOnTop))
            firstCard.setOnClickListener(getCardClickListener(R.id.firstCardOnTop))
        }
    }

    private fun getCardClickListener(@IdRes endStateId: Int) = OnClickListener {
        binding.run {
            when (root.currentState) {
                R.id.fanOut -> {
                    root.setTransition(R.id.fanOut, R.id.firstCardOnTop)
                    root.transitionToEnd()
                    collapsedCardCompletedListener(endStateId)
                }
                R.id.thirdCardOnTop -> {
                    if (R.id.thirdCardOnTop == endStateId) {
                        //
                    } else {
                        root.setTransition(R.id.thirdCardOnTop, endStateId)
                        root.transitionToEnd()
                    }
                }
                R.id.secondCardOnTop -> {
                    if (R.id.secondCardOnTop == endStateId) {
                        //
                    } else {
                        root.setTransition(R.id.secondCardOnTop, endStateId)
                        root.transitionToEnd()
                    }
                }
                R.id.firstCardOnTop -> {
                    if (R.id.firstCardOnTop == endStateId) {
                        //
                    } else {
                        root.setTransition(R.id.firstCardOnTop, endStateId)
                        root.transitionToEnd()
                    }
                }
            }
        }
    }

    private fun collapsedCardCompletedListener(@IdRes endStateId: Int) {
        binding.run {
            root.setTransitionListener(object : TransitionAdapter() {
                override fun onTransitionCompleted(motionLayout: MotionLayout, currentId: Int) {
                    if (currentId == R.id.firstCardOnTop) {
                        root.setTransition(R.id.firstCardOnTop, endStateId)
                        root.transitionToEnd()
                    }
                    root.setTransitionListener(null)
                }
            })
        }
    }
}