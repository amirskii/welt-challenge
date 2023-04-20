package com.example.weltchallenge.data.di

import com.example.weltchallenge.data.api.GithubApi
import com.example.weltchallenge.data.gateway.GithubGateway
import com.example.weltchallenge.data.gateway.GithubRemoteGateway
import com.example.weltchallenge.mappers.data.UserDmMapper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkInjectionModule {

    val module = module {

        factory<GithubGateway> { GithubRemoteGateway(
            api = get(),
            mapper = get()
        ) }

        factory<GsonConverterFactory> {
            GsonConverterFactory.create()
        }

        factory<HttpLoggingInterceptor> {
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        factory<OkHttpClient> {
            OkHttpClient.Builder()
                .addInterceptor(get<HttpLoggingInterceptor>())
                .build()
        }

        single<Retrofit> {
            Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(get<OkHttpClient>())
                .addConverterFactory(get<GsonConverterFactory>())
                .build()
        }

        single<GithubApi> { provideGithubApiService(get()) }
        single { UserDmMapper() }
    }

    private fun provideGithubApiService(retrofit: Retrofit) =
        retrofit.create(GithubApi::class.java)
}