package com.example.kotlinprac.pastcampus.wallet

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.annotation.IdRes
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.example.kotlinprac.BaseActivity
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivityWalletMainBinding
import timber.log.Timber

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
                        openDetail(thirdCard, thirdCardTitleTextView.text)
                    } else {
                        root.setTransition(R.id.thirdCardOnTop, endStateId)
                        root.transitionToEnd()
                    }
                }
                R.id.secondCardOnTop -> {
                    if (R.id.secondCardOnTop == endStateId) {
                        openDetail(secondCard, secondCardTitleTextView.text)
                    } else {
                        root.setTransition(R.id.secondCardOnTop, endStateId)
                        root.transitionToEnd()
                    }
                }
                R.id.firstCardOnTop -> {
                    if (R.id.firstCardOnTop == endStateId) {
                        openDetail(firstCard, firstCardTitleTextView.text)
                    } else {
                        root.setTransition(R.id.firstCardOnTop, endStateId)
                        root.transitionToEnd()
                    }
                }
            }
        }
    }

    private fun openDetail(view: View, cardName: CharSequence) {
        view.transitionName = "card" // XML과 동일하게 설정
        val optionsCompat =
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                Pair(view, view.transitionName)
            )
        Timber.e("## view.backgroundTintList : ${view.backgroundTintList}")
        WalletDetailActivity.start(this, cardName.toString(), view.backgroundTintList, optionsCompat)
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