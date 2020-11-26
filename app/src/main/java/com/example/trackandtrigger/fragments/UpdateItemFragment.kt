package com.example.trackandtrigger.fragments

import android.app.AlertDialog
import android.content.ClipData
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.trackandtrigger.R
import com.example.trackandtrigger.data.ItemViewModel
import com.example.trackandtrigger.data.Items
import kotlinx.android.synthetic.main.fragment_update_item.*
import kotlinx.android.synthetic.main.fragment_update_item.view.*


class UpdateItemFragment : Fragment() {

    private val args by navArgs<UpdateItemFragmentArgs>()

    private lateinit var mItemViewModel: ItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update_item, container, false)

        mItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)

        view.update_item_name.setText(args.currentItem.itemName)
        view.update_item_quantity.setText(args.currentItem.quantity.toString())

        view.update_list_button.setOnClickListener {
            updateItem()
        }

        //Add menu
        setHasOptionsMenu(true)

        return view
    }

    private fun updateItem() {
        val itemName = update_item_name.text.toString()
        val quantity = Integer.parseInt(update_item_quantity.text.toString())
        val category = args.currentItem.category

        if (inputCheck(itemName, update_item_quantity.text)) {
            //Create Item Object
            val updatedItem = Items(args.currentItem.id_item, itemName, quantity, category)
            //Update Current Item
            mItemViewModel.updateItem(updatedItem)
            Toast.makeText(requireContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show()
            //Navigate back
            findNavController().navigate(R.id.action_updateItemFragment_to_itemsFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT)
                .show()
        }

    }

    private fun inputCheck(itemName: String, quantity: Editable): Boolean {
        return !(TextUtils.isEmpty(itemName) && quantity.isEmpty())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteItem()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteItem() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mItemViewModel.deleteItem(args.currentItem)
            Toast.makeText(
                requireContext(),
                "Successfully removed: ${args.currentItem.itemName}",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_updateItemFragment_to_itemsFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${args.currentItem.itemName}?")
        builder.setMessage("Are you sure you want to delete ${args.currentItem.itemName}?")
        builder.create().show()
    }

}