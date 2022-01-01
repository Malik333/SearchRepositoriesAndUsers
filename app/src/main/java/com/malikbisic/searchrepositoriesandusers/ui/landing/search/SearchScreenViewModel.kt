package com.malikbisic.searchrepositoriesandusers.ui.landing.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.malikbisic.searchrepositoriesandusers.model.Author
import com.malikbisic.searchrepositoriesandusers.model.Repository
import com.malikbisic.searchrepositoriesandusers.repository.RepositoryRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject


@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val repositoryRepo: RepositoryRepo
) : ViewModel() {

    private val _gitHubRepositories = MutableLiveData<List<Repository>>()
    val gitHubRepositories: LiveData<List<Repository>>
        get() = _gitHubRepositories

    private val _gitHubAuthors = MutableLiveData<List<Author>>()
    val gitHubAuthors: LiveData<List<Author>>
        get() = _gitHubAuthors


    fun searchRepositories(text: String, filters: ArrayList<String>) {

        if (text.isEmpty()) {
            _gitHubRepositories.value = listOf()
        }

        val params: HashMap<String, String> = HashMap<String, String>()
        params["q"] = text

        if (!filters.isNullOrEmpty()) {
            val sortItems = filters.joinToString(".")
            params["sort"] = sortItems
        }


        repositoryRepo.getGitHubRepositories(params)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { repositories -> _gitHubRepositories.value = repositories.items },
                { error -> Log.e("SearchScreenObser", error.localizedMessage) })
    }

    fun searchAuthors(text: String, filters: ArrayList<String>) {

        if (text.isEmpty()) {
            _gitHubAuthors.value = listOf()
        }

        val params: HashMap<String, String> = HashMap<String, String>()
        params["q"] = text

        if (!filters.isNullOrEmpty()) {
            val sortItems = filters.joinToString(".").lowercase()
            params["sort"] = sortItems
        }

        repositoryRepo.getGitHubAuthors(params)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { users -> _gitHubAuthors.value = users.items },
                { error -> Log.e("SearchScreenObser", error.localizedMessage) })
    }
}