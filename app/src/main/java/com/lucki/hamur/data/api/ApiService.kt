package com.lucki.hamur.data.api

import com.lucki.hamur.data.api.dto.Container
import retrofit2.http.GET


interface ApiService {

    @GET("plitka.json")
    suspend fun getDataServer(): Container
}