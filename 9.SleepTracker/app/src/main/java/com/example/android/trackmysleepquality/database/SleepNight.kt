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

package com.example.android.trackmysleepquality.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// to make this class as a class that room can use to create a table -> annotate it with the entity annotation
// to give the table a different name from the default of the class name -> add as parameter to the annotation
// set the table name -> optional -> best practice to identify with a name with the word table
@Entity(tableName = "daily_sleep_quality_table")
data class SleepNight(
//        the id has to be a long because we are going to autoGenerate the key + initialization
//        tell room which is the primary key -> MANDATORY
//        to tell room to autogenerate the key -> set it to true
//        room will generate an unique key for every instance of this class which parallels every row in the table
        @PrimaryKey(autoGenerate = true)
        var nightId: Long = 0L,

//        annotate all other columns with column info and give them a custom name

//        start time in milliseconds -> comes from the system time
        @ColumnInfo(name = "start_time_milli")
        val startTimeMilli: Long = System.currentTimeMillis(),

//        end time -> initialize to be the same as the startTime
//        it tells us if the stop button has been pressed to record an endTime or not
        @ColumnInfo(name = "end_time_milli")
        var endTimeMilli: Long = startTimeMilli,

//        sleep quality rating -> -1 is no sleepQuality has been recorded yet
        @ColumnInfo(name = "quality_rating")
        var sleepQuality: Int = -1
)
