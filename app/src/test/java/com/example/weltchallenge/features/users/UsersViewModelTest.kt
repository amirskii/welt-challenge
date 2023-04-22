package com.example.weltchallenge.features.users

import com.example.weltchallenge.base.BaseViewModelTest
import com.example.weltchallenge.models.User
import com.example.weltchallenge.models.UserDetails
import com.example.weltchallenge.usecase.FetchUserDetailsUseCase
import com.example.weltchallenge.usecase.SearchUsersUseCase
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class UsersViewModelTest : BaseViewModelTest() {

    private val searchUsersUseCase = FakeSearchUsersUseCase()

    @MockK
    lateinit var searchUsersUseCaseMock: SearchUsersUseCase

    @MockK
    lateinit var fetchUserDetailsUseCaseMock: FetchUserDetailsUseCase

    private val fetchUserDetailsUseCase = FakeFetchUserDetailsUseCase()

    private lateinit var viewModel: UsersViewModelImpl

    private fun buildViewModel(): UsersViewModelImpl {
        return UsersViewModelImpl(
            searchUsersUseCase,
            fetchUserDetailsUseCase
        )
    }

    override fun setup() {
        super.setup()
        viewModel = buildViewModel()
    }

    @Test
    fun `should call use case`() =
        runTest {
            // given
            setupWithMocks()

            // when
            viewModel.searchUsers("username")

            // expect
            coVerify(exactly = 1) {
                searchUsersUseCaseMock.execute(any())
            }
        }

    @Test
    fun `search users should change state from loading`() =
        runTest {
            // given
            viewModel.searchUsers("username")
            val results = mutableListOf<UsersUiState>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.uiState.toList(results)
            }
            searchUsersUseCase.emit(listOf(user))

            // expect
            results.first()
                .shouldBe(UsersUiState().copy(loading = true))
        }

    @Test
    fun `search users should change state to success`() =
        runTest {
            // given
            viewModel.searchUsers("username")

            val results = mutableListOf<UsersUiState>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.uiState.toList(results)
            }
            searchUsersUseCase.emit(listOf(user))

            // expect
            results.last()
                .shouldBe(UsersUiState().copy(users = listOf(user)))
        }

    @Test
    fun `search users should change state to error when IOException`() =
        runTest {
            // given
            setupWithMocks(isError = true)

            // when
            viewModel.searchUsers("username")

            // expect
            viewModel.uiState.value
                .shouldBe(UsersUiState().copy(error = "Unknown error"))
        }

    @Test
    fun `repeat last search should call use case with same query string`() =
        runTest {
            // given
            setupWithMocks()

            // when
            viewModel.searchUsers("abc")
            viewModel.repeatLastSearch()

            // expect
            coVerify(exactly = 2) {
                searchUsersUseCaseMock.execute("abc")
            }
        }

    @Test
    fun `get user details should change ui state to user details success`() =
        runTest {
            viewModel.getUserDetails("username")

            val results = mutableListOf<UsersUiState>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.uiState.toList(results)
            }
            fetchUserDetailsUseCase.emit(userDetails)

            results.last()
                .shouldBe(UsersUiState().copy(userDetails = userDetails))
        }

    @Test
    fun `get user details should change state to error when IOException`() =
        runTest {
            setupWithMocks(isError = true)

            // given
            viewModel.getUserDetails("username")

            // expect
            viewModel.events.first()
                .shouldBe(UsersEvents.ErrorEvent("Unknown error"))
        }

    internal class FakeSearchUsersUseCase : SearchUsersUseCase {
        private val flow = MutableSharedFlow<List<User>>()
        suspend fun emit(value: List<User>) {
            flow.emit(value)
        }

        override suspend fun execute(query: String): Flow<List<User>> {
            return flow
        }

    }

    internal class FakeFetchUserDetailsUseCase : FetchUserDetailsUseCase {
        private val flow = MutableSharedFlow<UserDetails>()
        suspend fun emit(value: UserDetails) =
            flow.emit(value)

        override suspend fun execute(query: String): Flow<UserDetails> =
            flow
    }

    private fun setupWithMocks(isError: Boolean = false) {
        viewModel = UsersViewModelImpl(
            searchUsersUseCaseMock,
            fetchUserDetailsUseCaseMock
        )
        if (isError) {
            coEvery { searchUsersUseCaseMock.execute(any()) } returns flow {
                throw IOException()
            }

            coEvery { fetchUserDetailsUseCaseMock.execute(any()) } returns flow {
                throw IOException()
            }
        } else {
            coEvery { searchUsersUseCaseMock.execute(any()) } returns flowOf(listOf(user))
            coEvery { fetchUserDetailsUseCaseMock.execute(any()) } returns flowOf(userDetails)
        }
    }

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

    private val user = User(
        id = 1,
        imageUrl = "avatarUrl",
        imagePlaceholder = "L",
        login = "login"
    )
}