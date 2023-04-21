package com.example.weltchallenge.models

data class UserDetails (
    val id: Int,
    val username: String,
    val name: String,
    val avatarUrl: String,
    val bio: String,
    val company: String,
    val publicRepos: String,
    val followers: String,
    val following: Int,
    val location: String
)