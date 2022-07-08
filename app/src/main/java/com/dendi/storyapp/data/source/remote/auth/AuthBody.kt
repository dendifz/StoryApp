package com.dendi.storyapp.data.source.remote.auth

data class AuthBody(
    val name: String? = null,
    val email: String,
    val password: String
)
