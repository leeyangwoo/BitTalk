package com.example.bit_user.bitchatting.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.bit_user.bitchatting.DTO.ChatMsg;
import com.example.bit_user.bitchatting.DTO.ChatRoom;

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

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "bittalk";

    //table name
    private static final String TABLE_LOGIN = "member";
    private static final String TABLE_CHATROOM = "mychatroom";
    private static final String TABLE_CHATMSG = "chatmsg";

    // Login Table Columns names
    private static final String KEY_MNO = "mno";
    private static final String KEY_MID = "mid";
    private static final String KEY_MPASSWORD = "mpasswd";
    private static final String KEY_MNAME = "mname";

    // Chatroom table column
    private static final String KEY_CRNO = "crno";
    private static final String KEY_NUMP = "numparticipant";
    private static final String KEY_CRNAME = "crname";

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
                + KEY_MNO + " INTEGER PRIMARY KEY,"
                + KEY_MID + " TEXT,"
                + KEY_MPASSWORD + " TEXT,"
                + KEY_MNAME + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);
        String CREATE_CHATROOM_TABLE = "CREATE TABLE " + TABLE_CHATROOM + "("
                + KEY_CRNO + " INTEGER PRIMARY KEY,"
                + KEY_NUMP + " INTEGER,"
                + KEY_CRNAME + " TEXT" + ")";
        db.execSQL(CREATE_CHATROOM_TABLE);

        // Create Message Table
        String CREATE_MSG_TABLE = "CREATE TABLE " + TABLE_CHATMSG + "("
                + CHATMSG_KEY_CMNO + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + CHATMSG_KEY_CRNO + " INTEGER NOT NULL,"
                + CHATMSG_COLUMN_SENDERNO + " INT NOT NULL,"
                + CHATMSG_COLUMN_MSG + " TEXT NOT NULL,"
                + CHATMSG_COLUMN_SENDTIME + " TIMESTAMP NOT NULL,"
                + "FOREIGN KEY ("+CHATMSG_KEY_CRNO+") REFERENCES "+TABLE_CHATROOM+"("+KEY_CRNO+"));";
        db.execSQL(CREATE_MSG_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHATROOM );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHATMSG );
        // Create tables again
        onCreate(db);
    }

    /**
     * Insert
     * */
    public void addUser(int mNo, String mId, String mPassword, String mName) {
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

    public void addChatroom(int crno, int nump, String crName){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CRNO, crno);
        values.put(KEY_NUMP, nump);
        values.put(KEY_CRNAME, crName);

        db.insert(TABLE_CHATROOM, null, values);
        db.close();
    }

    // Add a record into Message table
    public void addMessage(int crno, int senderid, String message) {
        // 매개변수가 올바르지 않을 경우에 대한 예외처리
        /*try {
            SQLiteDatabase dbInsert = this.getWritableDatabase();
            String INSERT_INTO_CHATMSG_TABLE = "INSERT INTO " + TABLE_CHATMSG
                    + "(crno,senderid,message,timestamp) VALUES (" + Integer.toString(crno)
                    + "," + Integer.toString(senderid) + "," + message
                    + new Timestamp(System.currentTimeMillis()).toString() + ")";
            dbInsert.execSQL(INSERT_INTO_CHATMSG_TABLE);
        } catch(Exception e) {}*/

        // 예외처리 안 된 버전
        SQLiteDatabase dbInsert = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CHATMSG_KEY_CRNO, crno);
        values.put(CHATMSG_COLUMN_SENDERNO, senderid);
        values.put(CHATMSG_COLUMN_MSG, message);
        values.put(CHATMSG_COLUMN_SENDTIME, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA).format(new Date()));

        dbInsert.insert(TABLE_CHATMSG, null, values);
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

    public List<ChatRoom> getChatroomList(){
        List<ChatRoom> roomList = new ArrayList<>();
        String selectQuery = "SELECT DISTINCT r."+KEY_CRNO+", "+KEY_NUMP+", "+KEY_CRNAME+" FROM "
                + TABLE_CHATROOM + " AS r INNER JOIN "+TABLE_CHATMSG+" AS m ON r."+KEY_CRNO+"=m."+KEY_CRNO
                +" ORDER BY "+CHATMSG_COLUMN_SENDTIME+" DESC";
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
        String selectQuery = "SELECT * FROM " + TABLE_CHATROOM + " WHERE "+KEY_CRNO+"="+crno;
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
        String selectQuery = "SELECT * FROM " + TABLE_CHATMSG + " WHERE "+CHATMSG_KEY_CRNO+"="+crno;
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
        String selectQuery ="SELECT msg FROM chatmsg WHERE "+KEY_CRNO+"="+crno+" ORDER BY "+CHATMSG_KEY_CMNO
                    +" DESC LIMIT 1";
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
        String selectQuery ="SELECT sendtime FROM chatmsg WHERE "+KEY_CRNO+"="+crno+" ORDER BY "+CHATMSG_KEY_CMNO
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
        db.delete(TABLE_LOGIN, null, null);

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
        String DELETE_FROM_CHATMSG_TABLE = "DELETE FROM " + TABLE_CHATMSG
                + " WHERE "+CHATMSG_KEY_CMNO+"="+cmno;
        dbDelete.execSQL(DELETE_FROM_CHATMSG_TABLE);
    }

}