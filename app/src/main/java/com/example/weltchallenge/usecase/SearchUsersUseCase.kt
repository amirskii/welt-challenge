package com.example.weltchallenge.usecase

import com.example.weltchallenge.data.models.User
import kotlinx.coroutines.flow.Flow

interface SearchUsersUseCase {

    suspend fun execute(searchString: String): Flow<List<User>>
}