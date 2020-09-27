package com.sk.calllogtaskapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CallDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(callEntity: CallCacheEntity): Long

    @Query("SELECT * FROM calls")
    suspend fun get(): List<CallCacheEntity>

    @Query("SELECT * FROM calls ORDER BY id DESC")
    fun readAllData(): LiveData<List<CallCacheEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addCall(contact: CallCacheEntity)



}