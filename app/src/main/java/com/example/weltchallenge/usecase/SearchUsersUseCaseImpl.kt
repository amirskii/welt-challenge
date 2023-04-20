package com.example.weltchallenge.usecase

import com.example.weltchallenge.data.gateway.GithubGateway
import com.example.weltchallenge.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchUsersUseCaseImpl(
    private val githubGateway: GithubGateway
) : SearchUsersUseCase {

    override suspend fun execute(searchString: String): Flow<List<User>> =
        flow {
            val customers = githubGateway.getUsers(searchString)
            emit(customers)
        }
}