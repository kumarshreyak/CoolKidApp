package com.mycompany.coolkidapp.ui.videoplayer

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubeThumbnailLoader
import com.google.android.youtube.player.YouTubeThumbnailView
import com.mycompany.coolkidapp.Config
import com.mycompany.coolkidapp.R
import com.mycompany.coolkidapp.databinding.VideoListItemBinding
import com.mycompany.coolkidapp.model.VideoItem

class VideoListAdapter(private var videoUrlList: ArrayList<VideoItem>, private var context: Context,
                       private var itemClickInterface: ItemClickInterface
) : RecyclerView.Adapter<VideoListAdapter.VideoItemHolder>() {

    private var numLoaded = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoItemHolder {
        val binding = DataBindingUtil.inflate<VideoListItemBinding>(LayoutInflater.from(context),
            R.layout.video_list_item, parent, false)
        return VideoItemHolder(binding)
    }

    override fun getItemCount(): Int {
        return videoUrlList.size
    }

    override fun onBindViewHolder(holder: VideoItemHolder, position: Int) {
        holder.populateView(videoUrlList[position].title)
        if(numLoaded < itemCount)
            numLoaded ++;
        else
            return
        holder.binding.ytThumbnail.initialize(Config.YOUTUBE_API_KEY, object : YouTubeThumbnailView.OnInitializedListener {
            override fun onInitializationSuccess(thumbView: YouTubeThumbnailView?, loader: YouTubeThumbnailLoader?) {
                loader?.setVideo(videoUrlList[position].url)
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
            ) {
            }
        })

        holder.binding.rootLayout.setOnClickListener { itemClickInterface.onItemClick(position, videoUrlList[position].url) }
    }

    class VideoItemHolder(var binding: VideoListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun populateView(videoName: String) {
            binding.tvTitle.text = videoName
        }
    }

    interface ItemClickInterface {
        fun onItemClick(pos: Int, url: String)
    }

}