package com.example.kotlinprac.rx

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import com.example.kotlinprac.BaseActivity
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivityRxKotlinTestBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import timber.log.Timber

@AndroidEntryPoint
class RxKotlinTestActivity :
    BaseActivity<ActivityRxKotlinTestBinding>(R.layout.activity_rx_kotlin_test) {

    private val viewModel: RxGithubViewModel by viewModels()

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getRepositoryNames("ask0908")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { repoNames -> Timber.e("수신 : $repoNames") },
                { error -> Timber.e("에러 : $error") },
                { Timber.e("## 레포 이름만 가져오기 완료") }
            )
    }
}