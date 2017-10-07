package com.example.android.androidregister.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.androidregister.model.Student;

import java.util.ArrayList;

/**
 * Created by HP on 07-10-2017.
 */

public class StudentsDAO {

    Context mContext;
    DbHelper mDbHelper;

    public StudentsDAO(Context context){
        mContext = context;
        mDbHelper = new DbHelper(mContext);
    }

    public long createStudent(String studentName, String batchName){
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.COLUMN_STUDENT_NAME,studentName);
        values.put(DbHelper.COLUMN_STUDENT_BATCH_NAME,batchName);
        long id = database.insert(DbHelper.TABLE_NAME_STUDENTS,null,values);
        return id;
    }

    public ArrayList<Student> getallStudents(String batch){
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Student student;
        ArrayList<Student> allBatchStudents = new ArrayList<>();
        Cursor cursor = database.query(DbHelper.TABLE_NAME_STUDENTS,
                new String[]{DbHelper.COLUMN_STUDENT_ID,DbHelper.COLUMN_STUDENT_NAME,DbHelper.COLUMN_STUDENT_BATCH_NAME},
                DbHelper.COLUMN_STUDENT_BATCH_NAME + " = ?",
                new String[]{batch},null,null,null);
        cursor.moveToFirst();
        do{
            int indexStudentId = cursor.getColumnIndex(DbHelper.COLUMN_STUDENT_ID);
            int indexStudentName = cursor.getColumnIndex(DbHelper.COLUMN_STUDENT_NAME);
            int indexBatchName = cursor.getColumnIndex(DbHelper.COLUMN_STUDENT_BATCH_NAME);
            int studentId = cursor.getInt(indexStudentId);
            String studentName = cursor.getString(indexStudentName);
            String batchName = cursor.getString(indexBatchName);
            student = new Student(studentId,studentName,batchName,null);
            allBatchStudents.add(student);
        }while (cursor.moveToNext());
        return allBatchStudents;
    }
}

