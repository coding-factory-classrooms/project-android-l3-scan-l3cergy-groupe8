package com.sushi.izishopping.foodlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sushi.izishopping.Food
import com.sushi.izishopping.databinding.ItemFoodBinding

// prend en parametre du constructeur, la FoodList
class FoodAdapter(private var foodList: List<Food>)
    : RecyclerView.Adapter<FoodAdapter.ViewHolder>(){
    class ViewHolder(val binding: ItemFoodBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFoodBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = foodList[position]
        with (holder.binding) {
            foodTitleTextView.text = food.name
            scanDateTextView.text = "TBD"
            barCodeTextView.text = food.barcode
            foodImageView.setImageResource(food.imgLink)
        }
    }

    override fun getItemCount(): Int = foodList.size

    fun updateDataSet(foodList: List<Food>) {
        this.foodList = foodList
        notifyDataSetChanged()
    }
}