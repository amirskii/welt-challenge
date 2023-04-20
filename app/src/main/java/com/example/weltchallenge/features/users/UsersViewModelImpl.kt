package com.example.weltchallenge.features.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weltchallenge.usecase.SearchUsersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsersViewModelImpl(
    private val searchUsersUseCase: SearchUsersUseCase
) : DeviceHoldersViewModel, ViewModel() {

    override val uiState = MutableStateFlow(UsersUiState())
    private var lastQuery = ""

    override fun searchUsers(query: String) {
        lastQuery = query
        uiState.update { state ->
            state.copy(
                loading = true
            )
        }
        viewModelScope.launch {
            searchUsersUseCase.execute(query)
                .catch {
                    uiState.update { state -> state.copy(error = it.localizedMessage) }
                }
                .onEach { users ->
                    uiState.update { state ->
                        state.copy(
                            users = users,
                            loading = false,
                            error = null
                        )
                    }
                }
                .collect()
        }
    }

    fun repeatLastSearch() {
        if (lastQuery.isNotEmpty()) {
            searchUsers(lastQuery)
        }
    }

}