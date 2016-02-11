package com.example.bit_user.bitchatting;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by bit-user on 2016-02-05.
 */
public class ChatroomActivity extends Activity {

    InputMethodManager imm;     // 키보드 화면 제어를 위한 변수

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        setTitle("채팅방");

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

    }
}


