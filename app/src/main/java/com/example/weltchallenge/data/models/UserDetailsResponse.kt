package com.example.weltchallenge.data.models


import com.google.gson.annotations.SerializedName

data class UserDetailsResponse(
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    @SerializedName("bio")
    val bio: String?,
    @SerializedName("company")
    val company: String?,
    @SerializedName("email")
    val email: Any?,
    @SerializedName("followers")
    val followers: Int?,
    @SerializedName("following")
    val following: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("location")
    val location: String?,
    @SerializedName("login")
    val login: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("public_repos")
    val publicRepos: Int?,
    @SerializedName("url")
    val url: String?
)