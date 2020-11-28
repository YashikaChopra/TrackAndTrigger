package com.example.trackandtrigger.activities

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.trackandtrigger.activities.ItemsActivity
import com.example.trackandtrigger.data.data_category.Category
import com.example.trackandtrigger.R

import kotlinx.android.synthetic.main.single_category_row.view.*

const val EXTRA_MESSAGE = "com.example.track_trigger.CATEGORY"

class CategoryAdapter: RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var categoryList = emptyList<Category>()

    class CategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.single_category_row, parent, false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val currentItem = categoryList[position]
        holder.itemView.category_name.text = currentItem.categoryName

        holder.itemView.singleCategory_row.setOnClickListener{
            val currentCate = currentItem.toString()
            val context = holder.itemView.category_name.context
            val intent = Intent(context, ItemsActivity::class.java)
            intent.putExtra(EXTRA_MESSAGE, currentCate)
            context.startActivity(intent)


//            val action = CategoryFragmentDirections.actionCategoryFragmentToItemsFragment(currentItem)
//            holder.itemView.findNavController().navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    fun setData(category: List<Category>){
        this.categoryList = category
        notifyDataSetChanged()
    }

}

//val action = CategoryFragmentDirections.actionCategoryFragmentToItemsFragment(currentItem)
//holder.itemView.singleCategory_row.setOnClickListener {
//    //no argument in action........... CategoryFragmentDirections.actionCategoryFragmentToItemsFragment(......currentItem.........)!!!!!!!!!!!!!!!!!!!!!
//    val action = CategoryFragmentDirections.actionCategoryFragmentToItemsFragment(currentItem)
//    holder.itemView.findNavController().navigate(action)
//}