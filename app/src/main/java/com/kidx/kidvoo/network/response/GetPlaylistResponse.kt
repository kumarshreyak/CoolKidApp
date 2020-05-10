package com.kidx.kidvoo.network.response

data class GetPlaylistResponse(
    val responseStatus: ResponseStatus,
    val responses: List<Response>
)

data class Playlist(
    val id: String,
    val title: String,
    val videoId: String,
    val categoryCode: String,
    val thumbnailUrl: String
)

data class Response(
    val categoryCode: String,
    val categoryName: String,
    val playlist: List<Playlist>
)