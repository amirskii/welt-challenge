package com.example.weltchallenge.usecase

import com.example.weltchallenge.data.gateway.GithubGateway
import com.example.weltchallenge.models.UserDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FetchUserDetailsUseCaseImpl(
    private val githubGateway: GithubGateway
) : FetchUserDetailsUseCase {

    override suspend fun execute(username: String): Flow<UserDetails> =
        flow {
            val customer = githubGateway.getUserDetails(username)
            emit(customer)
        }
}