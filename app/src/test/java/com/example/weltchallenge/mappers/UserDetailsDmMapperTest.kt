package com.example.weltchallenge.mappers

import com.example.weltchallenge.base.BaseTest
import com.example.weltchallenge.data.models.UserDetailsResponse
import com.example.weltchallenge.data.models.UserSearchDm
import com.example.weltchallenge.mappers.data.UserDetailsDmMapper
import com.example.weltchallenge.mappers.data.UserDmMapper
import com.example.weltchallenge.models.User
import com.example.weltchallenge.models.UserDetails
import com.google.gson.annotations.SerializedName
import io.kotest.matchers.shouldBe
import org.junit.Test

class UserDetailsDmMapperTest : BaseTest() {

    private lateinit var mapper: UserDetailsDmMapper

    override fun setup() {
        super.setup()
        mapper = UserDetailsDmMapper()
    }

    @Test
    fun `should map data user model to domain model`() {
        mapper.map(userDetailsResponse)
            .shouldBe(userDetails)
    }

    private val userDetailsResponse = UserDetailsResponse(
        avatarUrl = "avatarUrl",
        bio = "bio",
        company = "company",
        email = "email",
        followers = 0,
        following = 0,
        id = 1,
        location = "location",
        login = "login",
        name = "name",
        publicRepos = 0,
        url = "url"
    )

    private val userDetails = UserDetails(
        id = 1,
        username = "login",
        avatarUrl = "avatarUrl",
        bio = "bio",
        name = "name",
        company = "company",
        publicRepos = "0",
        followers = "0",
        following = 0,
        location = "location"
    )
}
