package com.example.myapplication.vm

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.BuildConfig
import com.example.myapplication.network.NetworkResponse
import com.example.myapplication.network.UserRepository
import com.example.myapplication.network.data.UserInfo
import com.example.myapplication.network.data.UserNews
import com.google.gson.Gson
import kotlinx.coroutines.launch

class NewsViewModel(app: Application) : AndroidViewModel(app) {

    private val userRepository = UserRepository()

    private val _newsResponse = MutableLiveData<UserNews>()
    val newsResponse: LiveData<UserNews>
        get() = _newsResponse

    private val sharedPreferences: SharedPreferences =
        app.applicationContext.getSharedPreferences(
            BuildConfig.SHARED_PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )

    fun retrieveSavedUser() {
        val userJson: String? = sharedPreferences.getString(USER_INFO, "")
        val userGson = Gson()
        val selectedUser: UserInfo = userGson.fromJson(userJson, UserInfo::class.java)
        with(selectedUser) {
            getUserNews(email, userId, name)
        }
    }

    private fun getUserNews(
        accessToken: String,
        userId: String,
        client: String,
    ) {
        viewModelScope.launch {
            when (val response = userRepository.getUserNews(accessToken, userId, client)) {
                is NetworkResponse.Success -> {
                    _newsResponse.value = response.response.body()
                }
                else -> {
                }
            }
        }
    }

    companion object {
        private const val USER_INFO: String = "USER_INFO"
    }
}
