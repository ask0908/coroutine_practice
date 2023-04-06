package com.example.kotlinprac.networkstate.two.ui

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivityMainBinding
import com.example.kotlinprac.networkstate.two.data.model.Article
import com.example.kotlinprac.networkstate.two.data.network.ApiResponse
import com.example.kotlinprac.networkstate.two.data.network.Status
import com.example.kotlinprac.networkstate.two.ui.custom.ProgressDialog
import com.example.kotlinprac.networkstate.two.utils.EndlessRecyclerViewScrollListener
import com.example.kotlinprac.networkstate.two.utils.ErrorHandler
import dagger.hilt.android.AndroidEntryPoint

/* https://medium.com/@rafiz19/handling-network-call-states-in-kotlin-coroutines-91aff82781a9 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mProgressDialog: Dialog
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private var newsPage = 1
    private var newsAdapter = NewsAdapter(arrayListOf())
    lateinit var scrollListener: EndlessRecyclerViewScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mProgressDialog = ProgressDialog(this)
        setUpRecyclerView()

        mainViewModel.run {
            newsList.observe(this@MainActivity, ::consumeNewsListResult)
            headline.observe(this@MainActivity, ::consumeHeadlinesResult)
            getNewsList(newsPage)
            getHeadlines()
        }

    }

    private fun setUpRecyclerView() {
        val myRecyclerViewManager = LinearLayoutManager(this)
        scrollListener = object : EndlessRecyclerViewScrollListener(myRecyclerViewManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                newsPage++
                mainViewModel.getNewsList(newsPage)
            }
        }
        binding.recyclerNews.apply {
            layoutManager = myRecyclerViewManager
            addOnScrollListener(scrollListener)
            adapter = newsAdapter
        }

        newsAdapter.onItemClick = {
            startActivity(
                Intent(this, WebViewActivity::class.java)
                    .putExtra("articleUrl", it.url)
            )
        }
    }

    private fun bindHeadline(headline: Article?) {
        Glide.with(this)
            .load(headline?.urlToImage)
            .placeholder(R.drawable.ic_placeholder)
            .into(binding.imgHeadline)
        binding.txtTitle.text = headline?.title
        binding.txtDate.text = headline?.publishedAt
        binding.rltHeadlineContainer.setOnClickListener {
            startActivity(
                Intent(this, WebViewActivity::class.java)
                    .putExtra("articleUrl", headline?.url)
            )
        }
    }


    private fun consumeNewsListResult(resource: ApiResponse<List<Article>>) {
        when (resource.status) {
            Status.LOADING -> showLoading()
            Status.ERROR -> {
                hideLoading()
                ErrorHandler(this).handleError(resource.throwable)
            }
            Status.SUCCESS -> {
                hideLoading()
                newsAdapter.addList(resource.data.orEmpty())
            }
        }
    }


    private fun consumeHeadlinesResult(resource: ApiResponse<Article>) {
        when (resource.status) {
            Status.LOADING -> showLoading()
            Status.ERROR -> {
                hideLoading()
                ErrorHandler(this).handleError(resource.throwable)
            }
            Status.SUCCESS -> {
                hideLoading()
                bindHeadline(resource.data)
            }
        }
    }

    private fun showLoading() = mProgressDialog.show()
    private fun hideLoading() = mProgressDialog.cancel()

}