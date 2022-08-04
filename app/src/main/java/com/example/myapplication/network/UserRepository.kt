package com.example.myapplication.network

import com.example.myapplication.BuildConfig
import com.example.myapplication.network.data.UserAuth
import com.example.myapplication.network.data.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class UserRepository {
    private val api = ApiBuilder(BuildConfig.WOLOX_API_URL).create(UserAPI::class.java)
    suspend fun getUserInfo(userAuth: UserAuth): NetworkResponse<Response<UserInfo>> =
        withContext(Dispatchers.IO) {
            NetworkRequestHandler.safeApiCall { api.getUserInfo(userAuth) }
        }
}
