package com.kidx.kidvoo

class Config {
    companion object {
        val YOUTUBE_API_KEY = "AIzaSyDarTy3sL-Qx04qWAZZcrVN4Xw9aNz4BQ0"

        val VIDEO_PLAYBACK_TIME = "VIDEO_PLAYBACK_TIME"
        val VIDEO_NUM = "VIDEO_NUM"

        // Intent Extras
        val EXTRA_CATEGORY_CODE = "EXTRA_CATEGORY_CODE"
        val EXTRA_LIST_POS = "EXTRA_LIST_POS"

        // Network Extra
        val BASE_URL = "https://kidvoo-server.herokuapp.com/coolKids/"

        // API Constants
        const val GET_CATEGORIES = "getCategories"
        const val GET_PLAYLIST = "getPlaylist"
    }
}