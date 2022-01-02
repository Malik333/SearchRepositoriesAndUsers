package com.malikbisic.searchrepositoriesandusers.ui.landing.user

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import com.malikbisic.searchrepositoriesandusers.R
import com.malikbisic.searchrepositoriesandusers.glide.GlideApp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.repository_details_fragment.*
import kotlinx.android.synthetic.main.user_screen_fragment.*

@AndroidEntryPoint
class UserScreenFragment : Fragment(R.layout.user_screen_fragment) {

    private val userScreenViewModel: UserScreenViewModel by viewModels<UserScreenViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userScreenViewModel.author.observe(viewLifecycleOwner, {

            if (it != null) {
                GlideApp.with(authorDetailsUserAvatar)
                    .load(it.avatarUrl)
                    .into(authorDetailsUserAvatar)

                authorDetailsUserName.text = it.authorName
                authorDetailsRepoCount.text = it.reposCount.toString()

                val url = it.userUrl
                seeMoreUserTv.setOnClickListener {
                    val openURL = Intent(Intent.ACTION_VIEW)
                    openURL.data = Uri.parse(url)
                    startActivity(openURL)
                }
            }
        })
    }

}