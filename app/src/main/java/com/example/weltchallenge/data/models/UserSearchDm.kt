package com.example.weltchallenge.data.models


import com.google.gson.annotations.SerializedName

data class UserSearchDm(
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("login")
    val login: String?,
)