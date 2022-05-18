package com.example.authenticatorapp.data.repository

import com.example.authenticatorapp.data.RetrofitBuilder
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class BackendRepository {

    fun requestLogin(name:String, password: String){
        val retrofitBuilder = RetrofitBuilder()
        val API = retrofitBuilder.createBackendApi()
        MainScope().launch {
            val response = API.login(name, password)
            println(response)
        }


    }

}