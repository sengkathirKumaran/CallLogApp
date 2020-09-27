package com.sk.calllogtaskapp.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContactDao {
    @Query("SELECT * FROM contacts ORDER BY id ASC")
    fun readData(): LiveData<List<ContactCacheEntity>>

    @Query("SELECT * FROM contacts")
    fun allContacts(): List<ContactCacheEntity>


    @Query("SELECT * FROM contacts WHERE favourites='Y'")
    fun favs(): LiveData<List<ContactCacheEntity>>

//    @Query("UPDATE contacts SET favourites='N' WHERE ")
//    fun update()

    @Update
    fun update(vararg con: ContactCacheEntity)

    @Delete
    fun delete(vararg con: ContactCacheEntity)

    @Query("DELETE FROM contacts WHERE number IN (:list)")
    fun deleteMultiple(list: List<String>)
}