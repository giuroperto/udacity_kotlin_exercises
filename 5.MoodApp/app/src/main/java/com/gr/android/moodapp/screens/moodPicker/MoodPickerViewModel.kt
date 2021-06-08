package com.gr.android.moodapp.screens.moodPicker

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gr.android.moodapp.R

class MoodPickerViewModel : ViewModel() {

    private val _mood = MutableLiveData<String>()
    val mood: LiveData<String>
        get() = _mood

    private val _allMoods = MutableLiveData<IntArray>()
    val allMoods : LiveData<IntArray>
        get() = _allMoods

    init {
        _allMoods.value = intArrayOf(0, 0, 0, 0)
    }

    fun countClick(selectedMood: String) {

        _mood.value = selectedMood

        when (selectedMood) {
            "Angry" -> {
                _allMoods.value?.get(0)?.plus(1)
                Log.i("ANGCOUNT", _allMoods.value.toString())
            }
            "Hungry" -> {
                _allMoods.value?.get(3)?.plus(1)
                Log.i("HUNCOUNT", _allMoods.value.toString())
            }
            "Relaxed" -> {
                _allMoods.value?.get(2)?.plus(1)
                _allMoods.value = intArrayOf(0, 0, 1, 0)
                Log.i("RELCOUNT", _allMoods.value?.get(2).toString())
            }
            "Smug" -> {
                _allMoods.value?.get(1)?.plus(1)
                Log.i("SMUCOUNT", _allMoods.value.toString())
            }
        }
    }

}