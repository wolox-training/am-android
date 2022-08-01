package com.example.myapplication.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.network.data.UserAuth
import com.example.myapplication.vm.LoginViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            logInButton.setOnClickListener {
                val emailText = firstNameEdit.text.toString()
                val passwordText = lastNameEdit.text.toString()
                loginViewModel.fieldsValuesValidation(emailText, passwordText)
            }
            signUpButton.setOnClickListener {
                goToSignUp()
            }
            termsAndConditions.setOnClickListener {
                goToTermsAndConditions()
            }
        }
        emptyFieldsObserver()
        validEmailObserver()
        userResponseObserver()
    }

    private fun emptyFieldsObserver() {
        loginViewModel.emptyFieldsError.observe(viewLifecycleOwner) {
            it?.let {
                Toast.makeText(context, getString(R.string.all_fields_required), Toast.LENGTH_SHORT)
                    .show()
                loginViewModel.emptyFieldsErrorShown()
            }
        }
    }

    private fun validEmailObserver() {
        loginViewModel.validEmail.observe(viewLifecycleOwner) {
            if (it == true) {
                with(binding) {
                    val emailText = firstNameEdit.text.toString()
                    val passwordText = lastNameEdit.text.toString()
                    loginViewModel.getUserInfo(UserAuth(emailText, passwordText))
                }
            } else {
                binding.firstNameEdit.error = getString(R.string.invalid_email)
            }
        }
    }

    private fun userResponseObserver() {
        loginViewModel.userResponseIsSuccessful.observe(viewLifecycleOwner) {
            if (it == true) {
                Toast.makeText(context, SUCCESS, Toast.LENGTH_SHORT)
                    .show()
                this.findNavController().navigate(
                    LoginFragmentDirections.actionLoginFragmentToHomePageFragment()
                )
            } else {
                Toast.makeText(context, FAILURE, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun goToSignUp() {
        this.findNavController().navigate(
            LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
        )
    }

    private fun goToTermsAndConditions() {
        val browserIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse(URL_WOLOX))
        startActivity(browserIntent)
    }

    companion object {
        private const val URL_WOLOX: String = "https://www.wolox.com.ar/"
        private const val SUCCESS: String = "Success"
        private const val FAILURE: String = "Failure"
    }
}
