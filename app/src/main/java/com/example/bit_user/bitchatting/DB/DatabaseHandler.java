package com.example.bit_user.bitchatting.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.bit_user.bitchatting.DTO.ChatMsg;
import com.example.bit_user.bitchatting.DTO.ChatRoom;
import com.example.bit_user.bitchatting.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by bit-user on 2016-02-11.
 */
public class DatabaseHandler extends SQLiteOpenHelper {



    public DatabaseHandler(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_LOGIN_TABLE = "CREATE TABLE " + Constants.TABLE_LOGIN + "("
                + Constants.KEY_MNO + " INTEGER PRIMARY KEY,"
                + Constants.KEY_MID + " TEXT,"
                + Constants.KEY_MPASSWORD + " TEXT,"
                + Constants.KEY_MNAME + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);
        String CREATE_CHATROOM_TABLE = "CREATE TABLE " + Constants.TABLE_CHATROOM + "("
                + Constants.KEY_CRNO + " INTEGER PRIMARY KEY,"
                + Constants.KEY_NUMP + " INTEGER,"
                + Constants.KEY_CRNAME + " TEXT" + ")";
        db.execSQL(CREATE_CHATROOM_TABLE);

        // Create Message Table
        String CREATE_MSG_TABLE = "CREATE TABLE " + Constants.TABLE_CHATMSG + "("
                + Constants.CHATMSG_KEY_CMNO + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Constants.CHATMSG_KEY_CRNO + " INTEGER NOT NULL,"
                + Constants.CHATMSG_COLUMN_SENDERNO + " INT NOT NULL,"
                + Constants.CHATMSG_COLUMN_MSG + " TEXT NOT NULL,"
                + Constants.CHATMSG_COLUMN_SENDTIME + " TIMESTAMP NOT NULL,"
                + "FOREIGN KEY ("+Constants.CHATMSG_KEY_CRNO+") REFERENCES "
                +Constants.TABLE_CHATROOM+"("+Constants.KEY_CRNO+"));";
        db.execSQL(CREATE_MSG_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_LOGIN );
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_CHATROOM );
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_CHATMSG );
        // Create tables again
        onCreate(db);
    }

    /**
     * Insert
     * */
    public void addUser(int mNo, String mId, String mPassword, String mName) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_MNO, mNo); // FirstName
        values.put(Constants.KEY_MID, mId); // LastName
        values.put(Constants.KEY_MPASSWORD, mPassword); // Email
        values.put(Constants.KEY_MNAME, mName); // UserName

        // Inserting Row
        db.insert(Constants.TABLE_LOGIN, null, values);
        db.close(); // Closing database connection
    }

    public void addChatroom(int crno, int nump, String crName){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_CRNO, crno);
        values.put(Constants.KEY_NUMP, nump);
        values.put(Constants.KEY_CRNAME, crName);

        db.insert(Constants.TABLE_CHATROOM, null, values);
        db.close();
    }

    // Add a record into Message table
    public void addMessage(int crno, int senderid, String message) {

        SQLiteDatabase dbInsert = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.CHATMSG_KEY_CRNO, crno);
        values.put(Constants.CHATMSG_COLUMN_SENDERNO, senderid);
        values.put(Constants.CHATMSG_COLUMN_MSG, message);
        values.put(Constants.CHATMSG_COLUMN_SENDTIME, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA).format(new Date()));

        dbInsert.insert(Constants.TABLE_CHATMSG, null, values);
        dbInsert.close();
    }

    public void updateChatroom(int crno, String crName){
        SQLiteDatabase db = getWritableDatabase();
    }

    /**
     * Getting data from database
     * */
    public HashMap getUserDetails(){
        HashMap user = new HashMap();
        String selectQuery = "SELECT  * FROM " + Constants.TABLE_LOGIN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            user.put("mno", cursor.getInt(0));
            user.put("mid", cursor.getString(1));
            user.put("mpassword", cursor.getString(2));
            user.put("mname", cursor.getString(3));
            user.put("mtoken", cursor.getString(4));
        }
        cursor.close();
        db.close();
        // return user
        return user;
    }

    public List<ChatRoom> getChatroomList(){
        List<ChatRoom> roomList = new ArrayList<>();
        String selectQuery = "SELECT DISTINCT r."+Constants.KEY_CRNO+", "+Constants.KEY_NUMP+", "+Constants.KEY_CRNAME+" FROM "
                + Constants.TABLE_CHATROOM + " AS r INNER JOIN "+Constants.TABLE_CHATMSG+" AS m ON r."+Constants.KEY_CRNO+"=m."+Constants.KEY_CRNO
                +" ORDER BY "+Constants.CHATMSG_COLUMN_SENDTIME+" DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        //cursor.moveToFirst();
        while(cursor.moveToNext()){
            ChatRoom room = new ChatRoom();
            room.setCrno(cursor.getInt(0));
            room.setNump(cursor.getInt(1));
            room.setCrName(cursor.getString(2));
            roomList.add(room);
        }
        cursor.close();
        db.close();
        return roomList;
    }
    public int existChatroom(int crno){
        int ExistChatroom = 0;
        ChatRoom room = new ChatRoom();
        String selectQuery = "SELECT * FROM " + Constants.TABLE_CHATROOM + " WHERE "+Constants.KEY_CRNO+"="+crno;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            room.setCrno(cursor.getInt(0));
            room.setNump(cursor.getInt(1));
            room.setCrName(cursor.getString(2));
            ExistChatroom = 1;
        }
        cursor.close();
        db.close();

        return ExistChatroom;
    }

    // Get the Message table
    public List<ChatMsg> getMessageList(int crno) {
        List<ChatMsg> msgList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + Constants.TABLE_CHATMSG + " WHERE "+Constants.CHATMSG_KEY_CRNO+"="+crno;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        while(cursor.moveToNext()) {
            ChatMsg msg = new ChatMsg();
            msg.setCmno(cursor.getInt(0));
            msg.setCrno(cursor.getInt(1));
            msg.setSenderNo(cursor.getInt(2));
            msg.setMessage(cursor.getString(3));
            msg.setSendTime(cursor.getString(4));
            msgList.add(msg);
        }
        cursor.close();
        db.close();
        return msgList;
    }

    public String getLastMsg(int crno){
        String lastMsg = "";
        String selectQuery ="SELECT " + Constants.CHATMSG_COLUMN_MSG
                + " FROM " + Constants.TABLE_CHATMSG
                + " WHERE " + Constants.KEY_CRNO + "=" + crno
                + " ORDER BY " + Constants.CHATMSG_KEY_CMNO
                + " DESC LIMIT 1";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            lastMsg = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return lastMsg;
    }

    public String getTimeStamp(int crno){
        String timeStamp = "";
        String selectQuery ="SELECT " + Constants.CHATMSG_COLUMN_SENDTIME
                + " FROM " + Constants.TABLE_CHATMSG
                + " WHERE " + Constants.KEY_CRNO + "=" + crno
                + " ORDER BY " + Constants.CHATMSG_KEY_CMNO
                +" DESC LIMIT 1";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            timeStamp = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return timeStamp;
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
        db.delete(Constants.TABLE_LOGIN, null, null);

        db.close();
    }

    // Remove a record into Message table
    public void removeMessage(int cmno) {
        // 매개변수가 올바르지 않을 경웨 대한 예외처리
        /*try {
            SQLiteDatabase dbDelete = this.getWritableDatabase();
            String DELETE_FROM_CHATMSG_TABLE = "DELETE FROM " + TABLE_CHATMSG
                    + " WHERE id=cmno";
            dbDelete.execSQL(DELETE_FROM_CHATMSG_TABLE);
        } catch(Exception e) {}*/

        // 예외처리 안 된 버전
        SQLiteDatabase dbDelete = this.getWritableDatabase();
        String DELETE_FROM_CHATMSG_TABLE = "DELETE FROM " + Constants.TABLE_CHATMSG
                + " WHERE "+Constants.CHATMSG_KEY_CMNO+"="+cmno;
        dbDelete.execSQL(DELETE_FROM_CHATMSG_TABLE);
    }

}