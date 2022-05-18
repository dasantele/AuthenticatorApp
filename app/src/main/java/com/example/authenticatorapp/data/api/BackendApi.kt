package com.example.authenticatorapp.data.api

import com.example.authenticatorapp.domain.model.LoginResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface BackendApi {


    @POST("login")
    @FormUrlEncoded
    suspend fun login(@Field("name") name:String,@Field("password") password:String): LoginResponse

}