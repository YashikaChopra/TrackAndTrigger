package com.example.trackandtrigger.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addItem(item: Items)

    @Update
    suspend fun updateItem(item: Items)

    @Delete
    suspend fun deleteItem(item: Items)

    @Query("SELECT * FROM item_table")
    fun readAllData(): LiveData<List<Items>>
    //fun readData(): LiveData<List<Items>>


}