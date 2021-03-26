package com.sushi.izishopping.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.sushi.izishopping.databinding.ActivityLoginBinding
import com.sushi.izishopping.viewmodel.LoginViewModel
import com.sushi.izishopping.viewmodel.LoginViewModelState

private const val TAG = "LoginActivity"

class LoginActivity : AppCompatActivity() {

    private val model: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Je m'abonne sur les etats de mon ecran
        model.getState().observe(this, Observer { updateUI(it!!) })

        // Lorsque je clique sur mon button Login
        binding.loginButton.setOnClickListener {
            // Je transfere l'information a mon viewModel
            model.login(
                binding.usernameEditText.text.toString(),
                binding.passwordEditText.text.toString())
        }

        binding.usernameEditText.doAfterTextChanged { model.updateLogin(
            binding.usernameEditText.text.toString(),
            binding.passwordEditText.text.toString()
        ) }

        binding.passwordEditText.doAfterTextChanged { model.updateLogin(
            binding.usernameEditText.text.toString(),
            binding.passwordEditText.text.toString()
        ) }

        // etat initial
        model.updateLogin(
            binding.usernameEditText.text.toString(),
            binding.passwordEditText.text.toString()
        )
    }

    // mon model fait des traitements mais je m'en tape je suis une view

    // Je m'attends a recevoir en retour un etat d'ecran et j'adapte en fonction
    private fun updateUI(state: LoginViewModelState) {
        Log.i(TAG, "updateUI: state=$state")
        when (state) {
            LoginViewModelState.Success -> {
                binding.loginButton.isEnabled = state.loginButtonEnabled
                Toast.makeText(this@LoginActivity,
                    "Login OK ! Navigation vers la liste de films..",
                    Toast.LENGTH_SHORT).show()

                navigateToMainActivity()
            }
            is LoginViewModelState.Failure -> {
                binding.loginButton.isEnabled = state.loginButtonEnabled
                Toast.makeText(this@LoginActivity,
                    "Tu n'es qu'un con. Recommence",
                    Toast.LENGTH_LONG).show()
            }
            is LoginViewModelState.UpdateLogin -> {
                binding.loginButton.isEnabled = state.loginButtonEnabled
            }

        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, FoodListActivity::class.java)
        startActivity(intent)
        finish()
    }

}