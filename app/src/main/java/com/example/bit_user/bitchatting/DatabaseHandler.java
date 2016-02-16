package com.example.bit_user.bitchatting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.sql.Timestamp;
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

    // Message table name
    private static final String TABLE_CHATMSG = "chatmsg";

    // Message Table Columns names
    private static final String CHATMSG_KEY_CMNO = "cmno";
    private static final String CHATMSG_KEY_CRNO = "crno";
    private static final String CHATMSG_COLUMN_SENDERNO = "senderno";
    private static final String CHATMSG_COLUMN_MSG = "msg";
    private static final String CHATMSG_COLUMN_SENDTIME = "sendtime";

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

        // Create Message Table
        String CREATE_MSG_TABLE = "CREATE TABLE " + TABLE_CHATMSG + "("
                + CHATMSG_KEY_CMNO + " INT PRIMARY KEY AUTOINCREMENT,"
                + CHATMSG_KEY_CRNO + " INT FOREIGN KEY REFERENCES chatroom,"
                + CHATMSG_COLUMN_SENDERNO + " INT FOREIGN KEY REFERENCES member(mno),"
                + CHATMSG_COLUMN_MSG + " TEXT,"
                + CHATMSG_COLUMN_SENDTIME + " DATETIME)";
        db.execSQL(CREATE_MSG_TABLE);
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
            user.put("mNo", cursor.getString(0));
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

    // Add a record into Message table
    public void addMessage(int crno, int senderid, String message) {

        // 매개변수가 올바르지 않을 경우에 대한 예외처리
        try {
            SQLiteDatabase dbInsert = this.getWritableDatabase();
            String INSERT_INTO_CHATMSG_TABLE = "INSERT INTO " + TABLE_CHATMSG
                    + "(crno,senderid,message,timestamp) VALUES (" + Integer.toString(crno)
                    + "," + Integer.toString(senderid) + "," + message
                    + new Timestamp(System.currentTimeMillis()).toString() + ")";
            dbInsert.execSQL(INSERT_INTO_CHATMSG_TABLE);
        } catch(Exception e) {}
    }

    // Remove a record into Message table
    public void removeMessage(int cmno) {
        // 매개변수가 올바르지 않을 경웨 대한 예외처리
        try {
            SQLiteDatabase dbDelete = this.getWritableDatabase();
            String DELETE_FROM_CHATMSG_TABLE = "DELETE FROM " + TABLE_CHATMSG
                    + " WHERE id=cmno";
            dbDelete.execSQL(DELETE_FROM_CHATMSG_TABLE);
        } catch(Exception e) {}
    }

    // Get the Message table
    public HashMap getMessageDetails() {
        HashMap msgInfo = new HashMap();
        String selectQuery = "SELECT * FROM " + TABLE_CHATMSG;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0) {
            msgInfo.put("cmno", cursor.getString(0));
            msgInfo.put("crno", cursor.getString(1));
            msgInfo.put("senderno", cursor.getString(2));
            msgInfo.put("msg", cursor.getString(3));
            msgInfo.put("sendtime", cursor.getString(4));
        }
        cursor.close();
        db.close();
        return msgInfo;
    }
    
}