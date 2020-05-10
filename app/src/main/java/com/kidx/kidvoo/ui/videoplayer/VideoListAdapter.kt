package com.kidx.kidvoo.ui.videoplayer

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubeThumbnailLoader
import com.google.android.youtube.player.YouTubeThumbnailView
import com.kidx.kidvoo.Config
import com.kidx.kidvoo.R
import com.kidx.kidvoo.databinding.VideoListItemBinding
import com.kidx.kidvoo.model.VideoItem
import com.squareup.picasso.Picasso

class VideoListAdapter(private var videoUrlList: ArrayList<VideoItem>, private var context: Context,
                       private var itemClickInterface: ItemClickInterface
) : RecyclerView.Adapter<VideoListAdapter.VideoItemHolder>() {

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
        holder.setIsRecyclable(false)
        if(videoUrlList[position].isPlaying) {
            holder.binding.tvTitle.setTextColor(context.resources.getColor(R.color.colorPrimaryDark))
            holder.binding.tvTitle.typeface = Typeface.DEFAULT_BOLD
        }

        Picasso.get()
            .load(videoUrlList[position].thumbnailUrl)
            .placeholder(R.color.white)
            .into(holder.binding.ytThumbnail)

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