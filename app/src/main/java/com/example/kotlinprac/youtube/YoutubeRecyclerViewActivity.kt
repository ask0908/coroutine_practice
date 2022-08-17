package com.example.kotlinprac.youtube

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivityYoutubeRecyclerViewBinding

class YoutubeRecyclerViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityYoutubeRecyclerViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_youtube_recycler_view)
        binding.run {
            lifecycleOwner = this@YoutubeRecyclerViewActivity

            val youtubeIdList = arrayListOf(
                YoutubeData("http://www.youtube.com/embed/egTPUCVRIUI"),
                YoutubeData("http://www.youtube.com/embed/uXJbN8wEJ5k"),
                YoutubeData("http://www.youtube.com/embed/CfPxlb8-ZQ0"),
                YoutubeData("http://www.youtube.com/embed/XhLyvAKH1Mc"),
            )

            val youtubeAdapter = YoutubeAdapter(this@YoutubeRecyclerViewActivity, youtubeIdList)
            youtubeRecyclerView.apply {
                layoutManager = LinearLayoutManager(this@YoutubeRecyclerViewActivity)
                setHasFixedSize(true)
                adapter = youtubeAdapter
            }
        }
    }
}