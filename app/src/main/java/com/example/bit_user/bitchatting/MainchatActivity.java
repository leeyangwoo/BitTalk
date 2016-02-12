package com.example.bit_user.bitchatting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainchatActivity extends AppCompatActivity {

    String[] strChatPersonList = new String[100];
    String[] strChatList = new String[100];
    int nChatNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainchat);

        ListView lvMainChat = (ListView)findViewById(R.id.mainChat_listView1);

        ArrayList<MainchatLvitem> dataMainChat = new ArrayList<>();
        MainchatLvitem[] mainchatLvitem = new MainchatLvitem[100];
        mainchatLvitem[0] = new MainchatLvitem("Barack Obama", "Good afternoon");
        mainchatLvitem[1] = new MainchatLvitem("Nitta Emi", "Fighto dayo!");
        mainchatLvitem[2] = new MainchatLvitem("Hong Jin-ho", "Hi, everyone! Hi, everyone!");
        mainchatLvitem[3] = new MainchatLvitem("Bladimir Putin", "Harasho");

        for(int i = 0; i < 4; i++)
            dataMainChat.add(mainchatLvitem[i]);


        final MainchatAdapter adapterMainChat = new MainchatAdapter(this, R.layout.mainchat_listviewitem, dataMainChat);
        lvMainChat.setAdapter(adapterMainChat);

        lvMainChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 해당 채팅 상대와 마지막 메시지를 Toast로 표시해 본다.
                String strTmp = adapterMainChat.getChatRoomInfo(position).getCrName() + "\n"
                        + adapterMainChat.getChatRoomInfo(position).getLastMsg();
                Toast.makeText(getApplicationContext(), strTmp, Toast.LENGTH_LONG).show();

            }
        });


        /*
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
        */
    }
}