package com.example.kotlinprac.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.kotlinprac.room.data.Item
import com.example.kotlinprac.room.data.ItemDao
import kotlinx.coroutines.launch

class InventoryViewModel(
    private val itemDao: ItemDao
): ViewModel() {
    // Item 객체를 비차단 방식으로 DB에 추가
    private fun insertItem(item: Item) {
        // 메인 쓰레드 밖에서 DB와 상호작용하려면 뷰모델 안에서 코루틴을 시작해야 한다. 그러려면 viewModelScope.launch {}를 써서 일시정지 함수를 호출한다
        // viewModelScope : 뷰모델이 소멸할 때 하위 코루틴을 자동 취소하는 뷰모델 클래스의 확장 프로퍼티
        viewModelScope.launch {
            itemDao.insert(item)
        }
    }

    // InventoryViewModel 클래스에서 문자열 3개(이름, 가격, 재고)를 가져오고 Item 인스턴스를 리턴
    private fun getNewItemEntry(itemName: String, itemPrice: String, itemCount: String): Item =
        Item(
            itemName = itemName,
            itemPrice = itemPrice.toDouble(),
            quantityInStock = itemCount.toInt()
        )

    // 이 퍼블릭 함수는 프래그먼트에서 호출되어 새 아이템을 DB에 추가한다다
   fun addNewItem(itemName: String, itemPrice: String, itemCount: String) {
        val newItem = getNewItemEntry(itemName, itemPrice, itemCount)
        insertItem(newItem)
    }

    fun isEntryValid(itemName: String, itemPrice: String, itemCount: String): Boolean {
        if (itemName.isBlank() || itemPrice.isBlank() || itemCount.isBlank()) {
            return false
        }
        return true
    }
}

class InventoryViewModelFactory(
    private val itemDao: ItemDao
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // 매개변수로 받은 modelClass가 InventoryViewModel과 같은지 확인하고 그 인스턴스 반환
        require(modelClass.isAssignableFrom(InventoryViewModel::class.java)) {
            throw IllegalArgumentException("Unknown ViewModel class")
        }

        @Suppress("UNCHECKED_CAST")
        return InventoryViewModel(itemDao) as T
    }
}