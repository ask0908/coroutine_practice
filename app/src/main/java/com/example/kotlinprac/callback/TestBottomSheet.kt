package com.example.kotlinprac.callback

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import com.example.kotlinprac.databinding.TestBottomSheetBinding

class TestBottomSheet(context: Context, layoutId: Int): Dialog(context, layoutId) {

    private lateinit var binding: TestBottomSheetBinding
    private lateinit var dialog: Dialog
    private var listener: OnSendFromBottomSheetDialog? = null

    fun showDialog() {
        binding = TestBottomSheetBinding.inflate(LayoutInflater.from(context))
        dialog = setDialogOptions()

        binding.run {
            btnBottomSheetCancel.setOnClickListener {
                if (listener == null) return@setOnClickListener
                listener?.sendValue("BottomSheetDialog에서 취소 버튼 클릭함!")
                dialog.dismiss()
            }

            btnBottomSheetOk.setOnClickListener {
                if (listener == null) return@setOnClickListener
                listener?.sendValue("BottomSheetDialog에서 확인 버튼 클릭함!")
                dialog.dismiss()
            }
        }
    }

    private fun setDialogOptions(): Dialog = Dialog(context).apply {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)
        setCanceledOnTouchOutside(true)
        window?.run {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setGravity(Gravity.BOTTOM)
        }
        show()
    }

    interface OnSendFromBottomSheetDialog {
        fun sendValue(value: String)
    }

    fun setCallback(listener: OnSendFromBottomSheetDialog) {
        this.listener = listener
    }

}