package com.lucki.hamur.domain

import javax.inject.Inject

class SendDataUseCase @Inject constructor(private val repository: GameRepository) {
    suspend operator fun invoke() = repository.getData()
}