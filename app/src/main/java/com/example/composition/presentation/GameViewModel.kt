package com.example.composition.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composition.R
import com.example.composition.data.GameRepositoryImpl
import com.example.composition.domain.entity.GameResult
import com.example.composition.domain.entity.GameSettings
import com.example.composition.domain.entity.Level
import com.example.composition.domain.entity.Question
import com.example.composition.domain.usecases.GenerateQuestionUseCase
import com.example.composition.domain.usecases.GetGameSettingsUseCase

class GameViewModel(application: Application): AndroidViewModel(application) {

    private lateinit var gameSettings: GameSettings
    private lateinit var level: Level


    private val repository = GameRepositoryImpl
    private val genQuestion = GenerateQuestionUseCase(repository)
    private val genGameSettings = GetGameSettingsUseCase(repository)

    private val context = application

    private var timer: CountDownTimer? = null


    private val _myQuestion = MutableLiveData<Question>()
    val myQuestion: LiveData<Question>
        get() = _myQuestion

    private val _formatTime =  MutableLiveData<String>()
    val formatTime: LiveData<String>
        get() = _formatTime


    private var countOfRightAnsw = 0
    private var countOfRightQuestion = 0

    private val _percentOfRightAnwers = MutableLiveData<Int>()
    val percentOfRightAnwers: LiveData<Int>
        get() = _percentOfRightAnwers

    private val _progress =  MutableLiveData<String>()
    val progress: LiveData<String>
        get() = _progress

    private val _enoughOfRightAnswered = MutableLiveData<Boolean>()
    val enoughOfRightAnswered: LiveData<Boolean>
        get() = _enoughOfRightAnswered

    private val _enoughPercentOfRightAnswered = MutableLiveData<Boolean>()
    val enoughPercentOfRightAnswered: LiveData<Boolean>
        get() = _enoughPercentOfRightAnswered


    private val _enoughSecondaryProgress = MutableLiveData<Int>()
    val enoughSecondaryProgress: LiveData<Int>
        get() = _enoughSecondaryProgress

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    fun startGame(level: Level) {
        getGameSettings(level)
        timer()
        generateQuestion()

    }



    fun chooseAnswer (number: Int) {
        checkNumber(number)
        updateProgress()
        generateQuestion()
    }


    private fun updateProgress(){
        val percent = calculatePercentOfRightAnw()
        _percentOfRightAnwers.value = percent
        _progress.value = String.format(
            context.resources.getString(R.string.progress_answers),
            countOfRightAnsw,
            gameSettings.minCountOfRightAnswer
        )
        _enoughOfRightAnswered.value = countOfRightAnsw >= gameSettings.minCountOfRightAnswer
        _enoughPercentOfRightAnswered.value = percent >= gameSettings.minCountOfPercentAnswer
    }

    private fun calculatePercentOfRightAnw(): Int {
        return ((countOfRightAnsw / countOfRightQuestion.toDouble()) * 100).toInt()
    }

    private fun checkNumber(number: Int) {
        val rightAns = myQuestion.value?.rightAnswer
        if (number == rightAns) {
            countOfRightAnsw++
        }
        countOfRightQuestion++
    }

    private fun getGameSettings(level: Level) {
        this.level = level
        this.gameSettings = genGameSettings(level)
        _enoughSecondaryProgress.value = gameSettings.minCountOfRightAnswer
    }

    private fun timer() {
        timer = object : CountDownTimer(gameSettings.gameTime * TIME_MIL_SECONDS,
            TIME_MIL_SECONDS) {
            override fun onTick(millisUntilFinished: Long) {
                _formatTime.value = convertTime(millisUntilFinished)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer?.start()
    }

    private fun generateQuestion() {
        genQuestion(gameSettings.maxSummValue)
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    private fun convertTime (millisUntilFinished: Long): String {
        val seconds = millisUntilFinished / TIME_MIL_SECONDS
        val minutes = seconds / SECONDS_IN_MINUTES
        val visibleNumber = seconds - (minutes * SECONDS_IN_MINUTES)
        return String.format("%02d:%02d",minutes,visibleNumber)
    }

    private fun finishGame() {
        _gameResult.value = GameResult(
            enoughOfRightAnswered.value == true && enoughPercentOfRightAnswered.value == true,
            countOfRightAnsw,
            countOfRightQuestion,
            gameSettings
        )
    }



    companion object{
        private const val SECONDS_IN_MINUTES = 60
        private const val TIME_MIL_SECONDS = 1000L
    }
}