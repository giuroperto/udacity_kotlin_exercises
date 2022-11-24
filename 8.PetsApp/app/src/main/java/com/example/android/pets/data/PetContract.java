package com.example.android.pets.data;

import android.provider.BaseColumns;

public final class PetContract {

//    To prevent someone from accidentally instantiating the contract class,
//    give it an empty constructor

    private PetContract() {}

//    Inner class that defines constant values for the pets db table.
//    Each entry in the table represents a single pet.

    public static abstract class PetEntry implements BaseColumns {

//        name of db table for pets
        public static final String TABLE_NAME = "pets";

//        unique ID number for the pet (only for use in the db table) -> INT
        public static final String _ID = BaseColumns._ID;

//        name of the pet -> TEXT
        public static final String COLUMN_NAME = "name";

//        breed of the pet -> TEXT
        public static final String COLUMN_BREED = "breed";

//        gender of the pet -> INT
//        possible values: GENDER_UNKNOWN, GENDER_MALE, GENDER_FEMALE
        public static final String COLUMN_GENDER = "gender";

//        weight of the pet -> INT
        public static final String COLUMN_WEIGHT = "weight";

//        possible values for the gender of the pet
        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;

    }

}