package com.kidx.kidvoo.ui.home

import com.kidx.kidvoo.network.response.GetCategoriesResponse
import com.kidx.kidvoo.network.response.GetPlaylistResponse

class HomeContract {
    interface View {
        fun getCategoriesSuccess(response: GetCategoriesResponse)

        fun getPlaylistSuccess(response: GetPlaylistResponse)

        fun apiFailure(failureMessage : String)
    }

    interface Presenter {
        fun getCategories()

        fun getPlaylist(categoryList : List<String>)
    }
}