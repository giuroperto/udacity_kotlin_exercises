package com.gr.android.guesstheword.screens.score

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

//adding a constructor parameter for the final score
class ScoreViewModel(finalScore: Int) : ViewModel() {

    private val _finalScoreLiveData = MutableLiveData<Int>()
    val finalScoreLiveData : LiveData<Int>
        get() = _finalScoreLiveData

    private val _eventPlayAgain = MutableLiveData<Boolean>()
    val eventPlayAgain : LiveData<Boolean>
        get() = _eventPlayAgain

    init {
        _eventPlayAgain.value = false
        _finalScoreLiveData.value = finalScore
        Log.i("ScoreVM", "Final score is $finalScore")
    }

    fun eventIsFinished() {
        _eventPlayAgain.value = true
    }

    fun eventFinishedComplete() {
        _eventPlayAgain.value = false
    }
}