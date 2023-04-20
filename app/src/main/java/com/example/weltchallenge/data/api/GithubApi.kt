package com.example.weltchallenge.data.api

import com.example.weltchallenge.data.models.SearchUserResponse
import com.example.weltchallenge.data.models.UserDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {
    /**
     * search User
     */
    @GET("search/users?")
    suspend fun searchUser(
        @Query("q") q : String
    ) : SearchUserResponse

    /**
     * User Details
     */
    @GET("users/{username}")
    suspend fun getUserDetails(
        @Path("username") username: String
    ) : UserDetailsResponse

}