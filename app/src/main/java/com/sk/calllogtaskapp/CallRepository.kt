package com.sk.calllogtaskapp

import androidx.lifecycle.LiveData
import com.sk.calllogtaskapp.db.CallCacheEntity
import com.sk.calllogtaskapp.db.CallDao
import com.sk.calllogtaskapp.db.ContactCacheEntity

class CallRepository(private val callDao: CallDao) {


    val readAllData: LiveData<List<CallCacheEntity>> = callDao.readAllData()


    suspend fun get() {
        println("ALL DATA=>${readAllData.toString()}")
        callDao.get()
    }

    fun addUser(contact: CallCacheEntity){
        callDao.addCall(contact)
    }

}