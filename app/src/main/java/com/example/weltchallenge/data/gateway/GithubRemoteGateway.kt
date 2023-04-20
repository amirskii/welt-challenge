package com.example.weltchallenge.data.gateway

import com.example.weltchallenge.data.api.GithubApi
import com.example.weltchallenge.data.models.User
import com.example.weltchallenge.data.models.UserSearchDm
import com.example.weltchallenge.mappers.data.UserDmMapper


class GithubRemoteGateway(
    private val api: GithubApi,
    private val mapper: UserDmMapper
) : GithubGateway {
    override suspend fun getUsers(searchString: String): List<User> {
        return mapper.mapList(api.searchUser(searchString).userItems)
    }

//    override suspend fun getUserDetails(id: Int): DeviceHolderDetails {
//        return api.getUserDetails(id)
//    }
}