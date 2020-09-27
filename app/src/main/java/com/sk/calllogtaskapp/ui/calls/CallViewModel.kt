package com.sk.calllogtaskapp.ui.calls

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.sk.calllogtaskapp.CallRepository
import com.sk.calllogtaskapp.db.CallCacheEntity
import com.sk.calllogtaskapp.db.CallDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CallViewModel(application: Application) : AndroidViewModel(application) {

    /*private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text*/

    val readAllData: LiveData<List<CallCacheEntity>>
    private val repository: CallRepository

    init {
        val userDao = CallDatabase.getDatabase(
            application
        ).callDao()
        repository = CallRepository(userDao)
        readAllData = repository.readAllData
    }

    fun addCall(call: CallCacheEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(call)
        }
    }

}