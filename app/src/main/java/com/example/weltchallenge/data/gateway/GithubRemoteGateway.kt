package com.example.weltchallenge.data.gateway

import com.example.weltchallenge.data.api.GithubApi
import com.example.weltchallenge.mappers.data.UserDmMapper
import com.example.weltchallenge.models.User


class GithubRemoteGateway(
    private val api: GithubApi,
    private val mapper: UserDmMapper
) : GithubGateway {
    override suspend fun getUsers(query: String): List<User> {
        return mapper.mapList(api.searchUser(query).userItems)
    }

//    override suspend fun getUserDetails(id: Int): DeviceHolderDetails {
//        return api.getUserDetails(id)
//    }
}