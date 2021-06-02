package com.gr.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

private val CORRECT_BUZZ_PATTERN = longArrayOf(100, 100, 100, 100, 100, 100)
private val PANIC_BUZZ_PATTERN = longArrayOf(0, 200)
private val GAME_OVER_BUZZ_PATTERN = longArrayOf(0, 2000)
private val NO_BUZZ_PATTERN = longArrayOf(0)

class GameViewModel : ViewModel() {

    enum class BuzzType(val pattern: LongArray) {
        CORRECT(CORRECT_BUZZ_PATTERN),
        GAME_OVER(GAME_OVER_BUZZ_PATTERN),
        COUNTDOWN_PANIC(PANIC_BUZZ_PATTERN),
        NO_BUZZ(NO_BUZZ_PATTERN)
    }

    //    The current word
    //    internal version
    private val _word = MutableLiveData<String>()
    //    external version
    val word: LiveData<String>
        get() = _word

    //    The current score
    //    internal version
    private val _score = MutableLiveData<Int>()
    //    external version
    val score: LiveData<Int>
        get() = _score

    //    The event is finished
    //    internal version
    private val _eventGameFinish = MutableLiveData<Boolean>()
    //    external version
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

    //    The current time
    //    internal version
    private val _currentTime = MutableLiveData<Long>()
    //    external version
    val currentTime: LiveData<Long>
        get() = _currentTime

    val currentTimeString = Transformations.map(_currentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }

    //    The buzz
    //    internal version
    private val _eventBuzz = MutableLiveData<BuzzType>()
    //    external version
    val eventBuzz : LiveData<BuzzType>
        get() = _eventBuzz

    //    The list of words = the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    private lateinit var timer: CountDownTimer

    init {
        resetList()
        nextWord()
        _score.value = 0
        _eventGameFinish.value = false
        _eventBuzz.value = BuzzType.NO_BUZZ

        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
//                TODO implement what should happen each tick of the timer
                _currentTime.value = (millisUntilFinished / ONE_SECOND)

                if (millisUntilFinished / ONE_SECOND <= COUNTDOWN_PANIC_SECONDS) {
                    _eventBuzz.value = BuzzType.COUNTDOWN_PANIC
                }
            }

            override fun onFinish() {
//                TODO implement what should happen when the timer finishes
                _currentTime.value = DONE
                _eventGameFinish.value = true
                _eventBuzz.value = BuzzType.GAME_OVER
            }

        }

        timer.start()

    }

//    callback called before viewmodel is destroyed
    override fun onCleared() {
        super.onCleared()
//    to avoid memory leaks, always cancel a CountDownTimer
        timer.cancel()
    }

    //    Resets the list of words and randomizes the order

    private fun resetList() {
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        wordList.shuffle()
    }

    //    Moves to the next word in the list

    private fun nextWord() {
//        Select and remove a word from the list
        if (wordList.isEmpty()) {
            resetList()
        }
        _word.value = wordList.removeAt(0)
    }

//    Methods for buttons presses

    fun onSkip() {
        _score.value = (_score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = (_score.value)?.plus(1)
        _eventBuzz.value = BuzzType.CORRECT
        nextWord()
    }

    fun onGameFinishComplete() {
        _eventGameFinish.value = false
    }

    fun onBuzzComplete() {
        _eventBuzz.value = BuzzType.NO_BUZZ
    }

    companion object {
//        These represent different important times
//        This is when the game is over
        const val DONE = 0L

//        This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L

        // This is the time when the phone will start buzzing each second
        private const val COUNTDOWN_PANIC_SECONDS = 5L

//        This is the total time of the game
        const val COUNTDOWN_TIME = 10000L
    }

}