package com.example.composition.domain.entity

data class GameSettings(
    val won: Int,
    val minCountOfRightAnswer: Int,
    val minCountOfPercentAnswer: Int,
    val gameTime: Int,
    val maxSummValue: Int
)