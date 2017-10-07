package com.example.android.androidregister.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by HP on 07-10-2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String TAG = "DbHelper";
    private static final String DATABASE_NAME = "batches.db";
    private static final int DATABASE_VERSION = 3;

    //creating table for names of all batches
    public static final String TABLE_NAME_BATCH = "batch";
    public static final String COLUMN_BATCH_ID = "_id";
    public static final String COLUMN_BATCH_NAME = "batch";
    public static final String COLUMN_NUMBER_OF_LECTURES = "lectures";

    //creating table for names of students
    public static final String TABLE_NAME_STUDENTS = "students";
    public static final String COLUMN_STUDENT_ID = "_id";
    public static final String COLUMN_STUDENT_NAME = "name";
    public static final String COLUMN_STUDENT_BATCH_NAME = "batch_name";

    //creating table to store the attendance of respective lecture
    public static final String TABLE_NAME_ATTENDANCE = "attendance";
    public static final String COLUMN_BATCH = "batch";
    public static final String COLUMN_LECTURE_NUMBER = "lecture_number";
    public static final String COLUMN_STUDENTS_PRESENT = "present";
    public static final String COLUMN_STUDENT_ABSENT = "absent";

    //creating the sql table for batches
    private static final String SQL_CREATE_TABLE_BATCH = "CREATE TABLE " + TABLE_NAME_BATCH + "("
            + COLUMN_BATCH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_BATCH_NAME + " TEXT NOT NULL, "
            + COLUMN_NUMBER_OF_LECTURES + " REAL NOT NULL);";

    //creating sql table for student
    private static final String SQL_CREATE_TABLE_STUDENT = "CREATE TABLE " + TABLE_NAME_STUDENTS + "("
            + COLUMN_STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_STUDENT_NAME + " TEXT NOT NULL, "
            + COLUMN_STUDENT_BATCH_NAME + " TEXT NOT NULL);";

    //creating sql table for attendance
    private static final String SQL_CREATE_TABLE_ATTENDANCE = "CREATE TABLE " + TABLE_NAME_ATTENDANCE + "("
            + COLUMN_BATCH + " TEXT NOT NULL, "
            + COLUMN_LECTURE_NUMBER + " INTEGER NOT NULL DEFAULT 1, "
            + COLUMN_STUDENTS_PRESENT + " TEXT NOT NULL, "
            + COLUMN_STUDENT_ABSENT + " TEXT NOT NULL);";

    public DbHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_BATCH);
        db.execSQL(SQL_CREATE_TABLE_STUDENT);
        db.execSQL(SQL_CREATE_TABLE_ATTENDANCE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_BATCH);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_STUDENTS);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_ATTENDANCE);
        onCreate(db);
    }
}
