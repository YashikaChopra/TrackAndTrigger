package com.example.trackandtrigger.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.trackandtrigger.R
import com.example.trackandtrigger.data.Items
import kotlinx.android.synthetic.main.single_item_row.view.*

class ItemsAdapter: RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

    private var itemList = emptyList<Items>()

    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.single_item_row, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.itemView.item_name.text = currentItem.itemName
        holder.itemView.item_quantity.text = currentItem.quantity.toString()

        holder.itemView.singleItem_row.setOnClickListener{
            val action = ItemsFragmentDirections.actionItemsFragmentToUpdateItemFragment(currentItem)
            holder.itemView.findNavController().navigate(action)

        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setData(item: List<Items>){
        this.itemList = item
        notifyDataSetChanged()
    }
}