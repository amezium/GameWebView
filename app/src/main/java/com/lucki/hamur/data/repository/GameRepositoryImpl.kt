package com.lucki.hamur.data.repository

import com.lucki.hamur.data.api.ApiService
import com.lucki.hamur.data.api.dto.Container
import com.lucki.hamur.domain.GameRepository
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(private val apiService: ApiService): GameRepository {

    /**
     * Отправляю данные на сервер
     */
    override suspend fun getData(): Container {
        return apiService.getDataServer()
    }
}