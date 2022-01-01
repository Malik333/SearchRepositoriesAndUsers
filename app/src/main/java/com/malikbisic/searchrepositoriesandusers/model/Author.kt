package com.malikbisic.searchrepositoriesandusers.model

import com.squareup.moshi.Json

data class Author(
    val id: Int?,
    @Json(name = "login")
    val authorName: String?,
    @Json(name = "avatar_url")
    val avatarUrl: String?,
)