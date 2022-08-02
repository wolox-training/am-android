package com.example.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSplashBinding
import com.example.myapplication.vm.SplashViewModel

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding

    private lateinit var splashViewModel: SplashViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        splashViewModel = ViewModelProvider(this)[SplashViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        splashViewModel.retrieveSavedUser()
        userIsLoggedObserver()
    }

    private fun userIsLoggedObserver() {
        splashViewModel.userIsLogged.observe(viewLifecycleOwner) {
            if (it == true) {
                this.findNavController().navigate(
                    SplashFragmentDirections.actionSplashFragmentToHomePageFragment()
                )
            } else {
                this.findNavController().navigate(
                    SplashFragmentDirections.actionSplashFragmentToLoginFragment()
                )
            }
        }
    }
}
