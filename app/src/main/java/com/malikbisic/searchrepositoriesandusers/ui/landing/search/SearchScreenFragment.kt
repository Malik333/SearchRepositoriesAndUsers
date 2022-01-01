package com.malikbisic.searchrepositoriesandusers.ui.landing.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.malikbisic.searchrepositoriesandusers.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.search_screen_fragment.*

@AndroidEntryPoint
class SearchScreenFragment : Fragment(R.layout.search_screen_fragment), OnQueryTextListener {

    private lateinit var mAdapter: RepositoryAdapter
    private val searchScreenViewModel by viewModels<SearchScreenViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdapter = RepositoryAdapter()
        recyclerview_repos.layoutManager = LinearLayoutManager(requireContext())
        recyclerview_repos.adapter = mAdapter

        searchScreenViewModel.gitHubRepositories.observe(viewLifecycleOwner, Observer {
            mAdapter.setRepositoryItems(it)
        })
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        TODO("Not yet implemented")
    }
}