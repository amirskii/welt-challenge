package com.example.weltchallenge.features.users

import com.example.weltchallenge.models.User
import com.example.weltchallenge.models.UserDetails
import kotlinx.coroutines.flow.Flow

interface UsersViewModel {
    val uiState: Flow<UsersUiState>
    fun searchUsers(query: String)
    fun getUserDetails(username: String)
}

data class UsersUiState(
    val users: List<User>? = null,
    val userDetails: UserDetails? = null,
    val loading: Boolean = false,
    val error: String? = null
)

