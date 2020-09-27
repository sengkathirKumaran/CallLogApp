package com.sk.calllogtaskapp.db

import androidx.lifecycle.LiveData
import com.sk.calllogtaskapp.fragments.ContactsFragment

class ContactRepository(private val contactDao: ContactDao, private val callDao: CallDao) {
    val readData: LiveData<List<ContactCacheEntity>> = contactDao.readData()

    suspend fun allContacts() {
        ContactsFragment().searchList.addAll(contactDao.allContacts())
    }

    val favs: LiveData<List<ContactCacheEntity>> = contactDao.favs()

    suspend fun addUser(contact: CallCacheEntity) {
        callDao.addCall(contact)
    }

    suspend fun update(con: ContactCacheEntity) {
        contactDao.update(con)
    }

    suspend fun delete(con: ContactCacheEntity) {
        contactDao.delete(con)
    }

    suspend fun deleteMultiple(list: List<String>) {
        contactDao.deleteMultiple(list)
    }

}