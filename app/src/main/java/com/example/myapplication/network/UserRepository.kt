package com.example.myapplication.network

import com.example.myapplication.BuildConfig
import com.example.myapplication.network.data.UserAuth
import com.example.myapplication.network.data.UserInfo
import com.example.myapplication.network.data.UserNews
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class UserRepository {
    private val api = ApiBuilder(BuildConfig.WOLOX_API_URL).create(UserAPI::class.java)
    suspend fun getUserInfo(userAuth: UserAuth): NetworkResponse<Response<UserInfo>> =
        withContext(Dispatchers.IO) {
            NetworkRequestHandler.safeApiCall { api.getUserInfo(userAuth) }
        }

    suspend fun getUserNews(
        accessToken: String,
        userId: String,
        client: String
    ): NetworkResponse<Response<UserNews>> =
        withContext(Dispatchers.IO) {
            NetworkRequestHandler.safeApiCall { api.getUserNews(accessToken, userId, client) }
        }
}
