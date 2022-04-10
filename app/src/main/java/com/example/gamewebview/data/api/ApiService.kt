package com.example.gamewebview.data.api

import com.example.gamewebview.data.api.dto.AppsFlyerDataDto
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {

    @POST("Dw8RJcYh")
    suspend fun sendDataServer(@Body appsFlyerData: AppsFlyerDataDto)
}