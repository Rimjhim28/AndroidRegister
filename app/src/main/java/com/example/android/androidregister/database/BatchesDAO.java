package com.example.android.androidregister.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 07-10-2017.
 */

public class BatchesDAO {
    Context mContext;
    DbHelper mD;
    public BatchesDAO(Context context){
        mContext = context;
        mD = new DbHelper(mContext);
    }

    public long createBatch(String name,int numberOfLectures){
        SQLiteDatabase database = mD.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.COLUMN_BATCH_NAME,name);
        values.put(DbHelper.COLUMN_NUMBER_OF_LECTURES,numberOfLectures);
        long id = database.insert(DbHelper.TABLE_NAME_BATCH,null,values);
        return id;
    }

    public List<String> getAllBatches(){
        List<String> batches = new ArrayList<>();
        List<String> noBatchesFound = new ArrayList<>();
        noBatchesFound.add("NO BATCHES FOUND");
        SQLiteDatabase database = mD.getReadableDatabase();
        Cursor cursor = database.query(DbHelper.TABLE_NAME_BATCH,new String[]{DbHelper.COLUMN_BATCH_NAME},null,null,null,null,null);
        if(cursor.moveToFirst()) {
            cursor.moveToFirst();
            do {
                int nameColumnIndex = cursor.getColumnIndex(DbHelper.COLUMN_BATCH_NAME);
                String batchName = cursor.getString(nameColumnIndex);
                batches.add(batchName);
            } while (cursor.moveToNext());
            return batches;
        }
        else return noBatchesFound;
    }
}
