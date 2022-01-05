package com.malikbisic.searchrepositoriesandusers.ui.landing.authentication

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.malikbisic.searchrepositoriesandusers.R
import com.malikbisic.searchrepositoriesandusers.glide.GlideApp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.authenticated_user_fragment.*
import kotlinx.android.synthetic.main.user_screen_fragment.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.log

@AndroidEntryPoint
class AuthenticatedUserFragment : Fragment(R.layout.authenticated_user_fragment) {

    private val authenticatedUserViewModel: AuthenticatedUserViewModel by viewModels<AuthenticatedUserViewModel>()

    private val baseUrl = "https://github.com/login/oauth/access_token"
    private val clientId = "28449931fbf2641f715b"
    private val clientSecretId = "82a1ad93742e543046ad6b2bcd9a96639d05d48a"
    private val redirectUrl = "searchapp://callback"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val loginToken = sharedPref.getString("login_token", "")

        if (!loginToken.isNullOrEmpty()) {
            progress_horizontal.visibility = View.VISIBLE
            authenticatedUserViewModel.getMyProfile(loginToken)
        } else {
            loginContainer.visibility = View.VISIBLE
        }

        signinBtn.setOnClickListener {
            val urlLogin = "https://github.com/login/oauth/authorize?client_id=$clientId&redirect_uri=$redirectUrl&scope=repo%20user"
            Log.d("AuthFragment", "url: $urlLogin")
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlLogin))
                .addCategory(Intent.CATEGORY_BROWSABLE)
            startActivity(intent)
        }

        logout.setOnClickListener {
            with(sharedPref.edit()){
                remove("login_token")
                apply()
            }

            loginContainer.visibility = View.VISIBLE
            userInfoContainer.visibility = View.GONE
        }

        //get access token
        authenticatedUserViewModel.accessToken.observe(viewLifecycleOwner, {
            val accessToken = it.accessToken
            val tokenType = it.accessType
            val token = "$tokenType $accessToken"

            with (sharedPref.edit()) {
                putString("login_token",token)
                apply()
            }

            authenticatedUserViewModel.getMyProfile(token)
        })

        //display user info
        authenticatedUserViewModel.myProfile.observe(viewLifecycleOwner, {
            if (it != null) {
                progress_horizontal.visibility = View.GONE
                userInfoContainer.visibility = View.VISIBLE
                loginContainer.visibility =View.GONE

                GlideApp.with(userAvatar)
                    .load(it.avatarUrl)
                    .into(userAvatar)

                userName.text = it.authorName
                userRepoCount.text = it.reposCount.toString()
                userFollowers.text = it.followersCount.toString()
                userJoined.text = formatDate(it.joinedDate!!)
                userUpdated.text = formatDate(it.updatedDate!!)

                val url = it.userUrl
                seeMoreAuthUserTv.setOnClickListener {
                    val openURL = Intent(Intent.ACTION_VIEW)
                    openURL.data = Uri.parse(url)
                    startActivity(openURL)
                }
            }
        })

        val data = activity?.intent?.data

        if (data != null) {
            val code = data.getQueryParameter("code").toString()
            progress_horizontal.visibility = View.VISIBLE
            authenticatedUserViewModel.getAccessToken(baseUrl, clientId, clientSecretId, code)
        }
    }

    private fun formatDate(date: String): String {
        val parser =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        return formatter.format(parser.parse(date))
    }

}