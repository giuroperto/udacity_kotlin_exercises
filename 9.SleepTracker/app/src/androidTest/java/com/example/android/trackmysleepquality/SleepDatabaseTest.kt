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

package com.example.android.trackmysleepquality

import android.util.Log
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.android.trackmysleepquality.database.SleepDatabase
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.database.SleepNight
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */

// make sure we have a database + we can read and write in the db + exercising all the functions
// and queries defined in DAO.
// contains unit tests -> Android instrumentation (framework) -> need to run them on a device
// can be virtual or physical

// this is a reusable piece of code

// identifies the runner we are using -> program that sets up and executes the tests
@RunWith(AndroidJUnit4::class)
class SleepDatabaseTest {

    private lateinit var sleepDao: SleepDatabaseDao
    private lateinit var db: SleepDatabase

//    during setup this function with BEFORE annotation is executed and we create a in memory
//    (the db is not actually saved on the file system and will be deleted once the tests have been
//    run) sleep db with the sleepDBDao
    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, SleepDatabase::class.java)
                // Allowing main thread queries, just for testing.
//                test specific method -> by default you get an error if you try to run queries
//                on the main thread. But this allows us to run our tests in the main thread which
//                we should only do during testing.
                .allowMainThreadQueries()
                .build()
        sleepDao = db.sleepDatabaseDao
    }

//    when testing is done, the function annotated with after executes and closes the db
    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

//    here we create, insert and retrieve a sleep night + assert they are the same
    @Test
//    if anything goes wrong we throw an exception
    @Throws(Exception::class)
    fun insertAndGetNight() = runBlocking {
        val night = SleepNight()
        sleepDao.insert(night)
        var tonight = sleepDao.getTonight()
        Log.d("GIULIA", tonight.toString())
        Log.d("GIULIA", tonight?.sleepQuality.toString())
        Log.d("GIULIA", "${tonight?.sleepQuality == -1}")
        tonight?.sleepQuality = 1
        sleepDao.update(tonight as SleepNight)
        Log.d("GIULIA", tonight?.sleepQuality.toString())

        assertEquals(tonight?.sleepQuality, -1)
    }
}

