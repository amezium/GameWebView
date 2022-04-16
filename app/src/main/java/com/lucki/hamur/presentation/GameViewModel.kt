package com.lucki.hamur.presentation

import androidx.lifecycle.ViewModel
import com.lucki.hamur.data.api.dto.Container
import com.lucki.hamur.domain.SendDataUseCase
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
    suspend fun getData(): Container {
        return sendDataUseCase()
    }
}