package com.example.weltchallenge.data.gateway

import com.example.weltchallenge.models.User

interface GithubGateway {
    suspend fun getUsers(query: String): List<User>

//    suspend fun getUserDetails(id: Int): DeviceHolderDetails
}