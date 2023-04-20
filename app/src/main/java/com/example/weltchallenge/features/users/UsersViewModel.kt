package com.example.weltchallenge.features.users

import com.example.weltchallenge.data.models.User
import kotlinx.coroutines.flow.Flow

interface DeviceHoldersViewModel {
    val uiState: Flow<UsersUiState>
    fun searchUsers(searchString: String)
}

data class UsersUiState(
    val users: List<User>? = null,
    val loading: Boolean = false,
    val error: String? = null
)

