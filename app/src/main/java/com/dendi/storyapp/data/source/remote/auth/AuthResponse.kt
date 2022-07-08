package com.dendi.storyapp.data.source.remote.auth

import com.dendi.storyapp.data.model.User

data class AuthResponse(

    val error: Boolean,
    val message: String,
    val loginResult: User
)