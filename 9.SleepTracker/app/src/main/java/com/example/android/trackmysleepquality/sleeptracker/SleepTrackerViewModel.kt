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
import com.example.android.trackmysleepquality.database.SleepDatabaseDao

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



}

