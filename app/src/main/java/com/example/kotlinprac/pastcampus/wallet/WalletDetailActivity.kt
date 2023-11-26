package com.example.kotlinprac.pastcampus.wallet

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import com.example.kotlinprac.BaseActivity
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivityWalletDetailBinding
import com.example.kotlinprac.pastcampus.wallet.model.DetailItem
import com.example.kotlinprac.pastcampus.wallet.model.Type
import timber.log.Timber
import java.util.Calendar

class WalletDetailActivity :
    BaseActivity<ActivityWalletDetailBinding>(R.layout.activity_wallet_detail) {

    companion object {
        private const val CARD_NAME = "CARD_NAME"
        private const val CARD_COLOR = "CARD_COLOR"

        fun start(
            context: Context,
            cardName: String,
            cardColor: ColorStateList?,
            optionsCompat: ActivityOptionsCompat
        ) {
            Intent(context, WalletDetailActivity::class.java).apply {
                putExtra(CARD_NAME, cardName)
                putExtra(CARD_COLOR, cardColor)
            }.run {
                context.startActivity(this, optionsCompat.toBundle())
            }
        }
    }

    private val adapter = DetailListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        adapter.submitList(mockData())
    }

    private fun initView() = binding.run {
        cardTitleTextView.text = intent.getStringExtra(CARD_NAME)
        binding.cardLayout.backgroundTintList =
            intent.getParcelableExtra(CARD_COLOR) as? ColorStateList
        binding.recyclerView.adapter = adapter
    }

    private fun mockData(): List<DetailItem> {
        fun createDate(year: Int, month: Int, day: Int) = Calendar.getInstance().apply {
            set(year, month, day)
        }.time

        val list = mutableListOf<DetailItem>().apply {
            add(DetailItem(
                1,
                createDate(2023, 1, 6),
                "A상점",
                24000,
                Type.PAY
            ))
            add(DetailItem(
                2,
                createDate(2023, 1, 6),
                "B상점",
                121200,
                Type.PAY
            ))
            add(DetailItem(
                3,
                createDate(2023, 1, 1),
                "온라인 마트",
                11000,
                Type.CANCEL
            ))
            add(DetailItem(
                4,
                createDate(2023, 1, 1),
                "온라인 마트",
                11000,
                Type.PAY
            ))
            add(DetailItem(
                5,
                createDate(2022, 12, 11),
                "대형 마트",
                11000,
                Type.POINT
            ))
            add(DetailItem(
                6,
                createDate(2022, 12, 11),
                "대형 마트",
                1100000,
                Type.PAY
            ))
            add(DetailItem(
                7,
                createDate(2022, 11, 11),
                "창고형 마트",
                230000,
                Type.CANCEL
            ))
            add(DetailItem(
                8,
                createDate(2022, 11, 11),
                "창고형 마트",
                230000,
                Type.PAY
            ))
            add(DetailItem(
                9,
                createDate(2022, 11, 11),
                "주유소",
                50000,
                Type.PAY
            ))
            add(DetailItem(
                10,
                createDate(2022, 10, 11),
                "식당",
                12000,
                Type.PAY
            ))
        }

        return list
    }
}