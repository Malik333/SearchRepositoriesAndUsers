package com.malikbisic.searchrepositoriesandusers.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AccessToken(
    @Json(name = "access_token")
    val accessToken: String,
    @Json(name = "token_type")
    val accessType: String
)
