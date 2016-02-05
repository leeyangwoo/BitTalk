package com.example.bit_user.bitchatting;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by bit-user on 2016-02-05.
 */
public class ChatRoomActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        setTitle("채팅방");
    }
}
