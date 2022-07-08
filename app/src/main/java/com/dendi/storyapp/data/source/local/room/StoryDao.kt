package com.dendi.storyapp.data.source.local.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dendi.storyapp.data.model.Story

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(story: List<Story>)

    @Query("SELECT * FROM Story")
    fun getAllStory(): PagingSource<Int, Story>


    @Query("SELECT * FROM Story")
    suspend fun getMapAll(): List<Story>


    @Query("DELETE FROM Story")
    suspend fun deleteAll()
}