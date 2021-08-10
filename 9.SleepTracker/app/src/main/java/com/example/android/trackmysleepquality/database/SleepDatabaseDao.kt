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

import androidx.lifecycle.LiveData
import androidx.room.*

//all daos need to be annotated with the dao keyword
@Dao
interface SleepDatabaseDao {
//    get data into our db
//    add an annotated insert method for inserting a single SleepNight
//    + function definition for an insert function that we can call from our code
    @Insert
// specify what we are going to insert as a parameter -> instance of our entity class SleepNight
// during compilation, room will generate code to turn this parsed in Kotlin object into a row of
// values for the db.
// when we call this function, room creates the row from the entity object and inserts it into the db.
    suspend fun insert(night: SleepNight)

//    when we update it it could be that in this new object only one value is different
//    or all of them except for the key
    @Update
    suspend fun update(night: SleepNight)

//    for the other operations -> SQL queries because there are no convenience annotations that do
//    the work we want done.
//    read operation -> annotated and implemented with the query annotation

//    get a specific night based on its key -> return the corresponding table row as an instance
//    of SleepNight.
//    add a SQL query as a string parameter to query
//    condition -> where the nightId (column in the table) matches the supplied key
    //    (:key is how we reference a parameter)
//    it will return a sleepNight or null if the key doesnt exist
    @Query(value = "SELECT * from daily_sleep_quality_table WHERE nightId = :key")
    suspend fun get(key: Long): SleepNight?

//    to delete all rows without deleting the table
//    Delete annotation -> delete a specific entity and do the work of choosing in our code
//    it is not very efficient
//    @Delete
//    fun delete(night: SleepNight)

//    Delete annotation -> supply a list of nights and the function would return the number of rows
//    actually deleted -> drawback is the need to fetch or know whats in the table
//    this is great for deleting specific entries but not efficient for clearing
//    @Delete
//    fun deleteAllNights(nights: List<SleepNight>): Int

//    since we don't care whats in the table -> delete everything
//    we can do this by using a query that deletes everything in the table.
//    uses delete from without a where constraint -> every row is deleted
//    and associates it with the clear function
    @Query("DELETE FROM daily_sleep_quality_table")
    suspend fun clear()

//    return all the rows in a table so that we can then display a full set of data
//    instead of one entity we want to return a list of sorted entities
    @Query(value = "SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC")
// live data -> amazing features of room -> we can get back live data
// room makes sure that this live data is updated whenever the db is updated
// what means that we only need to get a list of the all nights once -> attach an observer to it
// and then if the data changes, the UI will update itself to show the changed data w/o having
// to fetch the data again
    fun getAllNights(): LiveData<List<SleepNight>>

// returns the most recent night by looking at AllNights, ordering them with order, and returning
//    only one indicated by limit one -> because we order by id, it will be the highest which is
//    also the latest night.
//    the return type sleepNight is nullable because in the beginning and after we clear the
//    contents there's no tonight
    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC LIMIT 1")
    suspend fun getTonight(): SleepNight?

    /**
     * Selects and returns the night with given nightId.
     */
    @Query("SELECT * from daily_sleep_quality_table WHERE nightId = :key")
    fun getNightWithId(key: Long): LiveData<SleepNight>

}
