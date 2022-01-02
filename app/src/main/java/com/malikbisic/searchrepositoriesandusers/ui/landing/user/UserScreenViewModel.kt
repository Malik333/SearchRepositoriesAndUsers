package com.malikbisic.searchrepositoriesandusers.ui.landing.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.malikbisic.searchrepositoriesandusers.model.Author
import com.malikbisic.searchrepositoriesandusers.repository.RepositoryRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@HiltViewModel
class UserScreenViewModel @Inject constructor(
    private val repositoryRepo: RepositoryRepo,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _author = MutableLiveData<Author>()
    val author: LiveData<Author>
        get() = _author

    init {
        if (savedStateHandle.contains("username")) {
            val username = savedStateHandle.get<String>("username")

            repositoryRepo.getGitHubAuthor(username!!)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( { author -> _author.value = author },
                    {error -> Log.e("UserScreenViewModel", "error ${error.localizedMessage}")})
        }
    }
}