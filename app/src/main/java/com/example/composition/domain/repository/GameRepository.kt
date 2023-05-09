package com.example.composition.domain.repository

import com.example.composition.domain.entity.GameSettings
import com.example.composition.domain.entity.Level
import com.example.composition.domain.entity.Question
import com.example.composition.domain.usecases.GenerateQuestionUseCase
import com.example.composition.domain.usecases.GetGameSettingsUseCase

interface GameRepository {

    fun generateQuestion (
        maxValue: Int,
        countOption:Int
    ):Question

    fun getGameSettings(level: Level):GameSettings
}