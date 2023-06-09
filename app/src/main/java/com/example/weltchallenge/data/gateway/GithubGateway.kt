package com.example.weltchallenge.data.gateway

import com.example.weltchallenge.models.User
import com.example.weltchallenge.models.UserDetails

interface GithubGateway {
    suspend fun searchUser(query: String): List<User>

    suspend fun getUserDetails(username: String): UserDetails
}