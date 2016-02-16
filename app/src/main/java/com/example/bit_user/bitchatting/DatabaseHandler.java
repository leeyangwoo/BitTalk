package com.example.bit_user.bitchatting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

/**
 * Created by bit-user on 2016-02-11.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "bittalk";

    // Login table name
    private static final String TABLE_LOGIN = "member";
    private static final String TABLE_CHATROOM = "mychatroom";

    // Login Table Columns names
    private static final String KEY_MNO = "mno";
    private static final String KEY_MID = "mid";
    private static final String KEY_MPASSWORD = "mpasswd";
    private static final String KEY_MNAME = "mname";
    private static final String KEY_CRNO = "crno";
    private static final String KEY_NUMP = "numparticipant";
    private static final String KEY_CRNAME = "crname";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
                + KEY_MNO + " INT PRIMARY KEY,"
                + KEY_MID + " TEXT,"
                + KEY_MPASSWORD + " TEXT,"
                + KEY_MNAME + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);
        String CREATE_CHATROOM_TABLE = "CREATE TABLE " + TABLE_CHATROOM + "("
                + KEY_CRNO + " INT PRIMARY KEY,"
                + KEY_NUMP + " INT,"
                + KEY_CRNAME + " TEXT" + ")";
        db.execSQL(CREATE_CHATROOM_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN );

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(String mNo, String mId, String mPassword, String mName) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MNO, mNo); // FirstName
        values.put(KEY_MID, mId); // LastName
        values.put(KEY_MPASSWORD, mPassword); // Email
        values.put(KEY_MNAME, mName); // UserName

        // Inserting Row
        db.insert(TABLE_LOGIN, null, values);
        db.close(); // Closing database connection
    }



    /**
     * Getting user data from database
     * */
    public HashMap getUserDetails(){
        HashMap user = new HashMap();
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            user.put("mNo", cursor.getInt(0));
            user.put("mId", cursor.getString(1));
            user.put("mPassword", cursor.getString(2));
            user.put("mName", cursor.getString(3));
        }
        cursor.close();
        db.close();
        // return user
        return user;
    }
    //    --------나중에 쓸일이 있을지도 모르니 나머지 기능들은 주석처리함---------------------------------
//
//    /**
//     * Getting user login status
//     * return true if rows are there in table
//     * */
//    public int getRowCount() {
//        String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//        int rowCount = cursor.getCount();
//        db.close();
//        cursor.close();
//
//        // return row count
//        return rowCount;
//    }
//
    /**
     * Re create database
     * Delete all tables and create them again
     * */
    public void resetTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_LOGIN, null, null);
        db.close();
    }


}