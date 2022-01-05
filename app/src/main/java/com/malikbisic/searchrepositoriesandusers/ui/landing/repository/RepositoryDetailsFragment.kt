package com.malikbisic.searchrepositoriesandusers.ui.landing.repository

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.malikbisic.searchrepositoriesandusers.R
import com.malikbisic.searchrepositoriesandusers.glide.GlideApp
import com.malikbisic.searchrepositoriesandusers.ui.landing.search.SearchScreenFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.repository_details_fragment.*
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class RepositoryDetailsFragment : Fragment(R.layout.repository_details_fragment) {

    private val repositoryDetailsViewModel: RepositoryDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        repositoryDetailsViewModel.repository.observe(viewLifecycleOwner, {

            if (it != null) {
                GlideApp.with(repoDetailsUserAvatar)
                    .load(it.author?.avatarUrl)
                    .into(repoDetailsUserAvatar)

                repoDetailsUserAvatar.setOnClickListener { view ->
                    val directionsToUserScreen =
                        RepositoryDetailsFragmentDirections.actionRepositoryDetailsFragmentToUserScreenFragment(it.author?.authorName!!)
                    view.findNavController().navigate(directionsToUserScreen)
                }

                repoDetailsUserName.text = it.author?.authorName
                repoDetailsRepoName.text = it.repositoryName
                repoDetailsRepoLanguange.text = it.language

                repoDetailsRepoCreated.text = formatDate(it.created!!)
                repoDetailsRepoUpdated.text = formatDate(it.updated!!)
                val url: String = it.repoUrl!!

                seeMoreTv.setOnClickListener {
                    val openURL = Intent(Intent.ACTION_VIEW)
                    openURL.data = Uri.parse(url)
                    startActivity(openURL)
                }
            }
        })

    }

    private fun formatDate(date: String): String {
        val parser =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        return formatter.format(parser.parse(date))
    }

}