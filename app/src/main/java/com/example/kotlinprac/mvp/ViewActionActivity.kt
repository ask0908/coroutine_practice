package com.example.kotlinprac.mvp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlinprac.databinding.ActivityViewActionBinding

class ViewActionActivity : AppCompatActivity() {

    private lateinit var presenter: ViewActionPresenter
    private val binding: ActivityViewActionBinding by lazy {
        ActivityViewActionBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        presenter = ViewActionPresenter(this)
        presenter.getUserInformation()

        binding.run {
            button.setOnClickListener {
                presenter.updateUI("호출")
            }
        }
    }

}