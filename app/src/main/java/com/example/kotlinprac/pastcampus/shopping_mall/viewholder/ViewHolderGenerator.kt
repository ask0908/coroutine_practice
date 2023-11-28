package com.example.kotlinprac.pastcampus.shopping_mall.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.kotlinprac.databinding.ItemEmptyBinding
import com.example.kotlinprac.pastcampus.shopping_mall.model.ViewType

// ListAdapter의 createViewHolder()에서 호출할 것. 뷰타입에 맞는 뷰홀더들을 리턴시킬 것
// ListAdapter가 여러 종류일 때 뷰타입에 맞는 뷰홀더를 하나에서 관리하기 위해 이 object처럼 별도로 빼서 관리하기도 함
object ViewHolderGenerator {

    fun get(
        parent: ViewGroup,
        viewType: Int
    ): BindingViewHolder<*> = when(viewType) {
        // 부모 타입
        ViewType.VIEW_PAGER.ordinal -> ViewPagerViewHolder(parent.toBinding())
        ViewType.HORIZONTAL.ordinal -> HorizontalViewHolder(parent.toBinding())
        ViewType.FULL_AD.ordinal -> FullAdViewHolder(parent.toBinding())

        // 자식 타입
        ViewType.COUPON.ordinal -> CouponViewHolder(parent.toBinding())
        ViewType.IMAGE.ordinal -> ImageViewHolder(parent.toBinding())
        ViewType.SELL_ITEM.ordinal -> SellItemViewHolder(parent.toBinding())
        ViewType.SALE.ordinal -> SaleViewHolder(parent.toBinding())
        // itemEmptyBinding이 inflater를 통해 전달됨 -> 따로 바인딩하는 코드를 각각 추가할 필요 없음
        else -> ItemViewHolder(parent.toBinding())
    }

    class ItemViewHolder(binding: ItemEmptyBinding): BindingViewHolder<ItemEmptyBinding>(binding)

    private inline fun <reified V: ViewBinding> ViewGroup.toBinding(): V {
        return V::class.java.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        ).invoke(null, LayoutInflater.from(context), this, false) as V
    }
}