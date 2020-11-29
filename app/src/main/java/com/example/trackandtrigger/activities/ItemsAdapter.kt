package com.example.trackandtrigger.activities

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.trackandtrigger.activities.UpdateItemActivity
import com.example.trackandtrigger.R
import com.example.trackandtrigger.data.data_items.Items
import kotlinx.android.synthetic.main.single_category_row.view.*
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
//            val action = ItemsFragmentDirections.actionItemsFragmentToUpdateItemFragment(currentItem)
//            holder.itemView.findNavController().navigate(action)


            val itemNameForUpdate = currentItem.itemName
            val itemQuantityForUpdate = currentItem.quantity.toString()
            val itemIdForUpdate = currentItem.id_item
            val context = holder.itemView.item_name.context
            val intent = Intent(context, UpdateItemActivity::class.java).apply {
                putExtra("itemName", itemNameForUpdate )
                putExtra("quantity", itemQuantityForUpdate)
                putExtra("category", currentItem.category)
                putExtra("id_name", itemIdForUpdate)
                putExtra("currentItem", currentItem)
            }
            context.startActivity(intent)




//            val currentCate = currentItem.toString()
//            val context = holder.itemView.category_name.context
//            val intent = Intent(context, ItemsActivity::class.java)
//            intent.putExtra(EXTRA_MESSAGE, currentCate)
//            context.startActivity(intent)
        }
        holder.itemView.share_button.setOnClickListener {
            val context = holder.itemView.share_button.context
            val message = currentItem.itemName + " left: " + currentItem.quantity.toString()
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.putExtra(Intent.EXTRA_TEXT, message)
            shareIntent.type = "text/plain"

            context.startActivity(Intent.createChooser(shareIntent, "Share via : "))
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