package com.example.kotlinprac.youtube

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinprac.databinding.ItemYoutubeBinding

class YoutubeAdapter(
    private val context: Context,
    private val list: ArrayList<YoutubeData>
): RecyclerView.Adapter<YoutubeAdapter.YoutubeViewHolder>() {

    private lateinit var binding: ItemYoutubeBinding

    inner class YoutubeViewHolder(
        private val binding: ItemYoutubeBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: YoutubeData) {
            binding.model = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YoutubeViewHolder {
        binding = ItemYoutubeBinding.inflate(LayoutInflater.from(context), parent, false)
        return YoutubeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: YoutubeViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)

        binding.run {
            youtubePlayerWebView.apply {
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()
                webChromeClient = ChromeClient((context as YoutubeRecyclerViewActivity))
                setInitialScale(0)
                loadUrl(item.videoId)
            }
        }
    }

    override fun getItemCount(): Int = list.size

}