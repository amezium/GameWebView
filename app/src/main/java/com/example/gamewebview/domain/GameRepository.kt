package com.example.gamewebview.domain

import com.example.gamewebview.data.api.dto.AppsFlyerDataDto

interface GameRepository {

    /**
     * Отправляю данные на сервер
     */
    suspend fun sendData(appsFlyerData: AppsFlyerDataDto)
}