package com.example.kotlinprac.room

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ItemListFragmentBinding

class ItemListFragment : Fragment() {

    private var _binding: ItemListFragmentBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: InventoryViewModel by activityViewModels {
        InventoryViewModelFactory(
            (activity?.application as InventoryApplication).database.itemDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ItemListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ItemListAdapter {
            // 어댑터를 val에 선언할 때 어댑터 생성자를 이렇게도 초기화 가능하다
        }
        binding.recyclerView.adapter = adapter

        // allItems에 옵저버를 연결해서 데이터 변경사항을 수신 대기한다
        viewModel.allItems.observe(this.viewLifecycleOwner) { items ->
            items.let {
                // 새 아이템이 리스트에 포함되서 리사이클러뷰가 업데이트됨됨
               adapter.submitList(it)
            }
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.floatingActionButton.setOnClickListener {
            val action = ItemListFragmentDirections.actionItemListFragmentToAddItemFragment(
                getString(R.string.add_fragment_title)
            )
            this.findNavController().navigate(action)
        }
    }

}