package com.example.myapplication.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.vm.LoginViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            logInButton.setOnClickListener {
                val emailText = firstNameEdit.text.toString()
                val passwordText = lastNameEdit.text.toString()
                loginViewModel.fieldsValuesValidation(emailText, passwordText)
            }
        }
        loginViewModel.retrieveSavedUser()
        emptyFieldsObserver()
        invalidEmailObserver()
        retrieveSavedEmailObserver()
        retrieveSavedPasswordObserver()
    }

    private fun emptyFieldsObserver() {
        loginViewModel.emptyFieldsError.observe(viewLifecycleOwner) {
            it?.let {
                Toast.makeText(context, getString(R.string.all_fields_required), Toast.LENGTH_SHORT).show()
                loginViewModel.emptyFieldsErrorShown()
            }
        }
    }

    private fun invalidEmailObserver() {
        loginViewModel.invalidEmail.observe(viewLifecycleOwner) {
            it?.let {
                binding.firstNameEdit.error = getString(R.string.invalid_email)
                loginViewModel.invalidEmailErrorShown()
            }
        }
    }

    private fun retrieveSavedEmailObserver() {
        loginViewModel.email.observe(viewLifecycleOwner) {
            it?.let {
                binding.firstNameEdit.setText(it)
            }
        }
    }

    private fun retrieveSavedPasswordObserver() {
        loginViewModel.password.observe(viewLifecycleOwner) {
            it?.let {
                binding.lastNameEdit.setText(it)
            }
        }
    }
}
