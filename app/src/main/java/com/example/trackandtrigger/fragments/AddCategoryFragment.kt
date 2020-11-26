package com.example.trackandtrigger.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.trackandtrigger.R
import com.example.trackandtrigger.data.Items
import com.example.trackandtrigger.data.data_category.Category
import com.example.trackandtrigger.data.data_category.CategoryViewModel
import kotlinx.android.synthetic.main.fragment_add_category.*
import kotlinx.android.synthetic.main.fragment_add_category.view.*


class AddCategoryFragment : Fragment() {

    private lateinit var mCategoryViewModel: CategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_category, container, false)

        mCategoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)

        view.add_category_button.setOnClickListener {
            insertCategoryDataToDatabase()
        }
        return view
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
            Toast.makeText(requireContext(), "Successfully Added!", Toast.LENGTH_SHORT).show()
            //Navigate back
            findNavController().navigate(R.id.action_addCategoryFragment_to_categoryFragment2)
        }else{
            Toast.makeText(requireContext(), "Please fill the fields ", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(categoryName: String): Boolean{
        return !(TextUtils.isEmpty(categoryName))
    }

}