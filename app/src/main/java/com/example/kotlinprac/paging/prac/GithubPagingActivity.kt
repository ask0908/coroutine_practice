package com.example.kotlinprac.paging.prac

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.kotlinprac.BaseActivity
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivityGithubPagingBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class GithubPagingActivity :
    BaseActivity<ActivityGithubPagingBinding>(R.layout.activity_github_paging) {

    private val githubViewModel: GithubViewModel by viewModels()
    private val repoListAdapter = RepoListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind {
            viewModel = githubViewModel
            lifecycleOwner = this@GithubPagingActivity

            rvRepoList.apply {
                addItemDecoration(
                    DividerItemDecoration(
                        this@GithubPagingActivity,
                        DividerItemDecoration.VERTICAL
                    )
                )
                adapter = repoListAdapter
            }

            etSearchQuery.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val query = etSearchQuery.text.toString()
                    if (query.isNotEmpty()) {
                        githubViewModel.searchRepos(query)
                    }
                    true
                } else {
                    false
                }
            }

            btnSearch.setOnClickListener {
                hideKeyboard(etSearchQuery)
                progressOverlay.clPb.visibility = GONE
                val query = etSearchQuery.text.toString()
                if (query.isNotEmpty()) {
                    githubViewModel.searchRepos(query)
                }
            }
        }

        githubViewModel.repos.observe(this@GithubPagingActivity) { pagingData ->
            lifecycleScope.launch {
                repoListAdapter.submitData(pagingData)
            }
        }

        repoListAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                showProgressBarOverlay()
            } else {
                hideProgressBarOverlay()

                // 에러 상태 처리
                val errorState = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    Toast.makeText(this, "\uD83D\uDE28 Wooops ${it.error}", Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    private fun showProgressBarOverlay() {
        binding.progressOverlay.clPb.visibility = VISIBLE
    }

    private fun hideProgressBarOverlay() {
        binding.progressOverlay.clPb.visibility = GONE
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}