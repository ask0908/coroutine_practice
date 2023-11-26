package com.example.kotlinprac.pastcampus.search_media

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.FragmentFavoriteBinding
import com.example.kotlinprac.pastcampus.search_media.list.ListAdapter

class FavoriteFragment : Fragment() {

    private var binding: FragmentFavoriteBinding? = null
    private val adapter by lazy { ListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentFavoriteBinding.inflate(inflater, container, false).apply {
            binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            recyclerView.adapter = adapter
        }
    }

    // 프래그먼트는 onDestroyView()에서 바인딩 객체 해제 필요
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}