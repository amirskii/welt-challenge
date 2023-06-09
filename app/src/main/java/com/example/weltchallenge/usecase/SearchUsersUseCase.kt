package com.example.weltchallenge.usecase

import com.example.weltchallenge.models.User
import kotlinx.coroutines.flow.Flow

interface SearchUsersUseCase {

    suspend fun execute(query: String): Flow<List<User>>
}