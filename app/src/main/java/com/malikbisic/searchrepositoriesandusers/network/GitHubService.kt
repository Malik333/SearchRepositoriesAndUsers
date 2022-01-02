package com.malikbisic.searchrepositoriesandusers.network

import com.malikbisic.searchrepositoriesandusers.model.Repositories
import com.malikbisic.searchrepositoriesandusers.model.Repository
import com.malikbisic.searchrepositoriesandusers.model.Users
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface GitHubService {

    @GET(value = "search/repositories?page=1")
    fun getRepositories(@QueryMap params: Map<String, String>): Single<Repositories>

    @GET(value = "search/users?page=1")
    fun getUsers(@QueryMap params: Map<String, String>): Single<Users>

    @GET(value = "repos/{owner}/{repo}")
    fun getRepository(@Path("owner") owner: String, @Path("repo") repoName: String): Single<Repository>
}