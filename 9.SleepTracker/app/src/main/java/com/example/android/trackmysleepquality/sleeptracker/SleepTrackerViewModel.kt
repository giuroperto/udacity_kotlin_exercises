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

package com.example.android.trackmysleepquality.sleeptracker

import android.app.Application
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.database.SleepNight
import kotlinx.coroutines.*

/**
 * ViewModel for SleepTrackerFragment.
 */

//this class is the same as view model but it takes the app context as a parameter and makes it
//available as a property

//it needs access to the data in the db through the interface defined in the DAO
// so we pass the an instance of the Dao to the class

//we need the application context so we have access to resources such as strings and styles
//and pass it to the super class as well
class SleepTrackerViewModel(
        val database: SleepDatabaseDao,
        application: Application) : AndroidViewModel(application) {

//            we need a factory to instantiate the viewmodel and provide it with the data source

//            we will be using coroutines with the btn click because it triggers db operations
//            such as creating or updating a sleep night -> do not slow down UI

//            to manage all coroutines -> we need a job
//    this job allows us to cancel all coroutines started by this VM when the VM is no longer used
//    and destroyed, so that we don't have coroutines that dont have anywhere to return to
//    when a vm is destroyed, onCleared is called -> override the method to cancel all coroutines
            private var viewModelJob = Job()

        override fun onCleared() {
            super.onCleared()
            viewModelJob.cancel()
        }

//    determine a scope to the coroutines to run in -> determines which thread the coroutine will
//    run on + needs to know about the job
//    to create a scope, we ask for an instance of the CoroutineScope + pass in the Dispatcher
//    and the Job
//    Dispatchers.Main -> means that coroutines launched at the UI scope will run in the main thread
//    specially for coroutines started by a VM as it will eventually result in an update of the
//    UI after performing some processing
        private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

//    a variable to hold the current night -> live data because we want to observe it + make changes
    private var tonight = MutableLiveData<SleepNight?>()

//    get all the nights in the db when we create the vm -> it is live data and room will manage
//    if anything changes
    private val nights = database.getAllNights()

//    we need the tonight asap so we can work with that -> init block\
    init {
        initializeTonight()
    }

    private fun initializeTonight() {
//        using a coroutine to get tonight from a db so that we are not blocking the UI while
//        waiting for the result
//        specify a scope and in that scope launch the coroutine
//        creates the coroutine without blocking the current thread into context defined by the scope.
        uiScope.launch {

//            unless specified otherwise, the coroutine is scheduled to execute immediately
// get the value for tonight from the db
//            return a sleepNight or null
            tonight.value = getTonightFromDatabase()
        }

    }

//    suspend -> because we want to call it from inside the coroutine and not block
    private suspend fun getTonightFromDatabase() : SleepNight? {

//        to get this data, we have to create another coroutine, this time in the IO context
//        using the IO dispatcher and call the db method to get this value
//    returns the latest night saved in the db
        return withContext(Dispatchers.IO) {
            var night = database.getTonight()

//            if the start time and end time are the same -> continuing an existing night
//            otherwise no night has started and we will return null
            if (night?.endTimeMilli != night?.startTimeMilli) {
                night = null
            }
            night
        }

    }

    fun onStartTracking() {
//        when we click the button we want to get a sleepnight and save it in our db

//        launch a coroutine because everything will be time consuming -> db operation
//        ui scope because we need the result to continue and update the ui
        uiScope.launch {
//            new night that captures the current time as the start time
            val newNight = SleepNight()
//            suspend function for the db operation
            insert(newNight)
//            set tonight to the new night by calling the getTonight
            tonight.value = getTonightFromDatabase()
        }
    }

    private suspend fun insert(night: SleepNight) {
        withContext(Dispatchers.IO) {
            database.insert(night)
        }
    }

//    click handler for the stop button
    fun onStopTracking() {
        uiScope.launch {
//            the return annotation -> in kotlin the return@label syntax is used for specifying which
//            function among several nested ones this statement returns from
//            return from launch not the lambda
            var oldNight = tonight.value ?: return@launch

            oldNight.endTimeMilli = System.currentTimeMillis()

            update(oldNight)
        }
    }

    private suspend fun update(night: SleepNight) {
        withContext(Dispatchers.IO) {
            database.update(night)
        }
    }

    fun onClear() {
        uiScope.launch {
            clear()
            tonight.value = null
        }
    }

    suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

}

//pattern -> launch a coroutine in the UI thread because the result affects the UI
// then we call a suspend function for the long running work, so that we don't block the UI thread
// while waiting for the result
//define the suspend function -> the long running work has nothing to do with the UI -> IO context
// so that we can run it in a thread pool that is optimized and set aside for these operations
//and then we call the db function to do the work