package com.example.trackandtrigger.data.data_items

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "item_table")
data class Items(
    @PrimaryKey(autoGenerate = true)
    val id_item: Int,
    val itemName: String,
    val quantity: Int,
    val category: String
    //val itemImage: Bitmap
): Parcelable
//
//@Parcelize
//@Entity(tableName = "category_table")
//data class Category(
//      @PrimaryKey
//      val id_category: Int,
//      val category: String,
//): Parcelable


