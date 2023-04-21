package com.example.weltchallenge.features.users

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.weltchallenge.databinding.FragmentUsersBinding
import com.example.weltchallenge.features.base.BaseFragment
import com.example.weltchallenge.features.users.adapter.UserAdapter
import com.example.weltchallenge.models.UserDetails
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersFragment : BaseFragment<FragmentUsersBinding>(
    FragmentUsersBinding::inflate
) {
    private val viewModel by viewModel<UsersViewModelImpl>()

    private val adapter by lazy {
        UserAdapter {
            viewModel.getUserDetails(it.login)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        observe()
        setupSearchUi()
    }

    private fun setupUi() {
        with(binding) {
            deviceHoldersRecyclerView.adapter = adapter
            swipeRefreshLayout.setOnRefreshListener {
                viewModel.repeatLastSearch()
            }
        }
    }

    private fun setupSearchUi() {
        binding.apply {
            searchUsersView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        if (it.isNotEmpty()) {
                            viewModel.searchUsers(it)
                        }
                    }
                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean =
                    false
            })
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.swipeRefreshLayout.isRefreshing = state.loading

                    state.users?.let {
                        adapter.submitList(it)
                    }

                    showUserDetails(state.userDetails)

                    if (!state.error.isNullOrBlank()) {
                        showError(state.error)
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.events.collect {
                    if (it is UsersEvents.ErrorEvent) {
                        showError(it.message)
                    }
                }
            }
        }
    }

    private fun showUserDetails(userDetails: UserDetails?) {
        with(binding) {
            bottomSheet.isVisible = userDetails != null
            userDetails?.let {
                nameTextView.text = it.name
                bioTextView.text = it.bio
                followersValueTextView.text = it.followers
                reposValueTextView.text = it.publicRepos
            }
        }
    }
}