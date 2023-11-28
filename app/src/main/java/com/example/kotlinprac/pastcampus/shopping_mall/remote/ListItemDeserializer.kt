package com.example.kotlinprac.pastcampus.shopping_mall.remote

import com.example.kotlinprac.pastcampus.shopping_mall.model.Coupon
import com.example.kotlinprac.pastcampus.shopping_mall.model.Empty
import com.example.kotlinprac.pastcampus.shopping_mall.model.FullAd
import com.example.kotlinprac.pastcampus.shopping_mall.model.Horizontal
import com.example.kotlinprac.pastcampus.shopping_mall.model.Image
import com.example.kotlinprac.pastcampus.shopping_mall.model.ListItem
import com.example.kotlinprac.pastcampus.shopping_mall.model.Sale
import com.example.kotlinprac.pastcampus.shopping_mall.model.SellItem
import com.example.kotlinprac.pastcampus.shopping_mall.model.ViewPager
import com.example.kotlinprac.pastcampus.shopping_mall.model.ViewType
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class ListItemDeserializer: JsonDeserializer<ListItem> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): ListItem {
        val viewTypeName = json.asJsonObject.getAsJsonPrimitive("viewType").asString
        // ListItem 안에 ListItem이 있어서 deserialize하는 부분까지 넣어야 한다. 안 그러면 최상위 뷰타입은 파싱할 수 있지만 자식 뷰타입은 파싱 못함
        val gson = GsonBuilder()
            .registerTypeAdapter(ListItem::class.java, ListItemDeserializer())
            .create()

        return try {
            when (viewTypeName) {
                // data class, viewholder, viewholder generator와 여기만 수정하면 다른 코드를 바꾸지 않아도 대응 가능
                ViewType.VIEW_PAGER.name -> gson.fromJson(json, ViewPager::class.java)
                ViewType.HORIZONTAL.name -> gson.fromJson(json, Horizontal::class.java)
                ViewType.FULL_AD.name -> gson.fromJson(json, FullAd::class.java)

                ViewType.SELL_ITEM.name -> gson.fromJson(json, SellItem::class.java)
                ViewType.IMAGE.name -> gson.fromJson(json, Image::class.java)
                ViewType.SALE.name -> gson.fromJson(json, Sale::class.java)
                ViewType.COUPON.name -> gson.fromJson(json, Coupon::class.java)
                else -> gson.fromJson(json, Empty::class.java)
            }
        } catch (e: Exception) {
            gson.fromJson(json, Empty::class.java)
        }
    }
}