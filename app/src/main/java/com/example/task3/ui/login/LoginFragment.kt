package com.example.task3.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.domain.model.responses.ApiResponse
import com.example.domain.model.request.AuthUser
import com.example.task3.R
import com.example.task3.ui.CoroutinesErrorHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val viewModel: AuthViewModel by viewModels()
    private val tokenViewModel: TokenViewModel by activityViewModels()
    private lateinit var navController: NavController
    private var token: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)
        val loginTV = view.findViewById<TextView>(R.id.textlogin)
        val tokeJob=viewLifecycleOwner.lifecycleScope.launch{
            tokenViewModel.token.collect { token ->
                if (token != null) navController.navigate(R.id.action_loginFragment_to_mainFragment)
            }
        }
      val userJob=  viewLifecycleOwner.lifecycleScope.launch {
          tokeJob.join()
            viewModel.loginResponse.collect { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        tokenViewModel.saveToken(response.data.token)
                    }
                    is ApiResponse.Failure<*> -> {
                        loginTV.text = response.errorMessage.toString()
                    }
                    ApiResponse.Loading -> {
                        loginTV.text = getString(R.string.loading_state)
                    }
                }
            }
        }
        view.findViewById<Button>(R.id.loginButton).setOnClickListener {
            viewModel.login(
                AuthUser("test@gmail.com", "123Test"),
                object: CoroutinesErrorHandler {
                    override fun onError(message: String) {
                        loginTV.text = "Error! $message"
                    }
                }
            )
        }
    }
}