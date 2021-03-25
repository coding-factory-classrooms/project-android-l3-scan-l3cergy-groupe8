package com.sushi.izishopping.foodlist

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.sushi.izishopping.Food
import com.sushi.izishopping.databinding.ItemFoodBinding
import java.io.*
import java.net.URL


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
        with(holder.binding) {
            foodTitleTextView.text = food.name
            scanDateTextView.text = "TBD"
            barCodeTextView.text = food.barcode
            DownloadImageTask(foodImageView).execute(food.imgLink)
        }
    }

    override fun getItemCount(): Int = foodList.size

    fun updateDataSet(foodList: List<Food>) {
        this.foodList = foodList
        notifyDataSetChanged()
    }
}

private open class DownloadImageTask(@field:SuppressLint("StaticFieldLeak") var bmImage: ImageView) :
    AsyncTask<String?, Void?, Bitmap?>() {
    override fun doInBackground(vararg params: String?): Bitmap? {
        val urldisplay = params[0]
        var mIcon11: Bitmap? = null
        try {
            val `in` = URL(urldisplay).openStream()
            mIcon11 = BitmapFactory.decodeStream(`in`)
        } catch (e: Exception) {
            Log.e("Error", e.message!!)
            e.printStackTrace()
        }
        return mIcon11
    }

    override fun onPostExecute(result: Bitmap?) {
        bmImage.setImageBitmap(result)
    }

}