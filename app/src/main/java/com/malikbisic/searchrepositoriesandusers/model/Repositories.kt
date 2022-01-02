package com.malikbisic.searchrepositoriesandusers.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Repositories(
    @Json(name = "total_count")
    val totalItem: Int?,
    @Json(name = "incomplete_results")
    val incompleteResult: Boolean?,
    val items: List<Repository>
)

data class Repository(
    val id: Int?,
    @Json(name = "name")
    val repositoryName: String?,
    @Json(name = "watchers_count")
    val watchersCount: Int?,
    @Json(name = "forks_count")
    val forksCount: Int?,
    @Json(name = "open_issues_count")
    val issuesCount: Int?,
    @Json(name = "owner")
    val author: Author?,
    @Json(name = "created_at")
    val created: String?,
    @Json(name = "updated_at")
    val updated: String?,
    @Json(name = "language")
    val language: String?,
    @Json(name = "html_url")
    val repoUrl: String?
)

