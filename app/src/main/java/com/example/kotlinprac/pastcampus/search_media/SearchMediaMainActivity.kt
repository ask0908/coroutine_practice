package com.example.kotlinprac.pastcampus.search_media

import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import com.example.kotlinprac.BaseActivity
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivitySearchMediaMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SearchMediaMainActivity :
    BaseActivity<ActivitySearchMediaMainBinding>(R.layout.activity_search_media_main) {

    // 프래그먼트 변수를 전역으로 둬야 검색 시 프래그먼트에 전달해야 해서 전역으로 관리함
    private val searchFragment = SearchFragment()
    private val fragmentList = listOf(searchFragment, FavoriteFragment())
    private val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle, fragmentList)
    private var observableTextQuery: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.toolbar)) // 프로젝트 전체적으로 NoActionBar를 적용해서 이 화면에서만 툴바 표시
        bind {
            view = this@SearchMediaMainActivity
            initView()
        }
    }

    private fun initView() {
        binding.run {
            view = this@SearchMediaMainActivity
            viewPager.adapter = adapter

            // 뷰페이저를 MainActivity와 연결
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = if (fragmentList[position] is SearchFragment) {
                    "검색 결과"
                } else {
                    "즐겨찾기"
                }
            }.attach()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        observableTextQuery?.dispose()
        observableTextQuery = null
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        observableTextQuery = Observable.create { emitter: ObservableEmitter<String>? ->
            ((menu.findItem(R.id.search)).actionView as SearchView).apply {
                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        // 입력 후 검색 버튼을 누를 때 호출
                        emitter?.onNext(query)
                        return false
                    }

                    override fun onQueryTextChange(newText: String): Boolean {
                        // searchView에서 글자가 바뀔 때마다 호출
                        binding.viewPager.setCurrentItem(0, true)
                        emitter?.onNext(newText)
                        return false
                    }
                })
            }
        }
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                searchFragment.searchKeyword(it)
            }
        return true
    }
}