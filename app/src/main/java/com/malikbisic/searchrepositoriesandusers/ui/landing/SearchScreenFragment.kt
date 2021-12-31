package com.malikbisic.searchrepositoriesandusers.ui.landing

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.malikbisic.searchrepositoriesandusers.R

class SearchScreenFragment : Fragment(R.layout.search_screen_fragment) {

    private val searchScreenViewModel by viewModels<SearchScreenViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchScreenViewModel.gitHubRepositories.observe(viewLifecycleOwner, Observer {
            Log.i("DATA", "read")
        })
    }
}