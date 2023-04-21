package com.example.weltchallenge.data.gateway

import com.example.weltchallenge.data.api.GithubApi
import com.example.weltchallenge.mappers.data.UserDetailsDmMapper
import com.example.weltchallenge.mappers.data.UserDmMapper
import com.example.weltchallenge.models.User
import com.example.weltchallenge.models.UserDetails


class GithubRemoteGateway(
    private val api: GithubApi,
    private val userMapper: UserDmMapper,
    private val userDetailsMapper: UserDetailsDmMapper
) : GithubGateway {
    override suspend fun getUsers(query: String): List<User> {
        return userMapper.mapList(api.searchUser(query).userItems)
    }

    override suspend fun getUserDetails(username: String): UserDetails {
        return userDetailsMapper.map(api.getUserDetails(username))
    }
}