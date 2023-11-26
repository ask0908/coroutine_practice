package com.example.kotlinprac.pastcampus.search_media

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.FragmentSearchBinding
import com.example.kotlinprac.pastcampus.search_media.list.ItemHandler
import com.example.kotlinprac.pastcampus.search_media.list.ListAdapter
import com.example.kotlinprac.pastcampus.search_media.model.ListItem
import com.example.kotlinprac.pastcampus.search_media.repository.SearchRepositoryImpl

class SearchFragment : Fragment() {

    private var binding: FragmentSearchBinding? = null
    private val adapter by lazy { ListAdapter(Handler(viewModel)) }
    private val viewModel: SearchViewModel by viewModels {
        SearchViewModel.SearchViewModelFactory(SearchRepositoryImpl(RetrofitManager.searchService))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentSearchBinding.inflate(inflater, container, false).apply {
            binding = this
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@SearchFragment.viewModel
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            recyclerView.adapter = adapter
        }
        observeViewModel()
    }

    fun searchKeyword(text: String) {
        viewModel.search(text)
    }

    private fun observeViewModel() {
        viewModel.listLiveData.observe(viewLifecycleOwner) {
            binding?.apply {
                if (it.isEmpty()) {
                    emptyTextView.isVisible = true
                    recyclerView.isVisible = false
                } else {
                    emptyTextView.isVisible = false
                    recyclerView.isVisible = true
                }
            }

            adapter.submitList(it)
        }
    }

    // 프래그먼트는 onDestroyView()에서 바인딩 객체 해제 필요
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    class Handler(private val viewModel: SearchViewModel): ItemHandler {
        override fun onClickFavorite(item: ListItem) {
            viewModel.toggleFavorite(item)
        }

    }
}