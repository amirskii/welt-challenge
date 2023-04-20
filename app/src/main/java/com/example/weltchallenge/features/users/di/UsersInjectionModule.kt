package com.example.weltchallenge.features.users.di

import com.example.weltchallenge.features.users.UsersViewModelImpl
import com.example.weltchallenge.usecase.SearchUsersUseCase
import com.example.weltchallenge.usecase.SearchUsersUseCaseImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object UsersInjectionModule {

    val module = module {

        viewModel {
            UsersViewModelImpl(
                searchUsersUseCase = get()
            )
        }

        factory<SearchUsersUseCase> {
            SearchUsersUseCaseImpl(get())
        }
    }
}