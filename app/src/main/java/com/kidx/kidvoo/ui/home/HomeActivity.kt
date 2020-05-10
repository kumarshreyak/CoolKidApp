package com.kidx.kidvoo.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.kidx.kidvoo.Config.Companion.BASE_URL
import com.kidx.kidvoo.Config.Companion.EXTRA_CATEGORY_CODE
import com.kidx.kidvoo.R
import com.kidx.kidvoo.databinding.ActivityHomeBinding
import com.kidx.kidvoo.model.CategoryItem
import com.kidx.kidvoo.model.ThumbnailItem
import com.kidx.kidvoo.network.CoolNetworkService
import com.kidx.kidvoo.network.response.GetCategoriesResponse
import com.kidx.kidvoo.network.response.GetPlaylistResponse
import com.kidx.kidvoo.ui.videoplayer.PlaylistActivity

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

    override fun showProgress() {
        binding.rvCategoryList.visibility = GONE
        binding.progressBar.visibility = VISIBLE
    }

    override fun hideProgress() {
        binding.rvCategoryList.visibility = VISIBLE
        binding.progressBar.visibility = GONE
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
                thumbList.add(ThumbnailItem(thumbItem.thumbnailUrl, thumbItem.title, categoryItem.categoryCode))
            }
            categoryList.add(CategoryItem(categoryItem.categoryName, categoryItem.categoryCode, thumbList))
        }
        hideProgress()
        binding.rvCategoryList.adapter = CategoryListAdapter(categoryList, this, this)
        binding.rvCategoryList.adapter!!.notifyDataSetChanged()
    }

    override fun apiFailure(failureMessage: String) {
        Snackbar.make(binding.root, failureMessage, Snackbar.LENGTH_SHORT).show()
    }
}
