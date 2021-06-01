package com.gr.android.guesstheword.screens.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

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

    //    The list of words = the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    init {
        resetList()
        nextWord()
        _score.value = 0
        _eventGameFinish.value = false
    }

//    callback called before viewmodel is destroyed
    override fun onCleared() {
        super.onCleared()
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
            _eventGameFinish.value = true
        } else {
            _word.value = wordList.removeAt(0)
        }
    }

//    Methods for buttons presses

    fun onSkip() {
        _score.value = (score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = (score.value)?.plus(1)
        nextWord()
    }

    fun onGameFinishComplete() {
        _eventGameFinish.value = false
    }

}