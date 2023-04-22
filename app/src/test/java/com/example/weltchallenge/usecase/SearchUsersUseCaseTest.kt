package com.example.weltchallenge.usecase

import com.example.weltchallenge.base.BaseViewModelTest
import com.example.weltchallenge.data.gateway.GithubGateway
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SearchUsersUseCaseTest : BaseViewModelTest() {

    @MockK
    private lateinit var githubGateway: GithubGateway

    @Test
    fun `should call gateway`() =
        runTest {
            val interactor = initWithMocks {
                coEvery { githubGateway.searchUser("username") } returns mockk()
            }

            interactor.execute("username")
                .toList()

            coVerify(exactly = 1) {
                githubGateway.searchUser(any())
            }
        }

    private fun initWithMocks(mockBlock: () -> Unit): SearchUsersUseCaseImpl {
        mockBlock()
        return SearchUsersUseCaseImpl(
            githubGateway
        )
    }
}