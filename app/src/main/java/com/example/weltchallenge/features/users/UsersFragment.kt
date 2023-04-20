package com.example.weltchallenge.features.users

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.weltchallenge.databinding.FragmentUsersBinding
import com.example.weltchallenge.features.base.BaseFragment
import com.example.weltchallenge.features.users.adapter.UserAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersFragment : BaseFragment<FragmentUsersBinding>(
    FragmentUsersBinding::inflate
) {
    private val viewModel by viewModel<UsersViewModelImpl>()

    private val adapter by lazy {
        UserAdapter {
//            findNavController().navigate(DeviceHoldersFragmentDirections.toDetailsFragment(it.id))
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        observe()
    }

    private fun setupUi() {
        with(binding) {
            deviceHoldersRecyclerView.adapter = adapter
//            deviceHoldersRecyclerView.addItemDecoration(
//                UiUtils.getDividerIconDecoration(requireContext())
//            )
            swipeRefreshLayout.setOnRefreshListener {
                viewModel.searchUsers("amir")
            }
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
                    if (!state.error.isNullOrBlank()) {
                        showError(state.error)
                    }
                }
            }
        }
    }
}