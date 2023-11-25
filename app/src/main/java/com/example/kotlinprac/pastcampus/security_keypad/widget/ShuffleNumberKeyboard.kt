package com.example.kotlinprac.pastcampus.security_keypad.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.GridLayout
import android.widget.TextView
import androidx.core.view.children
import com.example.kotlinprac.databinding.WidgetShuffleNumberKeyboardBinding
import kotlin.random.Random

class ShuffleNumberKeyboard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): GridLayout(context, attrs, defStyleAttr), View.OnClickListener {

    // 액티비티는 자동으로 바인딩 인스턴스를 제거해서 GC의 대상으로 만들지만 커스텀 뷰의 바인딩 인스턴스는 자동으로 참조가 끊어지지 않아서
    // 뷰가 끝날 때 참조를 끊어야 함
    private var _binding: WidgetShuffleNumberKeyboardBinding? = null
    private val binding
        get() = _binding!!

    private var listener: KeyPadListener? = null

    init {
        _binding = WidgetShuffleNumberKeyboardBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
        binding.view = this
        binding.clickListener = this
        shuffle()
    }

    // 뷰가 끝날 때 바인딩 참조 해제
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _binding = null
    }

    // 키패드 섞어서 숫자 무작위 정렬
    fun shuffle() {
        val keyNumberArray = ArrayList<String>()

        for (i in 0 .. 9) {
            keyNumberArray.add(i.toString())
        }

        binding.gridLayout.children.forEach { view ->
            if (view is TextView && view.tag != null) {
                val randIndex = Random.nextInt(keyNumberArray.size) // 0~10까지 총 10개 (상단 for문 참고)
                view.text = keyNumberArray[randIndex]
                keyNumberArray.removeAt(randIndex) // 리스트에서 해당 인덱스를 지우지 않으면 중복 발생
            }
        }
    }

    fun setKeypadListener(keyPadListener: KeyPadListener) {
        this.listener = keyPadListener
    }

    fun onClickDelete() = listener?.onClickDelete()

    fun onClickDone() = listener?.onClickDone()

    interface KeyPadListener {
        fun onClickNum(num: String)
        fun onClickDelete()
        fun onClickDone()
    }

    override fun onClick(v: View?) {
        if (v is TextView && v.tag != null) {
            listener?.onClickNum(v.text.toString())
        }
    }
}