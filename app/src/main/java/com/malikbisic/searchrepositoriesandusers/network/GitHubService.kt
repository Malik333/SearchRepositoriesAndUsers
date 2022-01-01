package com.malikbisic.searchrepositoriesandusers.network

import com.malikbisic.searchrepositoriesandusers.model.Repositories
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface GitHubService {

    @GET(value = "search/repositories")
    fun getRepositories(@QueryMap params: Map<String, String>): Single<Repositories>
}