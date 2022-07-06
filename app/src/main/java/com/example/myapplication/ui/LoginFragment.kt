package com.example.myapplication.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.vm.LoginViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.lifecycleOwner = this
        binding.loginViewModel = loginViewModel
        val savedEmail = loginViewModel.sharedPreferences.getString("USERNAME", null)
        val savedPassword = loginViewModel.sharedPreferences.getString("PASSWORD", null)
        loginViewModel.setInformation(savedEmail, savedPassword)

        loginViewModel.toast.observe(viewLifecycleOwner) {
            if (it == true) {
                Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show()
                loginViewModel.toast.value = false
            }
        }

        loginViewModel.error.observe(viewLifecycleOwner) {
            if (it == true) {
                binding.firstNameEdit.error = "Invalid email address"
                loginViewModel.save.value = false
            }
        }

        return binding.root
    }
}
