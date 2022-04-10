package com.example.gamewebview.domain

import com.example.gamewebview.data.api.dto.AppsFlyerDataDto
import javax.inject.Inject

class SendDataUseCase @Inject constructor(private val repository: GameRepository) {
    suspend operator fun invoke(appsFlyerData: AppsFlyerDataDto) = repository.sendData(appsFlyerData)
}