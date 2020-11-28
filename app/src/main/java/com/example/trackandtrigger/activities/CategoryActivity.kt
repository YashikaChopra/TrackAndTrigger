
package com.example.trackandtrigger.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trackandtrigger.R
import com.example.trackandtrigger.data.data_category.CategoryViewModel
import com.example.trackandtrigger.activities.AddCategoryActivity
import com.example.trackandtrigger.activities.CategoryAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CategoryActivity : AppCompatActivity() {

    //private lateinit var mCategoryViewModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_category)

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