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

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// only one table -> supply the SleepNight class
// if more than one table, add them all to the list []
// version starts at one, so whenever you change the schema you have to update the version
// otherwise your app won't work anymore
// export schema is true by default and saves the schema of the db to a folder
// this provides you with a history of your db and can be very helpful for complex db that change often
@Database(entities = [SleepNight::class], version = 1, exportSchema = false)
abstract class SleepDatabase : RoomDatabase() {

//    tell it about the dao associated with our entity so it can interact with the db
//    declare an abstract value of the type of our DAO
//    it is possible to have multiple tables and Daos in an app
    abstract val sleepDatabaseDao: SleepDatabaseDao

//    companion object allows clients to access the methods for creating or getting a DB w/o
//    instantiating the class

//    as the point of this class is to provide us with a db, there won't be the need to instatiate it
    companion object {

//        declare a private nullable variable for the db and initialize it to null
//    it will keep the reference to the db once we have one
//    will help us avoid opening connections to the db, which is expensive
//    this annotation help us make sure the value of the variable is always up to date and the same
//    to all execution threads -> it will never be cached, and all writes and reads will be done
//    from the main memory, it means that changes made by one thread to INSTANCE are visible to all
//    other threads immediately. Won't happen that two threads update the same variable at the same
//    time.
        @Volatile
        private var INSTANCE: SleepDatabase? = null

//    will return a reference to the Sleep DB
//    we are going to use a DB builder and it is going to require a context
        fun getInstance(context: Context) : SleepDatabase {
//            synchronized block
//            multiple threads can potentially ask for a db instance at the same time, leaving us
//            with two instead of one -> wrapping with synchronized means that only one thread of
//    execution at a time can enter this block of code, making sure that the db only gets initialized
//    once + we need to pass ourselves into the synchronize so that we have access to the context.
            synchronized(this) {
//                copy the current value of the instance to a local variable -> smart cast
//                make sure we always return a SleepDB and a smart cast is only available to local
//                variables, not class variables. Now we can return the instance.
                var instance = INSTANCE

//                check if there is already a db
                if (instance == null) {
//                    create the db using room dbBuilder -> invoking it passing the context + telling
//    which db to build, so we pass the reference of the sleepDB class + giving the db a name
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            SleepDatabase::class.java,
                            "sleep_history_db"
                    )
//                    next step about migration
//                    normally we would have to provide a migration object with a migration strategy
//                    when we create the db
//                    migration = when we change the db schema by changing the number or type of
//                    columns, we need a way to convert the existing tables and data into the new schema.
//                    Migration object is an object that tells how you take all the rows with your
//                    old schema and convert them to rows in the new schema -> so data that switch
//                    from versions will not be lost, but instead they will be adapted
//                    for this lesson however, instead of migrating, just swipe and rebuild the db
                            .fallbackToDestructiveMigration()
//                    build the db
                            .build()
//                    and assing instance to the newly created db
                    INSTANCE = instance
                }

                return instance

            }

        }
    }

}
