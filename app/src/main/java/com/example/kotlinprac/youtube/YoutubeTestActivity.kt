package com.example.kotlinprac.youtube

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivityYoutubeTestBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController

const val VIDEO_ID = "egTPUCVRIUI"

class YoutubeTestActivity : AppCompatActivity() {

    private val TAG = this.javaClass.simpleName
    private lateinit var binding: ActivityYoutubeTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_youtube_test)
        binding.run {
            val options = IFramePlayerOptions.Builder().controls(0).build()
            youTubePlayerView.enableAutomaticInitialization = false

            val listener: YouTubePlayerListener = object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    // using pre-made custom ui
                    val defaultPlayerUiController = DefaultPlayerUiController(binding.youTubePlayerView, youTubePlayer)
                    binding.youTubePlayerView.setCustomPlayerUi(defaultPlayerUiController.rootView)
                    youTubePlayer.cueVideo(VIDEO_ID, 0F)
                }
            }
            youTubePlayerView.initialize(listener, options)
            youTubePlayerView.addFullScreenListener(object : YouTubePlayerFullScreenListener {
                override fun onYouTubePlayerEnterFullScreen() {
                    youTubePlayerView.enterFullScreen()
                }

                override fun onYouTubePlayerExitFullScreen() {
                    youTubePlayerView.exitFullScreen()
                }
            })
        }
    }

}