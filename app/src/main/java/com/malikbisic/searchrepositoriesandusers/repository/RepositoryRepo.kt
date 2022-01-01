package com.malikbisic.searchrepositoriesandusers.repository

import com.malikbisic.searchrepositoriesandusers.model.Author
import com.malikbisic.searchrepositoriesandusers.model.Repositories
import com.malikbisic.searchrepositoriesandusers.model.Users
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
}