package com.sk.calllogtaskapp.ui.contacts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.sk.calllogtaskapp.CallRepository
import com.sk.calllogtaskapp.db.CallCacheEntity
import com.sk.calllogtaskapp.db.ContactCacheEntity
import com.sk.calllogtaskapp.db.CallDatabase
import com.sk.calllogtaskapp.db.ContactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactViewModel(application: Application) : AndroidViewModel(application) {

    /*private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text*/


    val readAllData: LiveData<List<ContactCacheEntity>>
    val readFavs: LiveData<List<ContactCacheEntity>>
    private val repository: ContactRepository

    init {
        val contactDao = CallDatabase.getDatabase(
            application
        ).contactDao()

        val callDao = CallDatabase.getDatabase(application).callDao()
        repository = ContactRepository(contactDao, callDao)
        readAllData = repository.readData
        readFavs = repository.favs
    }

    fun allContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.allContacts()
        }
    }

    fun addCall(call: CallCacheEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(call)
        }
    }

    fun update(con: ContactCacheEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(con)
        }
    }

    fun delete(con: ContactCacheEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(con)
        }
    }

    fun deleteMultiple(list: List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMultiple(list)
        }
    }
}