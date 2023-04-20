package com.example.weltchallenge.data.models


import com.google.gson.annotations.SerializedName

data class SearchUserResponse(
    @SerializedName("items")
    val userItems: List<UserSearchDm>,
    @SerializedName("total_count")
    val totalCount: Int?
)