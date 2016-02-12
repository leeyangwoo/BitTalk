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
        ChatRoom chatRoom = new ChatRoom(0, 1, 2, "Barack Obama", "Hello, good afternoon");

        ListView lvChatRoom = (ListView)findViewById(R.id.chatroom_lvChatRoom);
        ListView lvChatMsg = (ListView)findViewById(R.id.chatroom_lvChatMsg);

        // 채팅방 제목 리스트뷰에 내용 입력
        // MainchatAdapter 객체도 MainchatActivity에서 넘어와야 하지만 테스트를 위해 직접 만듦
        ArrayList<MainchatLvitem> dataMainChat = new ArrayList<>();
        MainchatLvitem mainchatLvitem = new MainchatLvitem(chatRoom.getCrName(), "");
        dataMainChat.add(mainchatLvitem);
        final MainchatAdapter adapterMainChat = new MainchatAdapter(this, R.layout.mainchat_listviewitem, dataMainChat);
        lvChatRoom.setAdapter(adapterMainChat);

        // 채팅 메시지 리스트뷰에 내용 입력
        ChatMsg chatMsg = new ChatMsg(0, 0, 2, chatRoom.getCrName(), chatRoom.getLastMsg()/*, new java.util.Date()*/);
        ArrayList<ChatroomLvitem> dataChatMsg = new ArrayList<>();
        ChatroomLvitem chatroomLvitem = new ChatroomLvitem(chatMsg);
        dataChatMsg.add(chatroomLvitem);
        // 여기까지는 정상 동작 --------------------------------------------------------------------

        final ChatmsgAdapter adapterChatMsg = new ChatmsgAdapter(this, R.layout.chatroom_listviewitem, dataChatMsg);
        lvChatMsg.setAdapter(adapterChatMsg);




        /*
        final ArrayList<LinkedList<String>> chatMsgList = new ArrayList<>();
        final ArrayList<String> chatRoomList = new ArrayList<>();
        final EditText edtItem = (EditText) findViewById(R.id.chatroom_edtvMsg);
        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        // 테스트용 채팅방 생성
        chatRoomList.add("시민은행");

        chatMsgList.add(new LinkedList<String>(){});
        chatMsgList.get(0).add("[시민은행]당신의 개인정보가 도용되었습니다. 이를 해결하기 위해 주민등록번호와 계좌번호, 비밀번호가 필요합니다.");

        ListView lvChatRoom = (ListView)findViewById(R.id.chatroom_lvChatRoom);
        ListView lvChatMsg = (ListView)findViewById(R.id.chatroom_lvChatMsg);
        Button btnSendMsg = (Button)findViewById(R.id.chatroom_btnSubmit);


        final ArrayAdapter<String> chatMsgAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, chatMsgList.get(0));
        final ArrayAdapter<String> chatRoomAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, chatRoomList);

        lvChatRoom.setAdapter(chatRoomAdapter);
        lvChatMsg.setAdapter(chatMsgAdapter);

        // 버튼을 누르면 에딧텍스트에 입력된 문자열을 리스트뷰에 올린다.
        btnSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatMsgList.get(0).add("[나]" + edtItem.getText().toString());
                chatMsgAdapter.notifyDataSetChanged();
                edtItem.setText("");
                imm.hideSoftInputFromWindow(edtItem.getWindowToken(), 0);
            }
        });
        */
    }
}




//http://developer.android.com/intl/ko/tools/debugging/debugging-studio.html
