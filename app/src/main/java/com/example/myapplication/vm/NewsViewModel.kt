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
import com.example.myapplication.network.data.UserNews
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

    fun retrieveSavedUser(
        page: Int
    ) {
        val userAccessToken: String? = sharedPreferences.getString(ACCESS_TOKEN, "")
        val userUid: String? = sharedPreferences.getString(UID, "")
        val userClient: String? = sharedPreferences.getString(CLIENT, "")
        if (userAccessToken != null || userUid != null || userClient != null) {
            getUserNews(userAccessToken!!, userUid!!, userClient!!, page)
        }
    }

    private fun getUserNews(
        accessToken: String,
        userId: String,
        client: String,
        page: Int
    ) {
        viewModelScope.launch {
            val headers = mapOf(
                "Access-Token" to accessToken,
                "Uid" to userId,
                "Client" to client
            )
            when (val response = userRepository.getUserNews(page, headers)) {
                is NetworkResponse.Success -> {
                    _newsResponse.value = response.response.body()
                }
                else -> {
                }
            }
        }
    }

    companion object {
        private const val UID: String = "UID"
        private const val CLIENT: String = "CLIENT"
        private const val ACCESS_TOKEN: String = "ACCESS_TOKEN"
    }
}
