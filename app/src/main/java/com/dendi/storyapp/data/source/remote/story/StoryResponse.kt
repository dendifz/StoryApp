package com.dendi.storyapp.data.source.remote.story

import com.dendi.storyapp.data.model.Story

data class StoryResponse(

    val listStory: List<Story>,
    val error: Boolean,
    val message: String
)

