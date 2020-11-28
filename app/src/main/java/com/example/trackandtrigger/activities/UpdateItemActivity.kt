package com.example.trackandtrigger.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.trackandtrigger.R
import com.example.trackandtrigger.data.data_items.ItemViewModel
import com.example.trackandtrigger.data.data_items.Items
import kotlinx.android.synthetic.main.activity_upadate_item.*

class UpdateItemActivity : AppCompatActivity() {

    private lateinit var mItemViewModel: ItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upadate_item)
        setTitle("Update item")

        mItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)

        val itemName = intent.getStringExtra("itemName")
        val quantity = intent.getStringExtra("quantity")
        val category = intent.getStringExtra("category")
        val idItem = intent.getIntExtra("id_name", 0)




        val mitemName = findViewById<TextView>(R.id.update_item_name)
        mitemName.setText(itemName)

        val mquantity = findViewById<TextView>(R.id.update_item_quantity)
        mquantity.setText(quantity.toString())

        val mUpdateItemButton = findViewById<Button>(R.id.update_list_button)
        mUpdateItemButton.setOnClickListener {
            updateItem(category, idItem)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.delete_menu, menu)
        return true
    }

    private fun updateItem(category: String, idItem: Int) {
        val itemName = update_item_name.text.toString()
        val quantity = Integer.parseInt(update_item_quantity.text.toString())

        if (inputCheck(itemName, update_item_quantity.text)) {
            //Create Item Object
            val updatedItem = Items(idItem, itemName, quantity, category)
            //Update Current Item
            mItemViewModel.updateItem(updatedItem)
            Toast.makeText(applicationContext, "Updated Successfully!", Toast.LENGTH_SHORT).show()
            //Navigate back
            finish()
        } else {
            Toast.makeText(applicationContext, "Please fill out all fields.", Toast.LENGTH_SHORT)
                .show()
        }



    }

    private fun inputCheck(itemName: String, quantity: Editable): Boolean {
        return !(TextUtils.isEmpty(itemName) && quantity.isEmpty())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            val idItem = intent.getIntExtra("id_name", 0)
            val category = intent.getStringExtra("category")
            deleteItem(idItem, category)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteItem(idItem: Int, category: String) {
        val itemName = update_item_name.text.toString()
        val quantity = Integer.parseInt(update_item_quantity.text.toString())

        val deleteItem = Items(idItem, itemName, quantity, category)
        val builder = AlertDialog.Builder(this)
        builder.setPositiveButton("Yes") { _, _ ->
            mItemViewModel.deleteItem(deleteItem)
            Toast.makeText(
                applicationContext,
                "Successfully removed: ${itemName}",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${itemName}?")
        builder.setMessage("Are you sure you want to delete ${itemName}?")
        builder.create().show()
    }

}