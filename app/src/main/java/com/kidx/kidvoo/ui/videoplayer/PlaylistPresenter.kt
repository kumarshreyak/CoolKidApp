package com.kidx.kidvoo.ui.videoplayer

import com.google.gson.Gson
import com.kidx.kidvoo.Config
import com.kidx.kidvoo.base.BasePresenter
import com.kidx.kidvoo.network.CoolNetworkInterface
import com.kidx.kidvoo.network.request.GetPlaylistRequest
import com.kidx.kidvoo.network.response.GetPlaylistResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Response

class PlaylistPresenter(var view : PlaylistContract.View,
                        var apiService: CoolNetworkInterface) :
    BasePresenter(), PlaylistContract.Presenter {

    override fun onSuccessResponse(responseBody: Response<ResponseBody>) {
        val url = responseBody.raw().request.url.toString()
        when {
            url.endsWith(Config.GET_PLAYLIST) -> {
                val response = Gson().fromJson(responseBody.body()?.string(), GetPlaylistResponse::class.java)
                view.getPlaylistSuccess(response)
            }
        }
    }

    override fun onFailureResponse(responseBody: Response<ResponseBody>) {
        val url = responseBody.raw().request.url.toString()
        when {
            url.endsWith(Config.GET_PLAYLIST) -> {
                view.apiFailure(responseBody.errorBody().toString())
            }
        }
    }

    override fun getPlaylist(categoryList: List<String>) {
        apiService.getPlaylist(GetPlaylistRequest(categoryList))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getNewObserver())
    }

}