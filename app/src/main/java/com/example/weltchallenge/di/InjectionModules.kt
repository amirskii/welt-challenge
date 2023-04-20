package com.example.weltchallenge.di

import com.example.weltchallenge.data.di.NetworkInjectionModule
import com.example.weltchallenge.features.users.di.UsersInjectionModule


object InjectionModules {

    val modules = listOf(
        UsersInjectionModule.module,
        NetworkInjectionModule.module,
//        DetailsInjectionModule.module
    )
}