package com.mycompany.coolkidapp.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.mycompany.coolkidapp.Config.Companion.BASE_URL
import com.mycompany.coolkidapp.Config.Companion.EXTRA_CATEGORY_CODE
import com.mycompany.coolkidapp.R
import com.mycompany.coolkidapp.SampleActivity
import com.mycompany.coolkidapp.databinding.ActivityHomeBinding
import com.mycompany.coolkidapp.model.CategoryItem
import com.mycompany.coolkidapp.model.ThumbnailItem
import com.mycompany.coolkidapp.network.CoolNetworkService
import com.mycompany.coolkidapp.network.response.GetCategoriesResponse
import com.mycompany.coolkidapp.network.response.GetPlaylistResponse
import com.mycompany.coolkidapp.ui.videoplayer.PlaylistActivity

class HomeActivity : AppCompatActivity(), ThumbnailListAdapter.ThumbItemClickInterface, HomeContract.View {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var categoryList: ArrayList<CategoryItem>
    private lateinit var thumbnailList: ArrayList<ThumbnailItem>
    private lateinit var presenter: HomeContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        initPresenter()
        initView()
    }

    private fun initPresenter() {
        presenter = HomePresenter(this, CoolNetworkService.getCoolNetworkService(BASE_URL))

        presenter.getCategories()
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

    }

    override fun onItemClick(categoryCode: String) {
        val intent = Intent(this, PlaylistActivity::class.java)
        intent.putExtra(EXTRA_CATEGORY_CODE, categoryCode)
        startActivity(intent)
    }

    override fun getCategoriesSuccess(response: GetCategoriesResponse) {
        val categoryCodeList = ArrayList<String>()
        for(item in response.responses) {
            categoryCodeList.add(item.categoryCode)
        }
        presenter.getPlaylist(categoryCodeList)
    }

    override fun getPlaylistSuccess(response: GetPlaylistResponse) {
        categoryList = ArrayList()
        for(categoryItem in response.responses) {
            val thumbList = ArrayList<ThumbnailItem>()
            for(thumbItem in categoryItem.playlist) {
                thumbList.add(ThumbnailItem(thumbItem.contentDetails.videoId, thumbItem.snippet.title, categoryItem.categoryCode))
            }
            categoryList.add(CategoryItem(categoryItem.categoryCode, categoryItem.categoryCode, thumbList))
        }
        binding.rvCategoryList.adapter = CategoryListAdapter(categoryList, this, this)
        binding.rvCategoryList.adapter!!.notifyDataSetChanged()
    }

    override fun apiFailure(failureMessage: String) {
        Snackbar.make(binding.root, failureMessage, Snackbar.LENGTH_SHORT).show()
    }
}
