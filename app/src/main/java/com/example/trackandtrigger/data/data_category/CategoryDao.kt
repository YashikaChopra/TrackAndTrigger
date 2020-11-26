package com.example.trackandtrigger.data.data_category

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCategory(category: Category)

    @Query("SELECT * FROM category_table ORDER BY id_category ASC")
    fun readAllCategoryData(): LiveData<List<Category>>


}