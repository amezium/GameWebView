package com.example.gamewebview.presentation

import androidx.lifecycle.ViewModel
import com.example.gamewebview.data.api.ApiFactory
import com.example.gamewebview.data.api.dto.AppsFlyerDataDto
import com.example.gamewebview.data.repository.GameRepositoryImpl
import com.example.gamewebview.domain.SendDataUseCase
import javax.inject.Inject

class GameViewModel @Inject constructor(private val sendDataUseCase: SendDataUseCase): ViewModel() {

    //значения для фрагмента с рекордом и сложностью
    var firstPlace = 0
    var secondPlace = 0
    var thirdPlace= 0
    var hardLevel = false

    /**
     * Отправляю данные на сервер(нужно их передать в конструктор)
     */
    suspend fun sendData(appsFlyerData: AppsFlyerDataDto){
        sendDataUseCase(appsFlyerData)
    }
}