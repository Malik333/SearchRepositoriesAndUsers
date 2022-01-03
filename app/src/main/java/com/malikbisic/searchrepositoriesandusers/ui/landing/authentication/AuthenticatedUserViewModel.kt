package com.malikbisic.searchrepositoriesandusers.ui.landing.authentication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.malikbisic.searchrepositoriesandusers.model.AccessToken
import com.malikbisic.searchrepositoriesandusers.model.Author
import com.malikbisic.searchrepositoriesandusers.repository.RepositoryRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@HiltViewModel
class AuthenticatedUserViewModel @Inject constructor(
    private val repositoryRepo: RepositoryRepo
) : ViewModel() {
    private val _accessToken = MutableLiveData<AccessToken>()
    val accessToken: LiveData<AccessToken>
        get() = _accessToken

    private val _myProfile = MutableLiveData<Author>()
    val myProfile: LiveData<Author>
        get() = _myProfile


    fun getMyProfile(accessToken: String) {
        repositoryRepo.getGitHubMyProfile(accessToken)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {user -> _myProfile.value = user},
                {error -> Log.e("AuthViewModel", "error user: ${error.localizedMessage}")})
    }

    fun getAccessToken(url: String, clientId: String, clientSecret: String, code: String) {
        repositoryRepo.getGitHubAccessToken(url, clientId, clientSecret, code)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe (
                {accessToken -> this._accessToken.value = accessToken  },
                {error -> Log.e("AuthViewModel", "error: ${error.localizedMessage}")})
    }
}