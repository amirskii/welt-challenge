package com.example.weltchallenge.usecase

import com.example.weltchallenge.models.UserDetails
import kotlinx.coroutines.flow.Flow

interface FetchUserDetailsUseCase {

    suspend fun execute(username: String): Flow<UserDetails>
}