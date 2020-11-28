package com.example.trackandtrigger.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.trackandtrigger.data.data_category.Category
import com.example.trackandtrigger.data.data_category.CategoryDao
import com.example.trackandtrigger.data.data_items.ItemDao
import com.example.trackandtrigger.data.data_items.Items


@Database(entities = [Items::class, Category::class], version = 1, exportSchema = false)
abstract class  AppDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao

    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "item_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}