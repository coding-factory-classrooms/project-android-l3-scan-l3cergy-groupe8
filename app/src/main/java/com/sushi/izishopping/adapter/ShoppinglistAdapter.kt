package com.sushi.izishopping.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sushi.izishopping.activity.FoodListActivity
import com.sushi.izishopping.databinding.ItemShoppinglistBinding
import com.sushi.izishopping.model.Shoppinglist

class ShoppinglistAdapter(private var shoppinglist: List<Shoppinglist>)
    : RecyclerView.Adapter<ShoppinglistAdapter.ViewHolder>(){
    class ViewHolder(val binding: ItemShoppinglistBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemShoppinglistBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val shoppinglist = shoppinglist[position]

        with (holder.binding) {
            shoppinglistTextView.text = shoppinglist.name
            shoppinglistNbFoodTextView.text = shoppinglist.nbFoodInShoppinglist.toString()

            root.setOnClickListener{
                val detailIntent = Intent(root.context, FoodListActivity::class.java)
                detailIntent.putExtra("shoppingListId", shoppinglist.id)
                root.context.startActivity(detailIntent)
            }
        }

    }

    override fun getItemCount(): Int = shoppinglist.size

    fun updateDataSet(shoppinglist: List<Shoppinglist>) {
        this.shoppinglist = shoppinglist
        notifyDataSetChanged()
    }
    
}