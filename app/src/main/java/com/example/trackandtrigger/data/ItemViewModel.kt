package com.example.trackandtrigger.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Items>>
    private val repository: ItemRepository

    init {
        val itemDao = AppDatabase.getDatabase(application).itemDao()
        repository = ItemRepository(itemDao)
        readAllData = repository.readAllData
    }

    fun addItem(item: Items){
        viewModelScope.launch(Dispatchers.IO){
            repository.addItem(item)
        }
    }

    fun updateItem(item: Items){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateItem(item)
        }
    }

    fun deleteItem(item: Items){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(item)
        }
    }

}