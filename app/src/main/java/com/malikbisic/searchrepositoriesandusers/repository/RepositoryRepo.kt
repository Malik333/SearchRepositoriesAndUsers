package com.malikbisic.searchrepositoriesandusers.repository

import com.malikbisic.searchrepositoriesandusers.model.*
import com.malikbisic.searchrepositoriesandusers.network.GitHubService
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryRepo @Inject constructor(
    private val gitHubService: GitHubService
) {
    fun getGitHubRepositories(params: Map<String, String>): Single<Repositories> {
        return gitHubService.getRepositories(params)
            .subscribeOn(Schedulers.io())
    }

    fun getGitHubAuthors(params: Map<String, String>): Single<Users> {
        return gitHubService.getUsers(params)
            .subscribeOn(Schedulers.io())
    }

    fun getGitHubRepository(owner: String, repoName: String): Single<Repository> {
        return gitHubService.getRepository(owner, repoName)
            .subscribeOn(Schedulers.io())
    }

    fun getGitHubAuthor(owner: String): Single<Author> {
        return gitHubService.getUser(owner)
            .subscribeOn(Schedulers.io())
    }

    fun getGitHubMyProfile(accessToken: String): Single<Author> {
        return gitHubService.getMyProfile(accessToken)
            .subscribeOn(Schedulers.io())
    }

    fun getGitHubAccessToken(url: String, clientId: String, clientSecret: String, code: String): Single<AccessToken> {
        return gitHubService.getAccessToken(url, clientId, clientSecret, code)
            .subscribeOn(Schedulers.io())
    }
}