package com.example.gamewebview.data.repository

import com.example.gamewebview.data.api.ApiService
import com.example.gamewebview.data.api.dto.AppsFlyerDataDto
import com.example.gamewebview.domain.GameRepository
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(private val apiService: ApiService): GameRepository {

    /**
     * Отправляю данные на сервер
     */
    override suspend fun sendData(appsFlyerData: AppsFlyerDataDto) {
        apiService.sendDataServer(appsFlyerData)
    }
}