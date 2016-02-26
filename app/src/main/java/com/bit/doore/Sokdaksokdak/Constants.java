package com.bit.doore.Sokdaksokdak;

public class Constants {
    // Server Address
    public static final String NODE_SERVER_URL = "http://192.168.1.34:3000";                    // Node Server
    public static final String CHAT_SERVER_URL =  "http://192.168.1.35/BitTalkServer/";         // Chat Server

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
    public static final String KEY_MTOKEN = "mtoken";

    // Chatroom table column
    public static final String KEY_CRNO = "crno";
    public static final String KEY_NUMP = "numparticipant";
    public static final String KEY_CRNAME = "crname";

    // Message Table Columns names
    public static final String CHATMSG_KEY_CMNO = "cmno";
    public static final String CHATMSG_KEY_CRNO = "crno";
    public static final String CHATMSG_COLUMN_SENDERNO = "senderno";
    public static final String CHATMSG_COLUMN_SENDERNAME = "senderName";
    public static final String CHATMSG_COLUMN_MSG = "msg";
    public static final String CHATMSG_COLUMN_SENDTIME = "sendtime";

    public final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    public static final int TYPE_MESSAGE = 0;
    public static final int TYPE_LOG = 1;
    public static final int TYPE_ACTION = 2;

    public static final String CHATROOM_TITLE = "채팅방";
    public static final String MAINCHAT_TITLE = "채팅방 목록";
    public static final String MAINSRC_TITLE = "대화상대 찾기";
    public static final String JSP_INVITE = "invite.jsp";
    public static final String JSP_WHO = "who.jsp";
    public static final String JSP_JOIN = "join.jsp";
    public static final String TOASTMSG_JOIN_SUCCESS = "Welcome! Login Please";
    public static final String TOASTMSG_JOIN_FAIL = "ID is already exist";
    public static final String JSP_LOGIN = "login.jsp";
    public static final String JSP_PUSH = "push.jsp";
    public static final String TOASTMSG_LOGIN_SUCCESS = "Successfully Logged In!";
    public static final String TOASTMSG_WRONG_PWD = "Incorrect Id or Password";

    public static final int SPLASH_TIME = 5000;

    public static final String JSP_TALK = "talk.jsp";
    public static final String JSP_SEARCH = "search.jsp";
    public static final String JSP_SETTOKEN = "settoken.jsp";

    public static final int REQUEST_LOGIN = 0;
    public static final int TYPING_TIMER_LENGTH = 600;
}
