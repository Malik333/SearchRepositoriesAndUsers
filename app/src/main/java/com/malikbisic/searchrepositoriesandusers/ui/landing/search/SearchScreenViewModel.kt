package com.malikbisic.searchrepositoriesandusers.ui.landing.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.malikbisic.searchrepositoriesandusers.model.Repository
import com.malikbisic.searchrepositoriesandusers.repository.RepositoryRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject


@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val repositoryRepo: RepositoryRepo
) :  ViewModel() {

    private val _gitHubRepositories = MutableLiveData<Repository>()
    val gitHubRepositories: LiveData<Repository>
            get()= _gitHubRepositories
   init {
      val params: HashMap<String, String> = HashMap<String, String>()
      params["q"] = "te"

       repositoryRepo.getGitHubRepositories(params)
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe()

   }
}