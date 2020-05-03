package com.mycompany.coolkidapp.ui.videoplayer

import com.mycompany.coolkidapp.network.response.GetPlaylistResponse

class PlaylistContract {
    interface View {
        fun getPlaylistSuccess(response: GetPlaylistResponse)

        fun apiFailure(failureMessage : String)
    }

    interface Presenter {
        fun getPlaylist(categoryList : List<String>)
    }
}