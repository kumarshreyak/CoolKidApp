package com.kidx.kidvoo.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kidx.kidvoo.R
import com.kidx.kidvoo.databinding.CategoryListItemBinding
import com.kidx.kidvoo.databinding.VideoListItemBinding
import com.kidx.kidvoo.model.CategoryItem

class CategoryListAdapter(private var categoryList: ArrayList<CategoryItem>, private var context: Context,
                          private var clickInterface: ThumbnailListAdapter.ThumbItemClickInterface
) : RecyclerView.Adapter<CategoryListAdapter.CategoryItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemHolder {
        val binding = DataBindingUtil.inflate<CategoryListItemBinding>(LayoutInflater.from(context),
            R.layout.category_list_item, parent, false)
        return CategoryItemHolder(binding)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryItemHolder, position: Int) {
        holder.populateView(categoryList[position].categoryTitle)
        holder.binding.rvThumbnailList.adapter =
            ThumbnailListAdapter(categoryList[position].thumbnailList, context, clickInterface)
    }

    class CategoryItemHolder(var binding: CategoryListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun populateView(categoryTitle: String) {
            binding.tvCategoryTitle.text = categoryTitle
        }
    }

}