package com.kidx.kidvoo.ui.home

import com.google.gson.Gson
import com.kidx.kidvoo.Config.Companion.GET_CATEGORIES
import com.kidx.kidvoo.Config.Companion.GET_PLAYLIST
import com.kidx.kidvoo.base.BasePresenter
import com.kidx.kidvoo.network.CoolNetworkInterface
import com.kidx.kidvoo.network.request.GetPlaylistRequest
import com.kidx.kidvoo.network.response.GetCategoriesResponse
import com.kidx.kidvoo.network.response.GetPlaylistResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Response

class HomePresenter(var view : HomeContract.View, var apiService: CoolNetworkInterface) : HomeContract.Presenter, BasePresenter() {

    override fun onSuccessResponse(responseBody: Response<ResponseBody>) {
        val url = responseBody.raw().request.url.toString()
        when {
            url.endsWith(GET_CATEGORIES) -> {
                val response = Gson().fromJson(responseBody.body()?.string(), GetCategoriesResponse::class.java)
                view.getCategoriesSuccess(response)
            }
            url.endsWith(GET_PLAYLIST) -> {
                val response = Gson().fromJson(responseBody.body()?.string(), GetPlaylistResponse::class.java)
                view.getPlaylistSuccess(response)
            }
        }
    }

    override fun onFailureResponse(responseBody: Response<ResponseBody>) {
        view.hideProgress()
        val url = responseBody.raw().request.url.toString()
        when {
            url.endsWith(GET_CATEGORIES) -> {
                val response = Gson().fromJson(responseBody.errorBody()?.toString(), GetCategoriesResponse::class.java)
                view.apiFailure(response.responseStatus.responseMessage)
            }
            url.endsWith(GET_PLAYLIST) -> {
                val response = Gson().fromJson(responseBody.errorBody()?.toString(), GetPlaylistResponse::class.java)
                view.apiFailure(response.responseStatus.responseMessage)
            }
        }
    }

    override fun getPlaylist(categoryList : List<String>) {
        apiService.getPlaylist(GetPlaylistRequest(categoryList))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getNewObserver())
    }

    override fun getCategories() {
        apiService.getCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getNewObserver())
    }

}