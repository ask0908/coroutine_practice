package com.example.kotlinprac.room

import androidx.lifecycle.*
import com.example.kotlinprac.room.data.Item
import com.example.kotlinprac.room.data.ItemDao
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class InventoryViewModel(
    private val itemDao: ItemDao
): ViewModel() {
    // InventoryViewModel 클래스 시작부에 Flow를 통해 DB의 모든 아이템들을 가져와 변수에 할당
    val allItems: LiveData<List<Item>> = itemDao.getItems().asLiveData()

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

    // 아이템의 id에 따라 Room DB에서 아이템 세부정보를 검색하는 함수
    fun retrieveItem(id: Int): LiveData<Item> {
        // getItem()은 Flow를 리턴하기 때문에 LiveData로 쓰려면 asLiveData()를 붙여야 한다
        return itemDao.getItem(id).asLiveData()
    }

    private fun updateItem(item: Item) {
        viewModelScope.launch {
            itemDao.update(item)
        }
    }

    fun sellItem(item: Item) {
        if (item.quantityInStock > 0) {
            /* copy() : data class의 모든 인스턴스에서 사용 가능함. 일부 속성을 바꾸지만 나머지 속성은 그대로 두기 위해
            * 객체를 복사할 때 사용된다 */
            // 현재 재고수량을 1 줄인 뒤 이 상태를 Room DB에 반영한다
            val newItem = item.copy(quantityInStock = item.quantityInStock - 1)
            updateItem(newItem)
        }
    }

    /* 재고수량이 0보다 큰지 확인하는 함수. 0일 경우 버튼을 비활성화시켜서 클릭하지 못하게 만들어야 하기 때문에 필요 */
    fun isStockAvailable(item: Item): Boolean {
        return (item.quantityInStock > 0)
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