package com.example.kotlinprac.room.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.NumberFormat

/* @Entity : 클래스를 DB Entity 클래스로 표시함. 각 엔티티 클래스에서 DB 테이블이 아이템을 보유하기 위해 만들어짐 */
@Entity(tableName = "item")
data class Item(
    @PrimaryKey(autoGenerate = true)    // id를 PK로 식별하려면 @PrimaryKey 어노테이션 추가. true면 Room에서 각 아이템의 id를 생성함 -> 아이템 별 id가 고유해짐
    val id: Int = 0,
    @ColumnInfo(name = "name")      // @ColumnInfo : 특정 필드와 연결된 열을 맞춤설정할 때 사용. 다른 컬럼명을 필드에 지정할 수 있다
    val itemName: String,
    @ColumnInfo(name = "price")
    val itemPrice: Double,
    @ColumnInfo(name = "quantity")
    val quantityInStock: Int
)

fun Item.getFormattedPrice(): String = NumberFormat.getCurrencyInstance().format(itemPrice)