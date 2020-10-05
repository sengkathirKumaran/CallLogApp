package com.sk.calllogtaskapp.db

import androidx.lifecycle.LiveData

class ContactRepository(private val contactDao: ContactDao, private val callDao: CallDao) {
    val readData: LiveData<List<ContactCacheEntity>> = contactDao.readData()





//    suspend fun allContacts(): List<ContactCacheEntity> {
//        contactDao.allContacts()
//    }

    val favs: LiveData<List<ContactCacheEntity>> = contactDao.favs()

    suspend fun addUser(contact: CallCacheEntity) {
        callDao.addCall(contact)
    }

    suspend fun update(con: ContactCacheEntity) {
        contactDao.update(con)
    }

    suspend fun deleteMultiple(list: List<String>) {
        contactDao.deleteMultiple(list)
    }

}