package com.mycompany.coolkidapp.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.mycompany.coolkidapp.Config.Companion.EXTRA_CATEGORY_CODE
import com.mycompany.coolkidapp.R
import com.mycompany.coolkidapp.SampleActivity
import com.mycompany.coolkidapp.databinding.ActivityHomeBinding
import com.mycompany.coolkidapp.model.CategoryItem
import com.mycompany.coolkidapp.model.ThumbnailItem
import com.mycompany.coolkidapp.ui.videoplayer.PlaylistActivity

class HomeActivity : AppCompatActivity(), ThumbnailListAdapter.ThumbItemClickInterface {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var categoryList: ArrayList<CategoryItem>
    private lateinit var thumbnailList: ArrayList<ThumbnailItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        initData()
        initView()
    }

    private fun initData() {
        thumbnailList = ArrayList()
        categoryList = ArrayList()
        thumbnailList.add(ThumbnailItem("uGrBHohIgQY", "Winning Google Kickstart", "coolCode"))
        thumbnailList.add(ThumbnailItem("uGrBHohIgQY", "Winning Google Kickstart", "coolCode"))
        thumbnailList.add(ThumbnailItem("uGrBHohIgQY", "Winning Google Kickstart", "coolCode"))
        thumbnailList.add(ThumbnailItem("uGrBHohIgQY", "Winning Google Kickstart", "coolCode"))
        categoryList.add(CategoryItem("Nursery Rhymes", "some code", thumbnailList))
        categoryList.add(CategoryItem("Cool Rhymes", "some code", thumbnailList))
        categoryList.add(CategoryItem("Not cool Rhymes", "some code", thumbnailList))
        categoryList.add(CategoryItem("Its Working  Rhymes", "some code", thumbnailList))
    }

    private fun initView() {
        binding.rvCategoryList.adapter = CategoryListAdapter(categoryList, this, this)
    }

    override fun onItemClick(categoryCode: String) {
        val intent = Intent(this, PlaylistActivity::class.java)
        intent.putExtra(EXTRA_CATEGORY_CODE, categoryCode)
        startActivity(intent)
    }
}
