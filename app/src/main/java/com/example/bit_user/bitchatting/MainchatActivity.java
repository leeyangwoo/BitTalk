package com.example.bit_user.bitchatting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainchatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainchat);


        String[] strChatPersonList = new String[100];
        String[] strChatList = new String[100];
        ListView lvMainChat = (ListView)findViewById(R.id.mainChat_listView1);

        ArrayList<MainchatLvitem> dataMainChat = new ArrayList<>();

        // 임의의 회원정보(앱을 실행중인 회원) 생성
        ChatMember tmpMember = new ChatMember(1, "aaa@abc.com", "나");

        // 임의의 채팅방 객체에 넣을 회원번호 배열 생성
        int[][] tmpArrMno = {
                {1, 2}, {1, 3}, {1, 4}, {1, 5}
        };

        // 임의의 채팅방 객체를 생성
        ChatRoom[] tmpChatRoom = new ChatRoom[4];
        tmpChatRoom[0] = new ChatRoom(1, tmpArrMno[0], 2, "Barack Obama", "Good afternoon");
        tmpChatRoom[1] = new ChatRoom(2, tmpArrMno[1], 2, "Nitta Emi", "Fighto dayo!");
        tmpChatRoom[2] = new ChatRoom(3, tmpArrMno[2], 2, "Hong Jin-ho", "Hi, everyone! Hi, everyone!");
        tmpChatRoom[3] = new ChatRoom(4, tmpArrMno[3], 2, "Bladimir Putin", "Harasho");

        MainchatLvitem[] mainchatLvitem = new MainchatLvitem[4];
        for(int i = 0; i < 4; i++) {
            mainchatLvitem[i] = new MainchatLvitem(tmpMember, tmpChatRoom[i]);
            dataMainChat.add(mainchatLvitem[i]);
        }
        final MainchatAdapter adapterMainChat = new MainchatAdapter(this, R.layout.mainchat_listviewitem, dataMainChat);
        lvMainChat.setAdapter(adapterMainChat);
        lvMainChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainchatLvitem tmpChatLvItem = (MainchatLvitem)adapterMainChat.getItem(position);
                String strTmp = tmpChatLvItem.getCrName() + "\n"
                        + tmpChatLvItem.getLastMsg();
                Toast.makeText(getApplicationContext(), strTmp, Toast.LENGTH_LONG).show();

            }
        });
    }
}
