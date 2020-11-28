package com.example.trackandtrigger.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.trackandtrigger.R
import com.example.trackandtrigger.data.data_items.ItemViewModel
import kotlinx.android.synthetic.main.activity_upadate_item.*

class UpdateItemActivity : AppCompatActivity() {

    private lateinit var mItemViewModel: ItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upadate_item)

        mItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)

        val itemName = intent.getStringExtra("itemName")
        val quantity = intent.getStringExtra("quantity")
        val category = intent.getStringExtra("category")





        val mUpdateItemButton = findViewById<Button>(R.id.update_list_button)
        mUpdateItemButton.setOnClickListener {
            updateItem()
        }

    }

    private fun updateItem() {
        val itemName = update_item_name.text.toString()
        val quantity = Integer.parseInt(update_item_quantity.text.toString())



    }

//    val itemName = update_item_name.text.toString()
//    val quantity = Integer.parseInt(update_item_quantity.text.toString())
//    val category = args.currentItem.category
//
//    if (inputCheck(itemName, update_item_quantity.text)) {
//        //Create Item Object
//        val updatedItem = Items(args.currentItem.id_item, itemName, quantity, category)
//        //Update Current Item
//        mItemViewModel.updateItem(updatedItem)
//        Toast.makeText(requireContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show()
//        //Navigate back
//        findNavController().navigate(R.id.action_updateItemFragment_to_itemsFragment)
//    } else {
//        Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT)
//            .show()
//    }
}