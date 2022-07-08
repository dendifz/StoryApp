package com.dendi.storyapp.data.source.remote.story

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface StoryServices {

    @GET("stories")
    suspend fun getStory(
        @Header("Authorization") BearerToken: String
    ): StoryResponse

    @Multipart
    @POST("stories")
    suspend fun addStory(
        @Header("Authorization") BearerToken: String,
        @Part("description") desc: RequestBody,
        @Part file: MultipartBody.Part
    ): StoryResponse

    @GET("stories")
    suspend fun getStories(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("location") location: Int? = null
    ): StoryResponse

}