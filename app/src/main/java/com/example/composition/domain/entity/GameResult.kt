package com.example.composition.domain.entity

data class GameResult (
    val won: Boolean,
    val maxCountOfRightAnswered: Int,
    val minCountOfRightQuestion: Int,
    val gameSettings: GameSettings
        )