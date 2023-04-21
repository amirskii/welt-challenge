package com.example.weltchallenge.mappers.data

import com.example.weltchallenge.data.models.UserDetailsResponse
import com.example.weltchallenge.models.UserDetails

class UserDetailsDmMapper {

    fun map(input: UserDetailsResponse): UserDetails {
        return UserDetails(
            id = input.id ?: 0,
            name = input.name ?: "",
            username = input.login ?: "",
            avatarUrl = input.avatarUrl ?: "",
            bio = input.bio?.trim() ?: "",
            company = input.company ?: "",
            publicRepos = "${input.publicRepos ?: 0}",
            followers = "${input.followers ?: 0}",
            following = input.following ?: 0,
            location = input.location ?: ""
        )
    }
}