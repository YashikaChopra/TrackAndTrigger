package com.example.trackandtrigger.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.trackandtrigger.R
import com.example.trackandtrigger.data.data_category.Category
import com.example.trackandtrigger.data.data_category.CategoryViewModel
import kotlinx.android.synthetic.main.activity_add_category.*

class AddCategoryActivity : AppCompatActivity() {

    private lateinit var mCategoryViewModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)
        setTitle("Add Category")

        mCategoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)

        val mAddCategoryButton = findViewById<Button>(R.id.add_category_button)
        mAddCategoryButton.setOnClickListener {
            insertCategoryDataToDatabase()
        }
    }
    private fun insertCategoryDataToDatabase() {
        val categoryName = add_category_name.text.toString()

        if(inputCheck(categoryName)){
            //Create Item Object
            val category = Category(
                0,
                categoryName
            )
            // Add to Database
            mCategoryViewModel.addCategory(category)
            Toast.makeText(applicationContext, "Successfully Added!", Toast.LENGTH_SHORT).show()
            //Navigate back
            finish()
        }else{
            Toast.makeText(applicationContext, "Please fill the fields ", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(categoryName: String): Boolean{
        return !(TextUtils.isEmpty(categoryName))
    }

}