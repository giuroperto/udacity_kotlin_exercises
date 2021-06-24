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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.trackmysleepquality.database.SleepDatabaseDao

/**
 * This is pretty much boiler plate code for a ViewModel Factory.
 *
 * Provides the SleepDatabaseDao and context to the ViewModel.
 */

//it takes the same arguments as the viewmodel -> data source DAO + application context
//extends the viewmodel provider factory
class SleepTrackerViewModelFactory(
        private val dataSource: SleepDatabaseDao,
        private val application: Application) : ViewModelProvider.Factory {

//    override create which takes any class type as an argument (to make it dynamically created VM)
//    and returns a viewmodel
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//    check if there is a viewmodel class available, and if there is, return an instance of it
        if (modelClass.isAssignableFrom(SleepTrackerViewModel::class.java)) {
            return SleepTrackerViewModel(dataSource, application) as T
        }
//    otherwise we throw an exception
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

//from the dao, the vm knows how to access the db -> but we haven't created a reference to the db
//we don't need the entire db, just the dao that access the table that we need

//Good practice: use miminum objects to keep db and vm separate, but we need to make sure there
//is a db when the viewmodel is created

//instead of having the vm to create the dependency to the db, the viewmodel factory provides this
//dependency to the viewmodel
//so that the viewmodel becomes easy to test