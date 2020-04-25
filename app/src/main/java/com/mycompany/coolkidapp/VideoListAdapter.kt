package com.mycompany.coolkidapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubeThumbnailLoader
import com.google.android.youtube.player.YouTubeThumbnailView
import com.mycompany.coolkidapp.databinding.VideoListItemBinding

class VideoListAdapter(private var videoUrlList: ArrayList<String>,
                       private var context: Context,
                       private var itemClickInterface: ItemClickInterface) : RecyclerView.Adapter<VideoListAdapter.VideoItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoItemHolder {
        val binding = DataBindingUtil.inflate<VideoListItemBinding>(LayoutInflater.from(context),
            R.layout.video_list_item, parent, false)
        return VideoItemHolder(binding)
    }

    override fun getItemCount(): Int {
        return videoUrlList.size
    }

    override fun onBindViewHolder(holder: VideoItemHolder, position: Int) {
        holder.populateView("Video $position")
        holder.binding.ytThumbnail.initialize(Config.YOUTUBE_API_KEY, object : YouTubeThumbnailView.OnInitializedListener {
            override fun onInitializationSuccess(thumbView: YouTubeThumbnailView?, loader: YouTubeThumbnailLoader?) {
                loader?.setVideo(videoUrlList[position])
                loader?.setOnThumbnailLoadedListener(object : YouTubeThumbnailLoader.OnThumbnailLoadedListener {
                    override fun onThumbnailLoaded(p0: YouTubeThumbnailView?, p1: String?) {
                        loader.release()
                    }

                    override fun onThumbnailError(
                        p0: YouTubeThumbnailView?,
                        p1: YouTubeThumbnailLoader.ErrorReason?
                    ) { }
                })
            }

            override fun onInitializationFailure(
                p0: YouTubeThumbnailView?,
                p1: YouTubeInitializationResult?
            ) { }
        })
        holder.binding.rootLayout.setOnClickListener { itemClickInterface.onItemClick(videoUrlList[position]) }
    }

    class VideoItemHolder(var binding: VideoListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun populateView(videoName: String) {
            binding.tvTitle.text = videoName
        }
    }

    interface ItemClickInterface {
        fun onItemClick(url: String)
    }

}