package com.example.composition.data

import com.example.composition.domain.entity.GameSettings
import com.example.composition.domain.entity.Level
import com.example.composition.domain.entity.Question
import com.example.composition.domain.repository.GameRepository

object GameRepositoryImpl: GameRepository {
    override fun generateQuestion(maxValue: Int, countOption: Int): Question {
        TODO("Not yet implemented")
    }

    override fun getGameSettings(level: Level): GameSettings {
        return when(level) {
            Level.TEST -> {
                GameSettings(10,2,50,15)
            }
            Level.EASY -> {
                GameSettings(10,6,45,60)
            }
            Level.NORMAL -> {
                GameSettings(10,8,60,60)
            }
            Level.HARD -> {
                GameSettings(10,10,80,60)
            }
        }
    }
}