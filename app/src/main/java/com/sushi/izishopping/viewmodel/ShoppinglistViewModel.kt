package com.sushi.izishopping.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sushi.izishopping.model.Shoppinglist
import com.sushi.izishopping.utils.database.dao.ShoppinglistDao

sealed class ShoppinglistListModelState(
    open val errorMessage: String = ""
) {
    class Loading() : ShoppinglistListModelState()
    class Empty() : ShoppinglistListModelState(errorMessage = "La liste de liste de course est vide.")
    data class Success(val shoppinglistList : List<Shoppinglist>) : ShoppinglistListModelState()
    class Failure() : ShoppinglistListModelState(errorMessage = "Une erreur est survenue durant la récupération de la liste.")

}

class ShoppinglistViewModel : ViewModel() {

    lateinit var shoppinglistDao: ShoppinglistDao

    var shoppinglistList : List<Shoppinglist> = listOf()

    private val state = MutableLiveData<ShoppinglistListModelState>()

    fun getInfosShoppinglist() : LiveData<ShoppinglistListModelState> = state

    fun getShoppinglistList() {

        state.postValue(ShoppinglistListModelState.Loading())

        shoppinglistList = shoppinglistDao.getAllShoppinglist().map { shoppinglistEntity ->
            Shoppinglist(shoppinglistEntity.name, shoppinglistEntity.shoppingListId, shoppinglistDao.getNbFoodInShoppinglist(shoppinglistEntity.shoppingListId))
        }

        when {
            shoppinglistList.isEmpty() -> {
                state.postValue(ShoppinglistListModelState.Empty())
            }
            shoppinglistList.isNotEmpty() -> {
                state.postValue(ShoppinglistListModelState.Success(shoppinglistList))
            }
            else -> {
                state.postValue(ShoppinglistListModelState.Failure())
            }
        }
    }
}