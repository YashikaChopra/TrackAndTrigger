
package com.example.trackandtrigger.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trackandtrigger.R
import com.example.trackandtrigger.data.data_category.CategoryViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CategoryActivity : AppCompatActivity() {

    //private lateinit var mCategoryViewModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_category)
        setTitle("Category")

        //RecyclerView
        val adapter = CategoryAdapter()
        val recyclerView = findViewById<View>(R.id.recyclerview_Category) as RecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
//        val recyclerView = viewCategory.recyclerview_fragmentCategory
//        recyclerView.adapter = adapter
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //CategoryViewModel
        val mCategoryViewModel: CategoryViewModel by viewModels()
        mCategoryViewModel.readAllCategoryData.observe(this, Observer { category ->
            adapter.setData(category)
        })



        val mFabCategory = findViewById<FloatingActionButton>(R.id.floatingActionButtonCategory)
        mFabCategory.setOnClickListener {

            val intent = Intent(this, AddCategoryActivity::class.java)
            startActivity(intent)


        }


    }


}