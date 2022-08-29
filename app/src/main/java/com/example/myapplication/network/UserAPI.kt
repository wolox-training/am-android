package com.example.myapplication.network

import com.example.myapplication.network.data.UserAuth
import com.example.myapplication.network.data.UserInfo
import com.example.myapplication.network.data.UserNews
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Query

val headers = mapOf(
    "Access-Token" to "-25R0g0vRLQdXv1eSomToA",
    "Uid" to "susan.stevens38@example.com",
    "Client" to "dc3gN1S2fo7Hjk9nrC1ybQ"
)
interface UserAPI {
    @POST("/auth/sign_in")
    suspend fun getUserInfo(@Body userAuth: UserAuth): Response<UserInfo>



    @GET("/comments")
    suspend fun getUserNews(
        @Query("page") page: Int,
        @HeaderMap header: Map<String,String> = headers
    ): Response<UserNews>
}
