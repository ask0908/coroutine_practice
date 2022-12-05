package com.example.kotlinprac.eventbus

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.kotlinprac.R
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class EventBusSecondFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_event_bus_second, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
        Log.e("2번 프래그먼트", "이벤트 등록")
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun getUser2(user: User) {
        Toast.makeText(requireActivity(), "2번 프래그먼트에서 받은 user : ${user.name}", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
        Log.e("2번 프래그먼트", "이벤트 해제")
    }

}