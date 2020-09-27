package com.sk.calllogtaskapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.sk.calllogtaskapp.db.CallCacheEntity
import com.sk.calllogtaskapp.db.CallDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<CallCacheEntity>>
    private val repository: CallRepository

    init {
        val callDao = CallDatabase.getDatabase(
            application
        ).callDao()
        repository = CallRepository(callDao)
        readAllData = repository.readAllData
    }

    fun get(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.get()
        }
    }
}