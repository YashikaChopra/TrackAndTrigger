package com.example.trackandtrigger.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trackandtrigger.activities.ItemsAdapter
import com.example.trackandtrigger.R
import com.example.trackandtrigger.activities.AddItemActivity
import com.example.trackandtrigger.data.data_items.ItemViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ItemsActivity : AppCompatActivity() {

    private lateinit var mItemViewModel: ItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items)

        val categoryC = intent.getStringExtra(EXTRA_MESSAGE)

        //RecyclerView
        val adapter = ItemsAdapter()
        val recyclerView = findViewById<View>(R.id.recyclerview_Items) as RecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager  = LinearLayoutManager(this)

        //ItemViewModel
        mItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        mItemViewModel.readAllData.observe(this, Observer{item ->
            adapter.setData(item)
        })

        //val categoryC = intent.getStringExtra(EXTRA_MESSAGE)

        val mFabItems = findViewById<FloatingActionButton>(R.id.floatingActionButtonItems)
        mFabItems.setOnClickListener{

            val intent = Intent(this, AddItemActivity::class.java).apply {
                putExtra("cate", categoryC)
            }
            startActivity(intent)
//            val action = ItemsFragmentDirections.actionItemsFragmentToAddItemFragment(currentCate)
//            it.findNavController().navigate(action)

        }


    }
}

