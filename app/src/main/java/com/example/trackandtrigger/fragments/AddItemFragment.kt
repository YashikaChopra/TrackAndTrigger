package com.example.trackandtrigger.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.trackandtrigger.R
import com.example.trackandtrigger.data.ItemViewModel
import com.example.trackandtrigger.data.Items
import kotlinx.android.synthetic.main.fragment_add_item.*
import kotlinx.android.synthetic.main.fragment_add_item.view.*


class AddItemFragment : Fragment() {

    private lateinit var mItemViewModel: ItemViewModel

    //private val args by navArgs<AddItemFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_item, container, false)

        mItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)


//        arguments?.let {
//            val category = AddItemFragmentArgs.fromBundle(it)
//        }


        view.add_to_list_button.setOnClickListener{

            arguments?.let {
                val args = AddItemFragmentArgs.fromBundle(it)
                insertDataToDatabase(args.currentCate)
            }


        }


        return view
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
            Toast.makeText(requireContext(), "Successfully Added!", Toast.LENGTH_SHORT).show()
            //Navigate back
            findNavController().navigate(R.id.action_addItemFragment_to_itemsFragment)
        }else{
            Toast.makeText(requireContext(), "Please enter Item Name. ", Toast.LENGTH_SHORT).show()
        }

    }

    private fun inputCheck(itemName: String, quantity: Editable): Boolean{
        return !(TextUtils.isEmpty(itemName) && quantity.isEmpty())
    }
}

