package com.kidx.kidvoo.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubeThumbnailLoader
import com.google.android.youtube.player.YouTubeThumbnailView
import com.kidx.kidvoo.Config
import com.kidx.kidvoo.R
import com.kidx.kidvoo.databinding.ThumbnailListItemBinding
import com.kidx.kidvoo.model.ThumbnailItem

class ThumbnailListAdapter(private var thumbnailList: ArrayList<ThumbnailItem>,
                           private var context: Context,
                           private var thumbItemClickInterface: ThumbItemClickInterface) :
    RecyclerView.Adapter<ThumbnailListAdapter.ThumbItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThumbItemHolder {
        val binding = DataBindingUtil.inflate<ThumbnailListItemBinding>(LayoutInflater.from(context),
            R.layout.thumbnail_list_item, parent, false)
        return ThumbItemHolder(binding)
    }

    override fun getItemCount(): Int {
        return thumbnailList.size
    }

    override fun onBindViewHolder(holder: ThumbItemHolder, position: Int) {
        holder.populateView(thumbnailList[position].title)
        holder.setIsRecyclable(false)
        holder.binding.ytThumbnail.initialize(Config.YOUTUBE_API_KEY, object : YouTubeThumbnailView.OnInitializedListener {
            override fun onInitializationSuccess(thumbView: YouTubeThumbnailView?, loader: YouTubeThumbnailLoader?) {
                loader?.setVideo(thumbnailList[position].url)
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
        holder.binding.rootLayout.setOnClickListener {
            thumbItemClickInterface.onItemClick(thumbnailList[position].categoryCode)
        }
    }

    class ThumbItemHolder(var binding: ThumbnailListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun populateView(categoryTitle: String) {
            binding.tvVideoTitle.text = categoryTitle
        }
    }

    interface ThumbItemClickInterface {
        fun onItemClick(categoryCode: String)
    }

}