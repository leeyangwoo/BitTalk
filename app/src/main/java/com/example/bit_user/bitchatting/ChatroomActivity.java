package com.example.bit_user.bitchatting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by bit-user on 2016-02-05.
 */
public class ChatroomActivity extends Activity {

    InputMethodManager imm;     // 키보드 화면 제어를 위한 변수


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        setTitle("채팅방");
        Intent intent = getIntent();
        int mno = intent.getIntExtra("mno", 0);
        int crno = intent.getIntExtra("crno",0);
        Log.i("intent",mno+" "+crno);

        // MainchatActivity에서 ChatRoom 객체가 넘어왔다고 가정하고 테스트
        int[] arMno = {1, 2};
        final ChatRoom room = new ChatRoom(1, arMno, 2, "Barack Obama", "");

        //
        final ListView lvChatRoom = (ListView)findViewById(R.id.chatroom_lvChatRoom);
        final ListView lvChatMsg = (ListView)findViewById(R.id.chatroom_lvChatMsg);
        final Button btnSendMsg = (Button)findViewById(R.id.chatroom_btnSubmit);
        final EditText edtItem = (EditText)findViewById(R.id.chatroom_edtvMsg);
        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        // 채팅방 제목 리스트뷰에 내용 입력
        // MainchatAdapter 객체도 MainchatActivity에서 넘어와야 하지만 테스트를 위해 직접 만듦
        ArrayList<MainchatLvitem> dataRoomTitle = new ArrayList<>();
        ChatMember member = new ChatMember(2, "president@whitehouse.gov", "Barack Obama");

        MainchatLvitem lvRoomTitle = new MainchatLvitem(member, room);
        dataRoomTitle.add(lvRoomTitle);
        final MainchatAdapter adapterRoomTitle = new MainchatAdapter(this, R.layout.mainchat_listviewitem, dataRoomTitle);
        lvChatRoom.setAdapter(adapterRoomTitle);

        final ArrayList<ChatroomLvitem> dataChatroom = new ArrayList<>();
        ChatMsg message1 = new ChatMsg(dataChatroom.size() + 1, 1, 2, "Barack Obama", "Hello, good morning");
        ChatMsg message2 = new ChatMsg(dataChatroom.size() + 2, 1, 2, "Barack Obama", "Good afternoon");
        ChatMsg message3 = new ChatMsg(dataChatroom.size() + 3, 1, 2, "Barack Obama", "Good evening");

        ChatroomLvitem lvItem1 = new ChatroomLvitem(room, message1);
        dataChatroom.add(lvItem1);
        ChatroomLvitem lvItem2 = new ChatroomLvitem(room, message2);
        dataChatroom.add(lvItem2);
        ChatroomLvitem lvItem3 = new ChatroomLvitem(room, message3);
        dataChatroom.add(lvItem3);
        final ChatmsgAdapter adapterChatMsg = new ChatmsgAdapter(this, R.layout.chatroom_listviewitem, dataChatroom);
        lvChatMsg.setAdapter(adapterChatMsg);

        // 버튼을 누르면 에딧텍스트에 입력된 문자열을 리스트뷰에 올린다.
        btnSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatMsg tmpMessage = new ChatMsg(dataChatroom.size() + 1, 1, 1, "나", edtItem.getText().toString());

                ChatroomLvitem tmpLvItem = new ChatroomLvitem(room, tmpMessage);
                dataChatroom.add(tmpLvItem);

                adapterChatMsg.notifyDataSetChanged();
                edtItem.setText("");
                imm.hideSoftInputFromWindow(edtItem.getWindowToken(), 0);
            }
        });
    }
}




//http://developer.android.com/intl/ko/tools/debugging/debugging-studio.html
