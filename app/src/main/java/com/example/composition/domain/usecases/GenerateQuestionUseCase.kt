package com.example.composition.domain.usecases

import com.example.composition.domain.entity.Question
import com.example.composition.domain.repository.GameRepository

class GenerateQuestionUseCase(
    private val repository: GameRepository
) {
    operator fun invoke(number: Int) : Question{
        return repository.generateQuestion(number, COUNT_OPTIONS)
    }

    private companion object {
        const val COUNT_OPTIONS = 6
    }
}