package com.example.composition.domain.entity

data class GameResult (
    val won: Int,
    val maxCountOfRight: Int,
    val minCountOfRight: Int,
    val gameSettings: GameSettings
        )