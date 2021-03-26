package com.sushi.izishopping.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sushi.izishopping.utils.database.dao.ShoppinglistDao
import com.sushi.izishopping.utils.database.entity.ShoppinlistEntity

sealed class ShoppinglistCreationViewModelState(
    open val errorMessage: String = "",
    open val creationButtonEnabled: Boolean = false
) {
    data class UpdateShoppinglistName(
        override val creationButtonEnabled: Boolean):
        ShoppinglistCreationViewModelState(
            creationButtonEnabled = creationButtonEnabled
        )

    class Success : ShoppinglistCreationViewModelState()
    class Failure(override val errorMessage: String) : ShoppinglistCreationViewModelState(errorMessage = "Une erreur est survenue durant la récupération de la liste.")

}

class ShoppinglistCreationViewModel : ViewModel() {

    lateinit var shoppinglistDao: ShoppinglistDao

    private val state = MutableLiveData<ShoppinglistCreationViewModelState>()

    fun getState() : LiveData<ShoppinglistCreationViewModelState> = state

    fun createShoppinglist(shoppinglistName: String) {
        shoppinglistDao.addShoppinglist(
            ShoppinlistEntity(
                shoppingListId = 0,
                shoppinglistName
            ))
        state.value = ShoppinglistCreationViewModelState.Success()
        /*if (isShoppingListNameValid(shoppinglistName)) {
            shoppinglistDao.addShoppinglist(
                ShoppinlistEntity(
                    shoppingListId = 0,
                    shoppinglistName
            ))
            state.value = ShoppinglistCreationViewModelState.Success()
        } else {
            state.value = ShoppinglistCreationViewModelState.Failure("Invalid Credentials")
        }*/
    }

    fun updateShoppinglistName(shoppinglistName: String) {
        val buttonEnabled = shoppinglistName.isNotBlank() && shoppinglistName.length > 3
        state.value = ShoppinglistCreationViewModelState.UpdateShoppinglistName(creationButtonEnabled = buttonEnabled)
    }
}

fun isShoppingListNameValid(shoppinglistName: String): Boolean {
    return Regex("[^a-zA-Z0-9 ~]").containsMatchIn(shoppinglistName.replace(" ", ""))
}