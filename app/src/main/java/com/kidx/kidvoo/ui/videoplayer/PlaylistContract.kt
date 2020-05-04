package com.kidx.kidvoo.ui.videoplayer

import com.kidx.kidvoo.network.response.GetPlaylistResponse

class PlaylistContract {
    interface View {
        fun getPlaylistSuccess(response: GetPlaylistResponse)

        fun apiFailure(failureMessage : String)
    }

    interface Presenter {
        fun getPlaylist(categoryList : List<String>)
    }
}