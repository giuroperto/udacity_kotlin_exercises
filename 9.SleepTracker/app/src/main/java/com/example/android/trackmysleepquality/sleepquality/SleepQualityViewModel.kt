/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.trackmysleepquality.sleepquality

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import kotlinx.coroutines.*


//record the sleep quality and then navigate back to our SleepTrackerFragment
// and the display should update automatically to show us the updated value
//in order to do this, we need a vm to go with the sleep quality and a vm FACTORY
//and we need to update the fragment

//so here we will pass the db from the factory + sleepNightKey that we got from the navigation
class SleepQualityViewModel (
        private val sleepNightKey: Long = 0L,
        val db: SleepDatabaseDao) : ViewModel() {

//            job for our coroutines
        private val viewModelJob = Job()

//            coroutine UI scope
//        private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

//    override fun onCleared() {
//        super.onCleared()
//        viewModelJob.cancel()
//    }

//    after recording the quality we want to navigate back to the SleepTrackerFragment
//    create the event variable with the backing property
    private val _navigateToSleepTracker = MutableLiveData<Boolean?>()

    val navigateToSleepTracker: LiveData<Boolean?>
        get() = _navigateToSleepTracker

//    we also need to create the doneNavigating function
    fun doneNavigating() {
        _navigateToSleepTracker.value = null
    }

//    create the click handler using the same coroutine pattern -> doing all in the same function
    fun onSetSleepQuality(quality: Int) {
//    launch a coroutine in the ui scope
        viewModelScope.launch {
//            switch to io dispatcher
//            withContext(Dispatchers.IO) {
//                get tonight using the key
                val tonight = db.get(sleepNightKey) ?: return@launch
//                sets the sleep quality
                tonight.sleepQuality = quality
//                update the db
                db.update(tonight)
//            }
//            trigger navigation
            _navigateToSleepTracker.value = true
        }
    }
}