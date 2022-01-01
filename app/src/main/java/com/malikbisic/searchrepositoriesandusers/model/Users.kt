package com.malikbisic.searchrepositoriesandusers.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Users(
    @Json(name = "total_count")
    val totalItem: Int?,
    @Json(name = "incomplete_results")
    val incompleteResult: Boolean?,
    val items: List<Author>
)
