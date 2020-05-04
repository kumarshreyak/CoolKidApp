package com.kidx.kidvoo.network

import com.kidx.kidvoo.Config.Companion.GET_CATEGORIES
import com.kidx.kidvoo.Config.Companion.GET_PLAYLIST
import com.kidx.kidvoo.network.request.GetPlaylistRequest
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface CoolNetworkInterface {

    @GET(GET_CATEGORIES)
    fun getCategories(): Observable<Response<ResponseBody>>

    @POST(GET_PLAYLIST)
    fun getPlaylist(@Body request: GetPlaylistRequest): Observable<Response<ResponseBody>>
}