package com.example.weltchallenge.data.gateway

import com.example.weltchallenge.base.BaseViewModelTest
import com.example.weltchallenge.data.api.GithubApi
import com.example.weltchallenge.data.models.SearchUserResponse
import com.example.weltchallenge.data.models.UserSearchDm
import com.example.weltchallenge.mappers.data.UserDetailsDmMapper
import com.example.weltchallenge.mappers.data.UserDmMapper
import com.example.weltchallenge.models.User
import com.example.weltchallenge.models.UserDetails
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GithubRemoteGatewayTest : BaseViewModelTest() {

    @MockK
    private lateinit var api: GithubApi

    @MockK
    private lateinit var userDmMapper: UserDmMapper

    @MockK
    private lateinit var userDetailsDmMapper: UserDetailsDmMapper

    @Test
    fun `getUserDetails should call api and mapper`() =
        runTest {
            val gateway = initWithMocks {
                coEvery { api.getUserDetails(any()) } returns
                        mockk()
                every { userDetailsDmMapper.map(any()) } returns userDetails
            }

            gateway.getUserDetails("username")

            coVerifySequence {
                api.getUserDetails("username")
                userDetailsDmMapper.map(any())
            }
        }

    @Test
    fun `searchUser should call api and mapper`() =
        runTest {
            val gateway = initWithMocks {
                coEvery { api.searchUser("username") } returns
                        SearchUserResponse(listOf(), 0)
                every { userDmMapper.mapList(any()) } returns listOf(user)
            }

            gateway.searchUser("username")

            coVerifySequence {
                api.searchUser("username")
                userDmMapper.mapList(any())
            }
        }

    @Test
    fun `searchUser should return list of users`() =
        runTest {
            val gateway = initWithMocks {
                coEvery { api.searchUser("username") } returns
                        SearchUserResponse(listOf(userDm), 1)
                every { userDmMapper.mapList(any()) } returns listOf(user)
            }

            val customers = gateway.searchUser("username")

            customers.shouldBe(listOf(user))
        }

    private fun initWithMocks(mockBlock: () -> Unit): GithubRemoteGateway {
        mockBlock()
        return GithubRemoteGateway(
            api,
            userMapper = userDmMapper,
            userDetailsMapper = userDetailsDmMapper
        )
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

    private val userDetails = UserDetails(
        id = 1,
        username = "username",
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