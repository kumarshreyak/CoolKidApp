package com.mycompany.coolkidapp.network.response

data class GetPlaylistResponse(
    val responseStatus: ResponseStatus,
    val responses: List<Response>
)

data class ContentDetails(
    val itemCount: Any,
    val videoId: String,
    val videoPublishedAt: String
)

data class High(
    val height: String,
    val url: String,
    val width: String
)

data class Medium(
    val height: String,
    val url: String,
    val width: String
)

data class Playlist(
    val contentDetails: ContentDetails,
    val id: String,
    val kind: String,
    val snippet: Snippet
)

data class Response(
    val categoryCode: String,
    val playlist: List<Playlist>
)

data class Snippet(
    val channelId: String,
    val channelTitle: String,
    val playlistId: String,
    val publishedAt: String,
    val thumbnails: Thumbnails,
    val title: String
)

data class Standard(
    val height: String,
    val url: String,
    val width: String
)

data class Thumbnails(
    val high: High,
    val medium: Medium,
    val standard: Standard
)