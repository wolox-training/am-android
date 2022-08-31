package com.example.myapplication.network.data

import com.google.gson.annotations.SerializedName

data class UserNews(
    val page: List<SingleNews>,
    @SerializedName("next_page") val nextPage: Int,
)
