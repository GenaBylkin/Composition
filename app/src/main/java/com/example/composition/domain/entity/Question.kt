package com.example.composition.domain.entity

data class Question (
    val summa: Int,
    val countAnswer: List<Int>,
    val leftNumber: Int
        ) {
    val rightAnswer: Int
        get() = summa - leftNumber
}