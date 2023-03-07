package com.example.metris;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

public class DatabaseHelper2 extends SQLiteOpenHelper {
    
    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "baza";
    private static final String COL1 = "data5";


    public DatabaseHelper2(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL1 + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public boolean addData(String data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, data);

        db.execSQL("INSERT INTO baza (data5) VALUES " + "(" + (data) + ")");

        return false;
    }

    /**
     * Returns all the data from database
     *
     * @return
     */
    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public String sprawdzdate(String data) {
        SQLiteDatabase db = this.getWritableDatabase();
        String result = "";
        String query = "SELECT (data5) FROM baza where data5 =" + data
                + " GROUP BY data5";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToLast()) {result = cursor.getString(0);}
        return result;
    }
}




