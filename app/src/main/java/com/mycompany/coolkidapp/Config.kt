package com.mycompany.coolkidapp

class Config {
    companion object {
        val YOUTUBE_API_KEY = "AIzaSyAQEdw2wOTswvT-IWVxDJl3h9U_51VPvKI"

        val VIDEO_PLAYBACK_TIME = "VIDEO_PLAYBACK_TIME"
        val VIDEO_NUM = "VIDEO_NUM"

        // Intent Extras
        val EXTRA_CATEGORY_CODE = "EXTRA_CATEGORY_CODE"

        // Network Extra
        val BASE_URL = "http://192.168.0.108:8001/coolKids/"

        // API Constants
        const val GET_CATEGORIES = "getCategories"
        const val GET_PLAYLIST = "getPlaylist"
    }
}