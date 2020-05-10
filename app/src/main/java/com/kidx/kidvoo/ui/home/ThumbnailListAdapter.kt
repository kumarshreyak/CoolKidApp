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
import com.squareup.picasso.Picasso

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

        Picasso.get()
            .load(thumbnailList[position].url)
            .placeholder(R.color.white)
            .into(holder.binding.ytThumbnail)

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