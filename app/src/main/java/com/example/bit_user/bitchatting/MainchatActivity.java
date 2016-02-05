package com.example.bit_user.bitchatting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainChatActivity extends AppCompatActivity {

    String[] strChatPersonList = new String[100];
    String[] strChatList = new String[100];
    int nChatNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainchat);

        final ArrayList<String> chatList = new ArrayList<>();
        ListView list = (ListView)findViewById(R.id.listView1);

        // 임의의 채팅방 목록

        strChatPersonList[0] = "시민은행";
        strChatPersonList[1] = "김미영팀장";
        strChatPersonList[2] = "국가정보원";
        strChatList[0] = "귀하의 개인정보가 도용...";
        strChatList[1] = "80만원 대.츌";
        strChatList[2] = "시계 받으세요.";
        nChatNum = 3;

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, chatList);
        list.setAdapter(adapter);

        // 미리 만들어진 채팅방 목록을 자동으로 불러오기
        /*for(String strChat : strChatList) {
            chatList.add(strChatPersonList + "\n" + strChat);
            adapter.notifyDataSetChanged();
        }*/
        for(int i = 0; i < nChatNum; i++) {
            chatList.add(strChatPersonList[i] + "\n" + strChatList[i]);
            adapter.notifyDataSetChanged();
        }

        // 리스트의 항목을 누르면 해당 채팅방이 선택됨
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 해당 채팅 상대와 마지막 메시지를 Toast로 표시해 본다.
                //Toast.makeText(getApplicationContext(), "여기에 채팅 상대/마지막 메시지를 넣으시오",
                //        Toast.LENGTH_LONG).show();
                String strTmp = strChatPersonList[position] + "\n" + strChatList[position];
                Toast.makeText(getApplicationContext(), strTmp,
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
