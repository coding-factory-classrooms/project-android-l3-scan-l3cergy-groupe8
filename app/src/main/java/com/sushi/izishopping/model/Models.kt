package com.sushi.izishopping.model

import android.os.Parcel
import android.os.Parcelable
import com.sushi.izishopping.utils.database.entity.FoodEntity

data class Food(
    val barcode: String,
    val name: String,
    val dateScan: String,
    val imgLink: String,
    val nutriScore : String)
    : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(barcode)
        parcel.writeString(name)
        parcel.writeString(dateScan)
        parcel.writeString(imgLink)
        parcel.writeString(nutriScore)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Food> {
        override fun createFromParcel(parcel: Parcel): Food {
            return Food(parcel)
        }

        override fun newArray(size: Int): Array<Food?> {
            return arrayOfNulls(size)
        }
    }
    fun toFoodEntity() : FoodEntity {
        return FoodEntity(
            0,
            this.barcode,
            this.name,
            this.dateScan
        )
    }
}

data class Shoppinglist(
    val name: String,
    val id : Int,
    val nbFoodInShoppinglist: Int
)
