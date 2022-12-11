package com.example.kotlinprac.room

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.FragmentItemDetailBinding
import com.example.kotlinprac.room.data.Item
import com.example.kotlinprac.room.data.getFormattedPrice
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ItemDetailFragment : Fragment() {

    private var _binding: FragmentItemDetailBinding? = null
    private val binding get() = _binding!!
    private val navigationArgs: ItemDetailFragmentArgs by navArgs()

    lateinit var item: Item
    private val viewModel: InventoryViewModel by activityViewModels {
        InventoryViewModelFactory(
            // database는 InventoryApplication에 정의한 프로퍼티다. Room DB 객체를 가져온 다음 인터페이스를 리턴하는 함수를 호출한다
            // 인터페이스에 정의한 Room DB 함수를 호출하기 위해서다
            (activity?.application as InventoryApplication).database.itemDao()
        )
    }

    private fun bind(item: Item) {
        binding.apply {
            itemName.text = item.itemName
            itemPrice.text = item.getFormattedPrice()
            itemCount.text = item.quantityInStock.toString()

            sellItem.isEnabled = viewModel.isStockAvailable(item)
            sellItem.setOnClickListener {
                viewModel.sellItem(item)
            }

            deleteItem.setOnClickListener {
                showConfirmationDialog()
            }

            editItem.setOnClickListener {
                editItem()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.itemId
        // id를 넘겨 Room DB에서 해당 아이템의 정보를 가져오는 절차를 관찰시킨 다음 가져온 데이터들을 뷰에 바인딩한다
        // 지연 초기화할 예정이었던 item에 값이 담김으로써 선택한 아이템의 수정, 삭제가 가능해진다
        viewModel.retrieveItem(id).observe(this.viewLifecycleOwner) { selectedItem ->
            item = selectedItem
            bind(item)
        }
    }

    /**
     * Displays an alert dialog to get the user's confirmation before deleting the item.
     */
    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteItem()
            }
            .show()
    }

    /**
     * Deletes the current item and navigates to the list fragment.
     */
    private fun deleteItem() {
        viewModel.deleteItem(item)
        findNavController().navigateUp()
    }

    /* 수정할 때는 fragment_add_item.xml을 재사용한다. 프래그먼트의 제목 문자열을 아이템 id와 같이 전송한다 */
    private fun editItem() {
        val action = ItemDetailFragmentDirections.actionItemDetailFragmentToAddItemFragment(
            getString(R.string.edit_fragment_title),    // "Edit Item"
            item.id
        )
        this.findNavController().navigate(action)
    }

    /**
     * Called when fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}