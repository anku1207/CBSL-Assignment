package com.example.mvvmkotlinexample.retrofit

import com.example.mvvmkotlinexample.model.ServicesSetterGetter
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("search?term=Michael+jackson&media=musicVideo")
    fun getMusicList() : Call<ServicesSetterGetter>

}