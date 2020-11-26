package com.example.trackandtrigger.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trackandtrigger.R
import com.example.trackandtrigger.data.data_category.CategoryViewModel
import kotlinx.android.synthetic.main.fragment_category.view.*


class CategoryFragment : Fragment() {

    private lateinit var mCategoryViewModel: CategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val viewCategory =  inflater.inflate(R.layout.fragment_category, container, false)

        //RecyclerView
        val adapter = CategoryAdapter()
        val recyclerView = viewCategory.recyclerview_fragmentCategory
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //CategoryViewModel
        mCategoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        mCategoryViewModel.readAllCategoryData.observe(viewLifecycleOwner, Observer {category ->
            adapter.setData(category)
        })


        viewCategory.floatingActionButtonCategory.setOnClickListener {
            findNavController().navigate(R.id.action_categoryFragment_to_addCategoryFragment)
        }

        return viewCategory
    }


}

