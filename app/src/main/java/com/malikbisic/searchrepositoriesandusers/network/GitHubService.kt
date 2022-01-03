package com.malikbisic.searchrepositoriesandusers.network

import com.malikbisic.searchrepositoriesandusers.model.*
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface GitHubService {

    @GET(value = "search/repositories?page=1")
    fun getRepositories(@QueryMap params: Map<String, String>): Single<Repositories>

    @GET(value = "search/users?page=1")
    fun getUsers(@QueryMap params: Map<String, String>): Single<Users>

    @GET(value = "repos/{owner}/{repo}")
    fun getRepository(@Path("owner") owner: String, @Path("repo") repoName: String): Single<Repository>

    @GET(value = "users/{username}")
    fun getUser(@Path("username") owner: String): Single<Author>

    @GET(value = "user")
    fun getMyProfile(@Header("Authorization") accessToken: String): Single<Author>

    @Headers("Accept: application/json")
    @POST
    @FormUrlEncoded
    fun getAccessToken(@Url url: String,
                       @Field("client_id") clientId: String,
                       @Field("client_secret") clientSecret: String,
                       @Field("code") code: String,): Single<AccessToken>
}