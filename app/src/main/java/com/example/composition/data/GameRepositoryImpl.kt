package com.example.composition.data

import com.example.composition.domain.entity.GameSettings
import com.example.composition.domain.entity.Level
import com.example.composition.domain.entity.Question
import com.example.composition.domain.repository.GameRepository
import java.lang.Integer.min
import java.lang.Math.max
import kotlin.random.Random

object GameRepositoryImpl: GameRepository {

    private const val MIN_SUM_VALUE = 2
    private const val MIN_ANSWER_VALUE = 1
    override fun generateQuestion(maxValue: Int, countOption: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxValue + 1)
        val visibleNumber = Random.nextInt(MIN_ANSWER_VALUE, sum)
        val options = HashSet<Int>()
        val rightAnswer = sum - visibleNumber
        options.add(rightAnswer)
        val from = max(rightAnswer - countOption, MIN_ANSWER_VALUE)
        val to = min(maxValue, rightAnswer + countOption)
        while (options.size < countOption) {
            options.add(Random.nextInt(from, to))
        }
        return Question(sum, options.toList(),visibleNumber )
    }

    override fun getGameSettings(level: Level): GameSettings {
        return when(level){
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