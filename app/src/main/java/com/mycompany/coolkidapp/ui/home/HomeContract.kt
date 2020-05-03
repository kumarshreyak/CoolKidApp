package com.mycompany.coolkidapp.ui.home

import com.mycompany.coolkidapp.network.response.GetCategoriesResponse
import com.mycompany.coolkidapp.network.response.GetPlaylistResponse

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