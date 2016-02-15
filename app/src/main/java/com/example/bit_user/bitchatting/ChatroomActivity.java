package com.example.bit_user.bitchatting;

import android.app.Activity;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
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

        // MainchatActivity에서 ChatRoom 객체가 넘어왔다고 가정하고 테스트
        int[] arMno = {1, 2};
        ChatRoom room = new ChatRoom(1, arMno, 2, "Barack Obama", "");

        ListView lvChatRoom = (ListView)findViewById(R.id.chatroom_lvChatRoom);
        ListView lvChatMsg = (ListView)findViewById(R.id.chatroom_lvChatMsg);

        // 채팅방 제목 리스트뷰에 내용 입력
        // MainchatAdapter 객체도 MainchatActivity에서 넘어와야 하지만 테스트를 위해 직접 만듦
        ArrayList<MainchatLvitem> dataRoomTitle = new ArrayList<>();
        ChatMember member = new ChatMember(1, "president@whitehouse.gov", "Barack Obama");

        MainchatLvitem lvRoomTitle = new MainchatLvitem(member, room);
        dataRoomTitle.add(lvRoomTitle);
        final MainchatAdapter adapterRoomTitle = new MainchatAdapter(this, R.layout.mainchat_listviewitem, dataRoomTitle);
        lvChatRoom.setAdapter(adapterRoomTitle);

        ChatMsg message = new ChatMsg(1, 1, 2, "Barack Obama", "Hello, good afternoon");
        ArrayList<ChatroomLvitem> dataChatroom = new ArrayList<>();
        ChatroomLvitem lvItem = new ChatroomLvitem(room, message);
        dataChatroom.add(lvItem);

        final ChatmsgAdapter adapterChatMsg = new ChatmsgAdapter(this, R.layout.chatroom_listviewitem, dataChatroom);
        lvChatMsg.setAdapter(adapterChatMsg);

        // 이전 코드 백업(리스트뷰 내용 입력부터)
        /*
        // 채팅방 제목 리스트뷰에 내용 입력
        // MainchatAdapter 객체도 MainchatActivity에서 넘어와야 하지만 테스트를 위해 직접 만듦
        ArrayList<MainchatLvitem> dataMainChat = new ArrayList<>();
        MainchatLvitem mainchatLvitem = new MainchatLvitem(chatRoom.getCrName(), "");
        dataMainChat.add(mainchatLvitem);
        final MainchatAdapter adapterMainChat = new MainchatAdapter(this, R.layout.mainchat_listviewitem, dataMainChat);
        lvChatRoom.setAdapter(adapterMainChat);

        // 채팅 메시지 리스트뷰에 내용 입력
        ChatMsg chatMsg = new ChatMsg(0, 0, 2, chatRoom.getCrName(), chatRoom.getLastMsg());
        ArrayList<ChatroomLvitem> dataChatMsg = new ArrayList<>();
        ChatroomLvitem chatroomLvitem = new ChatroomLvitem(chatMsg);
        dataChatMsg.add(chatroomLvitem);
        // 여기까지는 정상 동작 --------------------------------------------------------------------

        final ChatmsgAdapter adapterChatMsg = new ChatmsgAdapter(this, R.layout.chatroom_listviewitem, dataChatMsg);
        lvChatMsg.setAdapter(adapterChatMsg);
        */
    }
}




//http://developer.android.com/intl/ko/tools/debugging/debugging-studio.html
