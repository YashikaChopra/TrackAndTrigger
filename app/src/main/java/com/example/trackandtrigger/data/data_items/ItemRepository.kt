package com.example.trackandtrigger.data.data_items

import androidx.lifecycle.LiveData

class ItemRepository(private val itemDao: ItemDao) {
    val readAllData: LiveData<List<Items>> = itemDao.readAllData()

    suspend fun addItem(item: Items){
        itemDao.addItem(item)
    }

    suspend fun updateItem(item: Items){
        itemDao.updateItem((item))
    }

    suspend fun deleteItem(item: Items){
        itemDao.deleteItem(item)
    }
}