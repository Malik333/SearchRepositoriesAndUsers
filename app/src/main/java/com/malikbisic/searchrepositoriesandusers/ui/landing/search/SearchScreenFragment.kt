package com.malikbisic.searchrepositoriesandusers.ui.landing.search

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.malikbisic.searchrepositoriesandusers.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.search_screen_fragment.*

@AndroidEntryPoint
class SearchScreenFragment : Fragment(R.layout.search_screen_fragment),
    SearchView.OnQueryTextListener, OnClickListener {

    private lateinit var mAdapterRepositories: RepositoryAdapter
    private lateinit var mAdapterUsers: UserAdapter
    private val searchScreenViewModel by viewModels<SearchScreenViewModel>()

    var isUserSelected: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdapterRepositories = RepositoryAdapter()
        mAdapterUsers = UserAdapter()

        recyclerview_repos.layoutManager = LinearLayoutManager(requireContext())
        recyclerview_repos.adapter = mAdapterRepositories

        searchScreenViewModel.gitHubRepositories.observe(viewLifecycleOwner, Observer {
            mAdapterRepositories.setRepositoryItems(it)
        })

        searchScreenViewModel.gitHubAuthors.observe(viewLifecycleOwner, Observer {
            mAdapterUsers.setUserItems(it)
        })

        search_view.queryHint = "Search repositories"
        search_view.isSubmitButtonEnabled = false
        search_view.setOnQueryTextListener(this)

        repositories_btn.setBackgroundColor(Color.BLACK)
        users_btn.setBackgroundColor(Color.WHITE)

        repositories_btn.setOnClickListener(this)
        users_btn.setOnClickListener(this)
    }

    override fun onQueryTextSubmit(text: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(text: String?): Boolean {
        if (text != null) {
            if (isUserSelected)
                searchScreenViewModel.searchAuthors(text)
            else
                searchScreenViewModel.searchRepositories(text)
            return true
        }

        if (isUserSelected)
            searchScreenViewModel.searchAuthors("")
        else
            searchScreenViewModel.searchRepositories("")

        return true
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.repositories_btn -> {
                isUserSelected = false
                recyclerview_repos.adapter = mAdapterRepositories
                search_view.queryHint = "Search repositories"
                repositories_btn.setBackgroundColor(Color.BLACK)
                users_btn.setBackgroundColor(Color.WHITE)

                repositories_btn.setTextColor(Color.WHITE)
                users_btn.setTextColor(Color.BLACK)

                search_view.setQuery("", true)
            }
            R.id.users_btn -> {
                isUserSelected = true
                recyclerview_repos.adapter = mAdapterUsers
                search_view.queryHint = "Search users"
                repositories_btn.setBackgroundColor(Color.WHITE)
                users_btn.setBackgroundColor(Color.BLACK)

                repositories_btn.setTextColor(Color.BLACK)
                users_btn.setTextColor(Color.WHITE)

                search_view.setQuery("", true)
            }
        }
    }
}