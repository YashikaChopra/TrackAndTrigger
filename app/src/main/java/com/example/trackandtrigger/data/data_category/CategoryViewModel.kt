package com.example.trackandtrigger.data.data_category

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.trackandtrigger.data.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application): AndroidViewModel(application) {

    val readAllCategoryData: LiveData<List<Category>>
    private val repositoryCategory: CategoryRepository

    init {
        val categoryDao = AppDatabase.getDatabase(application).categoryDao()
        repositoryCategory = CategoryRepository((categoryDao))
        readAllCategoryData = repositoryCategory.readAllCategoryData
    }

    fun addCategory(category: Category){
        viewModelScope.launch(Dispatchers.IO) {
            repositoryCategory.addCategory(category)
        }
    }

}
