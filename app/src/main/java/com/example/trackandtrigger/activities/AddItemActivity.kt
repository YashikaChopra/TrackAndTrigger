package com.example.trackandtrigger.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.trackandtrigger.R
import com.example.trackandtrigger.data.data_items.ItemViewModel
import com.example.trackandtrigger.data.data_items.Items
import kotlinx.android.synthetic.main.activity_add_item.*

class AddItemActivity : AppCompatActivity() {

    private lateinit var mItemViewModel: ItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)
        setTitle("Add Item")

        mItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)

        val category = intent.getStringExtra("cate").toString()

        val mAddItemButton = findViewById<Button>(R.id.add_to_list_button)
        mAddItemButton.setOnClickListener {
            insertDataToDatabase(category)
        }


    }

    private fun insertDataToDatabase(category: String) {

        val itemName = add_item_name.text.toString()
        val quantity = add_item_quantity.text
        //val category = args.currentCategory.categoryName
        //
        // val category = requireArguments().getString("key")

//        arguments?.let {
//            val category = AddItemFragmentArgs.fromBundle(it)
//        }


        if(inputCheck(itemName, quantity)){
            //Create Item Object
            val item = Items(
                0,
                itemName,
                Integer.parseInt(quantity.toString()),
                category
            )
            // Add to Database
            mItemViewModel.addItem(item)
            Toast.makeText(applicationContext, "Successfully Added!", Toast.LENGTH_SHORT).show()
            //Navigate back
            finish()
        }else{
            Toast.makeText(applicationContext, "Please enter Item Name. ", Toast.LENGTH_SHORT).show()
        }

    }

    private fun inputCheck(itemName: String, quantity: Editable): Boolean{
        return !(TextUtils.isEmpty(itemName) && quantity.isEmpty())
    }

}
