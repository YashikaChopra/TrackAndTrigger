package com.example.trackandtrigger.data.data_category

import androidx.lifecycle.LiveData

class CategoryRepository(private val categoryDao: CategoryDao) {

    val readAllCategoryData: LiveData<List<Category>> = categoryDao.readAllCategoryData()

    suspend fun addCategory(category: Category){
        categoryDao.addCategory(category)
    }

}