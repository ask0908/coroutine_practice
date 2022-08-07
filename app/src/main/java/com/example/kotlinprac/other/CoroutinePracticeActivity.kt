package com.example.kotlinprac.other

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.kotlinprac.R
import kotlinx.coroutines.launch

class CoroutinePracticeActivity : AppCompatActivity() {

    private val TAG = this.javaClass.simpleName

    private lateinit var viewModel: GithubViewModel
    private lateinit var viewModelFactory: GithubViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine_practice)

        viewModelFactory = GithubViewModelFactory(GithubRepository())
        viewModel = ViewModelProvider(this, viewModelFactory)[GithubViewModel::class.java]

        getGithubRepositories("retrofit")
    }

    private fun getGithubRepositories(query: String) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.apply {
                    requestGithubRepositories(query)
                    githubRepositories.collect {
                        when (it) {
                            is ApiState.Success -> {
                                it.data?.let { data ->
                                    Log.e(TAG, "data - incomplete_results : ${data.incompleteResults}")
                                    val list = data.items
                                    for (i in list.indices) {
                                        Log.e(TAG, "license : ${list[i].license}")
                                    }
                                }
                                mGithubRepositories.value = ApiState.Loading()
                            }
                            is ApiState.Error -> {
                                Log.e(TAG, "## 에러 : ${it.message}")
                                mGithubRepositories.value = ApiState.Loading()
                            }
                            is ApiState.Loading -> {}
                        }
                    }
                }
            }
        }
    }

}