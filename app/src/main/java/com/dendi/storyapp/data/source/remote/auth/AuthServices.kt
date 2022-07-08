package com.dendi.storyapp.data.source.remote.auth

import retrofit2.http.Body
import retrofit2.http.POST

interface AuthServices {

    @POST("register")
    suspend fun registerUser(
        @Body authBody: AuthBody
    ): AuthResponse

    @POST("login")
    suspend fun loginUser(
        @Body authBody: AuthBody
    ): AuthResponse

}