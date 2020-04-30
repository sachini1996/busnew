package com.example.buslk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "BusNew.db";
    public static final String TABLE_NAME = "BusNew_table";
    public static final String COL_1 = "BusNo";
    public static final String COL_2 = "BusName";
    public static final String COL_3 = "StartPlace";
    public static final String COL_4 = "StopPlace";
    public static final String COL_5 = "TotalTime";
    public static final String COL_6 = "TotalTicketPrice";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL("create table "+TABLE_NAME+" (BusNo INTEGER PRIMARY KEY AUTOINCREMENT,BusName TEXT,StartPlace TEXT,StopPlace TEXT,TotalTime TEXT,TotalTicketPrice INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(String BusName, String StartPlace, String StopPlace, String TotalTime, String TotalTicketPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,BusName);
        contentValues.put(COL_3,StartPlace);
        contentValues.put(COL_4,StopPlace);
        contentValues.put(COL_5,TotalTime);
        contentValues.put(COL_6,TotalTicketPrice);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }
    public boolean updateData(String BusNo, String BusName, String StartPlace, String StopPlace, String TotalTime, String TotalTicketPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,BusNo);
        contentValues.put(COL_2,BusName);
        contentValues.put(COL_3,StartPlace);
        contentValues.put(COL_4,StopPlace);
        contentValues.put(COL_5,TotalTime);
        contentValues.put(COL_6,TotalTicketPrice);
        db.update(TABLE_NAME, contentValues, "BusNo = ?",new String[] {BusNo} );
        return true;
    }
    public Integer deleteData(String BusNo){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "BusNo = ?",new String[] {BusNo});
    }
}
