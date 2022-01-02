package com.malikbisic.searchrepositoriesandusers.ui.landing.search

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.malikbisic.searchrepositoriesandusers.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.search_screen_fragment.*
import java.util.*
import kotlin.collections.ArrayList


@AndroidEntryPoint
class SearchScreenFragment : Fragment(R.layout.search_screen_fragment),
    SearchView.OnQueryTextListener, OnClickListener {

    private lateinit var mAdapterRepositories: RepositoryAdapter
    private lateinit var mAdapterUsers: UserAdapter
    private val searchScreenViewModel by viewModels<SearchScreenViewModel>()

    private lateinit var builder: AlertDialog.Builder

    var isUserSelected: Boolean = false
    var text: String = ""
    var selectedStrings: ArrayList<String> = arrayListOf()

    private val handler: Handler = Handler(Looper.getMainLooper())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

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

        createAlertDialog()
    }

    override fun onQueryTextSubmit(text: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(text: String?): Boolean {
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed({
            if (text != null) {
                this.text = text
                if (isUserSelected)
                    searchScreenViewModel.searchAuthors(text, selectedStrings)
                else
                    searchScreenViewModel.searchRepositories(text, selectedStrings)
            } else {
                if (isUserSelected)
                    searchScreenViewModel.searchAuthors("", selectedStrings)
                else
                    searchScreenViewModel.searchRepositories("", selectedStrings)
            }
        }, 400)
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

    fun createAlertDialog() {
        val items = arrayOf("Stars", "Forks", "Updated")
        var checkedItems = booleanArrayOf(false, false, false)
        val selectedList = ArrayList<Int>()
        builder = AlertDialog.Builder(requireContext())

        builder.setTitle("This is list choice dialog box")
        builder.setMultiChoiceItems(items, checkedItems) { dialog, which, isChecked ->
            if (isChecked) {
                selectedList.add(which)
                checkedItems[Integer.valueOf(which)] = true
            } else if (selectedList.contains(which)) {
                selectedList.remove(Integer.valueOf(which))
                checkedItems[Integer.valueOf(which)] = true
            }
        }

        builder.setPositiveButton("DONE") { dialogInterface, i ->
            selectedStrings = ArrayList<String>()
            checkedItems = booleanArrayOf(false, false, false)

            for (j in selectedList.indices) {
                var item = items[selectedList[j]]
                selectedStrings.add(item.lowercase())
                checkedItems[j] = true
            }

            if (isUserSelected)
                searchScreenViewModel.searchAuthors(text, selectedStrings)
            else
                searchScreenViewModel.searchRepositories(text, selectedStrings)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.filter_menu, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.filter -> {
                builder.show()
            }
        }

        return true
    }
}