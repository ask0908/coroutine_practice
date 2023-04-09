package com.example.kotlinprac.paging.prac

import android.os.Bundle
import android.view.View.GONE
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.kotlinprac.BaseActivity
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivityGithubPagingBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
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
                val query = etSearchQuery.text.toString()
                if (query.isNotEmpty()) {
                    githubViewModel.searchRepos(query)
                }
            }
        }

        lifecycleScope.launch {
            githubViewModel.repoResult.collectLatest { pagingData ->
                repoListAdapter.submitData(pagingData)
            }
        }

        repoListAdapter.addLoadStateListener { loadState ->
            // 에러 상태 처리
            val errorState = when {
                loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                else -> null
            }
            errorState?.let {
                Timber.e("## 에러 : ${it.error}")
                Toast.makeText(this, "\uD83D\uDE28 에러 : ${it.error}", Toast.LENGTH_LONG).show()
            }
        }

    }
}