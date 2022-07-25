package com.example.myapplication.vm

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.network.Api
import com.example.myapplication.network.UserInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(app: Application) : AndroidViewModel(app) {

    private val _email = MutableLiveData<String?>()
    val email: LiveData<String?>
        get() = _email

    private val _password = MutableLiveData<String?>()
    val password: LiveData<String?>
        get() = _password

    private val _emptyFieldsError = MutableLiveData<Boolean>()
    val emptyFieldsError: LiveData<Boolean>
        get() = _emptyFieldsError

    private val _validEmail = MutableLiveData<Boolean>()
    val validEmail: LiveData<Boolean>
        get() = _validEmail

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    private val sharedPreferences: SharedPreferences =
        app.applicationContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun fieldsValuesValidation(emailValue: String, passwordValue: String) {
        if (emailValue.isEmpty() || passwordValue.isEmpty()) {
            _emptyFieldsError.value = true
        } else {
            emailValidation(emailValue, passwordValue)
        }
    }

    private fun emailValidation(emailValue: String, passwordValue: String) {
        val emailPattern = EMAIL_REGEX
        val emailComparable = emailValue.trim()
        if (emailComparable.matches(emailPattern.toRegex())) {
            val editor = sharedPreferences.edit()
            editor.also {
                it.putString(USERNAME, emailValue)
                it.putString(PASSWORD, passwordValue)
                it.commit()
            }
            getUserInfo()
            _validEmail.value = true
        } else {
            _validEmail.value = false
        }
    }

    fun retrieveSavedUser() {
        val savedEmail = sharedPreferences.getString(USERNAME, null)
        val savedPassword = sharedPreferences.getString(PASSWORD, null)
        if (savedEmail != null && savedPassword != null) {
            _email.value = savedEmail
            _password.value = savedPassword
        }
    }

    fun emptyFieldsErrorShown() {
        _emptyFieldsError.value = null
    }

    fun resetValidEmailValue() {
        _validEmail.value = null
    }

     private fun getUserInfo() {
        Api.retrofitService.getProperties().enqueue(
            object: Callback<List<UserInfo>> {
                override fun onResponse(call: Call<List<UserInfo>>, response: Response<List<UserInfo>>) {
                    _response.value = "Success: ${response.body()?.size} users retrieved"

                }

                override fun onFailure(call: Call<List<UserInfo>>, t: Throwable) {
                    _response.value = "Failure: " + t.message
                }
            })
    }

    companion object {
        private const val USERNAME: String = "USERNAME"
        private const val PASSWORD: String = "PASSWORD"
        private const val SHARED_PREFERENCES_NAME: String = "PREFERENCE_NAME"
        private const val EMAIL_REGEX: String = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    }
}
