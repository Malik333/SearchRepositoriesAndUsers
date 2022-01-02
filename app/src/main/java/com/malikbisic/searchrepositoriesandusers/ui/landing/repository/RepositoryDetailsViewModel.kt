package com.malikbisic.searchrepositoriesandusers.ui.landing.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.malikbisic.searchrepositoriesandusers.model.Repository
import com.malikbisic.searchrepositoriesandusers.repository.RepositoryRepo
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@HiltViewModel
class RepositoryDetailsViewModel @Inject constructor(
    private val repositoryRepo: RepositoryRepo,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _repository = MutableLiveData<Repository>()
    val repository: LiveData<Repository>
        get() = _repository

    init {

        if (savedStateHandle.contains("owner") && savedStateHandle.contains("repoName")) {

            val owner = savedStateHandle.get<String>("owner")
            val repoName = savedStateHandle.get<String>("repoName")

            repositoryRepo.getGitHubRepository(owner!!, repoName!!)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { repository -> _repository.value = repository },
                    { error -> Log.e("RepositoryDetailsViewModel", error.localizedMessage) })
        }

    }

}