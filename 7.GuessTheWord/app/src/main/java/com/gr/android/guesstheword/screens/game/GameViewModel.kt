package com.gr.android.guesstheword.screens.game

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    init {
        Log.i("GVM","GameViewModel Created!")
    }

//    callback called before viewmodel is destroyed
    override fun onCleared() {
        super.onCleared()

        Log.i("GVM", "GameViewModel destroyed!")
    }

}