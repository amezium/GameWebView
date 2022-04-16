package com.lucki.hamur.domain

import com.lucki.hamur.data.api.dto.Container


interface GameRepository {

    /**
     * Отправляю данные на сервер
     */
    suspend fun getData(): Container
}