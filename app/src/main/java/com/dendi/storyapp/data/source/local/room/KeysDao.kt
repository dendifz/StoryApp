package com.dendi.storyapp.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface KeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<KeysEntity>)


    @Query("SELECT * FROM keys WHERE id = :id")
    suspend fun getRemoteKeysId(id: String): KeysEntity?


    @Query("DELETE FROM keys")
    suspend fun deleteKeys()
}