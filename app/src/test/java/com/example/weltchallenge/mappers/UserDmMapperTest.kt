package com.example.weltchallenge.mappers

import com.example.weltchallenge.base.BaseTest
import com.example.weltchallenge.data.models.UserSearchDm
import com.example.weltchallenge.mappers.data.UserDmMapper
import com.example.weltchallenge.models.User
import io.kotest.matchers.shouldBe
import org.junit.Test

class UserDmMapperTest : BaseTest() {

    private lateinit var mapper: UserDmMapper
    override fun setup() {
        super.setup()
        mapper = UserDmMapper()
    }

    @Test
    fun `should map data user model to domain model`() {
        mapper.map(userDm)
            .shouldBe(user)
    }

    private val userDm = UserSearchDm(
        id = 1,
        avatarUrl = "avatarUrl",
        login = "login"
    )

    private val user = User(
        id = 1,
        imageUrl = "avatarUrl",
        imagePlaceholder = "L",
        login = "login"
    )
}
