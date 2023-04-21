package com.example.weltchallenge.features.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weltchallenge.usecase.FetchUserDetailsUseCase
import com.example.weltchallenge.usecase.SearchUsersUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsersViewModelImpl(
    private val searchUsersUseCase: SearchUsersUseCase,
    private val fetchUserDetailsUseCase: FetchUserDetailsUseCase
) : UsersViewModel, ViewModel() {
    private var lastQuery = ""

    override val uiState = MutableStateFlow(UsersUiState())

    private val eventsChannel = Channel<UsersEvents>()
    override val events: Flow<UsersEvents> = eventsChannel.receiveAsFlow()

    override fun searchUsers(query: String) {
        lastQuery = query
        uiState.update { state ->
            state.copy(
                loading = true,
                userDetails = null
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

    override fun getUserDetails(username: String) {
        viewModelScope.launch {
            fetchUserDetailsUseCase.execute(username)
                .catch {
                    eventsChannel.send(UsersEvents.ErrorEvent(it.localizedMessage))
                }
                .onEach { userDetails ->
                    uiState.update { state ->
                        state.copy(
                            userDetails = userDetails,
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