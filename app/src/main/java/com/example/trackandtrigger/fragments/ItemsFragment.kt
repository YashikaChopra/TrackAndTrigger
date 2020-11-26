package com.example.trackandtrigger.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trackandtrigger.R
import com.example.trackandtrigger.data.ItemViewModel
import com.example.trackandtrigger.data.Items
import kotlinx.android.synthetic.main.fragment_items.view.*



class ItemsFragment : Fragment() {

    private lateinit var mItemViewModel: ItemViewModel



    private val args by navArgs<ItemsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_items, container, false)

        //Recycler View
        val adapter = ItemsAdapter()
        val recyclerView = view.recyclerview_fragmentItems
        recyclerView.adapter = adapter
        recyclerView.layoutManager  = LinearLayoutManager(requireContext())

        //ItemViewModel
        mItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        mItemViewModel.readAllData.observe(viewLifecycleOwner, Observer { item ->
            adapter.setData(item)
        })

        view.floatingActionButton.setOnClickListener{
            val currentCate = args.currentCategory.categoryName


            val action = ItemsFragmentDirections.actionItemsFragmentToAddItemFragment(currentCate)
            it.findNavController().navigate(action)


//            val additemfragment = AddItemFragment()
//            val bundle = Bundle()
//            bundle.putString("key", "currentCate")
//            additemfragment.arguments = bundle
//
//
//            findNavController().navigate(R.id.action_itemsFragment_to_addItemFragment)
        }


        return view
    }

}

//view.floatingActionButton.setOnClickListener{
//    val currentCate = args.currentCategory.categoryName
//    //val action = ItemsFragmentDirections.actionItemsFragmentToAddItemFragment(currentCate)
//    findNavController().navigate(R.id.action_itemsFragment_to_addItemFragment)
//}
