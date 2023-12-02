package com.example.task3.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.domain.model.responses.ApiResponse
import com.example.task3.R
import com.example.task3.ui.CoroutinesErrorHandler
import com.example.task3.ui.login.TokenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private val viewModel: DetailsViewModel by viewModels()
    private val tokenViewModel: TokenViewModel by viewModels()

    private lateinit var navController: NavController
    private var token: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
         val x= viewLifecycleOwner.lifecycleScope.launch {
              tokenViewModel.token.collect{ token ->
                  if(token==null)
                      navController.navigate(R.id.action_mainFragment_to_loginFragment)

              }
          }

        val mainTV = view.findViewById<TextView>(R.id.infoTV)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.userInfoResponse.collect{ dataState->
                mainTV.text= when(dataState){
                    is ApiResponse.Success ->"ID: ${dataState.data.userInfo._id}\nMail: ${dataState.data.userInfo.email_address}\n\nToken: $token"
                    is ApiResponse.Failure -> "Code: ${dataState.code}, ${dataState.errorMessage}"
                    ApiResponse.Loading -> getString(R.string.loading_state)
                }
            }
        }
        view.findViewById<Button>(R.id.infoButton).setOnClickListener {
            viewModel.getUserInfo(object: CoroutinesErrorHandler {
                override fun onError(message: String) {
                    mainTV.text = "Error! $message"
                }
            })
        }
        view.findViewById<Button>(R.id.logoutButton).setOnClickListener {
            tokenViewModel.deleteToken()
        }
    }
}