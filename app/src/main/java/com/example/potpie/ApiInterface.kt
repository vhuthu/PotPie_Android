package com.example.potpie

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("products")
    fun getData(): Call<List<UsersItem>>
}