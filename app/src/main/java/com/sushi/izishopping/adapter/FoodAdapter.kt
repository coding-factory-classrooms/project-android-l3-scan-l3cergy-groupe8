package com.sushi.izishopping.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sushi.izishopping.utils.DownloadImageFromUrl
import com.sushi.izishopping.model.Food
import com.sushi.izishopping.activity.FoodDetailActivity
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
            scanDateTextView.text = food.dateScan
            barCodeTextView.text = food.barcode
            DownloadImageFromUrl(foodImageView).execute(food.imgLink)
            root.setOnClickListener {
                val context = holder.binding.root.context
                val detailIntent = Intent(context, FoodDetailActivity::class.java)
                detailIntent.putExtra("food", food)
                context.startActivity(detailIntent)
            }
        }

    }

    override fun getItemCount(): Int = foodList.size

    fun updateDataSet(foodList: List<Food>) {
        this.foodList = foodList
        notifyDataSetChanged()
    }
}