package com.sushi.izishopping.fooddetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sushi.izishopping.Food
import com.sushi.izishopping.R


private val foodItem : List<Food> = listOf(
    Food("3329770063297", "YOP Parfum Vanille", R.drawable.food_yop_vanille),
)

sealed class FoodDetailViewModelState (
    open val errorMessage: String = ""
) {
    class Loading() : FoodDetailViewModelState()
    class Empty() : FoodDetailViewModelState(errorMessage = "La page de détail est vide.")
    data class Success(val foodList : List<Food>) : FoodDetailViewModelState()
    class Failure() : FoodDetailViewModelState(errorMessage = "Une erreur est survenue durant la récupération du détail du produit.")
}

class FoodDetailViewModel : ViewModel() {
    private val state = MutableLiveData<FoodDetailViewModelState>()

    fun getInfos() : LiveData<FoodDetailViewModelState> = state

    fun getFoodDetail() {

        state.postValue(FoodDetailViewModelState.Loading())
//        TODO("Ajouter récupération des data via la BDD")

        when {
            foodItem.isEmpty() -> {
                state.postValue(FoodDetailViewModelState.Empty())
            }
            foodItem.isNotEmpty() -> {
                state.postValue(FoodDetailViewModelState.Success(foodItem))
            }
            else -> {
                state.postValue(FoodDetailViewModelState.Failure())
            }
        }
    }

}