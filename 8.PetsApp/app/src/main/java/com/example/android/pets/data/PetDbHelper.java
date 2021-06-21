package com.example.android.pets.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.pets.data.PetContract.PetEntry;


//db helper for pets app -> manages db creation and version mgmt
public class PetDbHelper extends SQLiteOpenHelper {

    public final static String LOG_TAG = PetDbHelper.class.getSimpleName();

//    name of the db file
    private final static String DB_NAME = "shelter.db";

//    db version -> if you change the db schema, you must increment the db version
    private final static Integer DB_VERSION = 1;

    public static final String SQL_CREATE_PETS_TABLE = "CREATE TABLE " + PetEntry.TABLE_NAME + " ("
            + PetEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PetEntry.COLUMN_NAME + " TEXT NOT NULL, "
            + PetEntry.COLUMN_BREED + " TEXT, "
            + PetEntry.COLUMN_GENDER + " INTEGER NOT NULL, "
            + PetEntry.COLUMN_WEIGHT + " INTEGER NOT NULL DEFAULT 0);";

//    constructs a new instance of {@link PetDbHelper}
//    @param context of the app
    public PetDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

//        execute the SQL statement
        db.execSQL(SQL_CREATE_PETS_TABLE);
        Log.d(LOG_TAG, "created table");
        Log.d(LOG_TAG, SQL_CREATE_PETS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
