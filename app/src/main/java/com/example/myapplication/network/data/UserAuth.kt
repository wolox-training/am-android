package com.example.myapplication.network.data

import com.google.gson.annotations.SerializedName

data class UserAuth(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)
