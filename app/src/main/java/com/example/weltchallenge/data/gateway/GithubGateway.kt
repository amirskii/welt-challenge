package com.example.weltchallenge.data.gateway

import com.example.weltchallenge.data.models.User

interface GithubGateway {
    suspend fun getUsers(searchString: String): List<User>

//    suspend fun getUserDetails(id: Int): DeviceHolderDetails
}