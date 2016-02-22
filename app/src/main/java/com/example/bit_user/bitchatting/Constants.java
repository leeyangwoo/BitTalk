package com.example.bit_user.bitchatting;

public class Constants {
    // Server Address
    public static final String NODE_SERVER_URL = "http://192.168.1.34:3000";    // Node Server
    public static final String CHAT_SERVER_URL =  "http://192.168.1.35/BitTalkServer/";        // Chat Server

    // All Static variables
    // Database Version
    public static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "bittalk";

    //table name
    public static final String TABLE_LOGIN = "member";
    public static final String TABLE_CHATROOM = "mychatroom";
    public static final String TABLE_CHATMSG = "chatmsg";

    // Login Table Columns names
    public static final String KEY_MNO = "mno";
    public static final String KEY_MID = "mid";
    public static final String KEY_MPASSWORD = "mpasswd";
    public static final String KEY_MNAME = "mname";

    // Chatroom table column
    public static final String KEY_CRNO = "crno";
    public static final String KEY_NUMP = "numparticipant";
    public static final String KEY_CRNAME = "crname";

    // Message Table Columns names
    public static final String CHATMSG_KEY_CMNO = "cmno";
    public static final String CHATMSG_KEY_CRNO = "crno";
    public static final String CHATMSG_COLUMN_SENDERNO = "senderno";
    public static final String CHATMSG_COLUMN_MSG = "msg";
    public static final String CHATMSG_COLUMN_SENDTIME = "sendtime";

    public final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
}
