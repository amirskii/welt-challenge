package com.example.weltchallenge.usecase

import com.example.weltchallenge.base.BaseViewModelTest
import com.example.weltchallenge.data.gateway.GithubGateway
import com.example.weltchallenge.usecase.FetchUserDetailsUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Test

class FetchUserDetailsUseCaseTest : BaseViewModelTest() {

    @MockK
    private lateinit var githubGateway: GithubGateway

    @Test
    fun `should call gateway`() =
        runTest {
            val interactor = initWithMocks {
                coEvery { githubGateway.getUserDetails(any()) } returns
                        mockk()
            }
            // .toList() works here cause data stream is finite
            interactor.execute("username")
                .toList()

            coVerifySequence {
                githubGateway.getUserDetails("username")
            }
        }

    private fun initWithMocks(mockBlock: () -> Unit): FetchUserDetailsUseCaseImpl {
        mockBlock()
        return FetchUserDetailsUseCaseImpl(
            githubGateway
        )
    }
}