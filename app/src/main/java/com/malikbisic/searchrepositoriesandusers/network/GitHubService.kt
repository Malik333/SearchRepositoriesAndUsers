package com.malikbisic.searchrepositoriesandusers.network

import com.malikbisic.searchrepositoriesandusers.model.Repositories
import com.malikbisic.searchrepositoriesandusers.model.Users
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface GitHubService {

    @GET(value = "search/repositories")
    fun getRepositories(@QueryMap params: Map<String, String>): Single<Repositories>

    @GET(value = "search/users")
    fun getUsers(@QueryMap params: Map<String, String>): Single<Users>
}