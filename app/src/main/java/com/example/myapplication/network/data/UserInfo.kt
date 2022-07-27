package com.example.myapplication.network.data

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("id") val id: Int,
    @SerializedName("email") val email: String,
    @SerializedName("name") val name: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("created_at") val creationDate: String
)
