package com.example.weltchallenge.mappers.data

import com.example.weltchallenge.models.User
import com.example.weltchallenge.data.models.UserSearchDm

class UserDmMapper {

    fun map(input: UserSearchDm): User {
        val placeholder = "${input.login?.first()}"

        return User(
            id = input.id ?: 0,
            login = input.login ?: "",
            imageUrl = input.avatarUrl ?: "",
            imagePlaceholder = placeholder
        )
    }

    fun mapList(inputs: List<UserSearchDm>): List<User> =
        inputs.map {
            map(it)
        }
}