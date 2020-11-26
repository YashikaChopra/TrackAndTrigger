package com.example.trackandtrigger.data.data_category

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "category_table")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id_category: Int,
    val categoryName: String
): Parcelable