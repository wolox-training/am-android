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
        val emailEdit = binding.firstNameEdit
        val passwordEdit = binding.lastNameEdit
        binding.logInButton.setOnClickListener { loginViewModel.fieldsValuesValidation(emailEdit, passwordEdit) }
        loginViewModel.retrieveSavedUser()


        loginViewModel.emptyFieldsError.observe(viewLifecycleOwner) {
            if (it == true) {
                Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show()
                loginViewModel.emptyFieldsErrorShown()
            }
        }

        loginViewModel.invalidEmail.observe(viewLifecycleOwner) {
            if (it == true) {
                emailEdit.error = "Invalid email address"
                loginViewModel.invalidEmailErrorShown()
            }
        }

        loginViewModel.email.observe(viewLifecycleOwner) {
            it?.let {
                emailEdit.setText(it)
            }
        }

        loginViewModel.password.observe(viewLifecycleOwner) {
            it?.let {
                passwordEdit.setText(it)
            }
        }

        return binding.root
    }
}
