package com.malikbisic.searchrepositoriesandusers.ui.landing.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.malikbisic.searchrepositoriesandusers.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchScreenFragment : Fragment(R.layout.search_screen_fragment) {

    private val searchScreenViewModel by viewModels<SearchScreenViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchScreenViewModel.gitHubRepositories.observe(viewLifecycleOwner, Observer {
            Log.i("DATA", "read")
        })
    }
}