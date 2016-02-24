package com.example.bit_user.bitchatting.Unused;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bit_user.bitchatting.Constants;
import com.example.bit_user.bitchatting.DB.DatabaseHandler;
import com.example.bit_user.bitchatting.DTO.ChatRoom;
import com.example.bit_user.bitchatting.DTO.MainchatLvitem;
import com.example.bit_user.bitchatting.Adapter.MainchatAdapter;
import com.example.bit_user.bitchatting.R;

import java.util.ArrayList;
import java.util.List;

public class MainchatActivity extends AppCompatActivity {
    ArrayList<MainchatLvitem> arChatroom;
    MainchatAdapter crAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainchat);
        setTitle(Constants.MAINCHAT_TITLE);

        arChatroom = new ArrayList<>();
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        int myMno = Integer.parseInt(db.getUserDetails().get("mNo").toString());

        crAdapter = new MainchatAdapter(this, R.layout.mainchat_listviewitem, arChatroom);
        ListView lvMainChat = (ListView)findViewById(R.id.mainChat_listView1);
        lvMainChat.setAdapter(crAdapter);

        lvMainChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainchatLvitem tmpChatLvItem = (MainchatLvitem) crAdapter.getItem(position);
                String strTmp = tmpChatLvItem.getChatroomInstance().getCrName() + "\n"
                        + tmpChatLvItem.getLastMsg();
                Toast.makeText(getApplicationContext(), strTmp, Toast.LENGTH_LONG).show();

            }
        });
        GetMychatTask getMychatTask = new GetMychatTask();
        getMychatTask.execute(myMno);

        /*String[] strChatPersonList = new String[100];
        String[] strChatList = new String[100];


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
        }*/
        /*final MainchatAdapter adapterMainChat = new MainchatAdapter(this, R.layout.mainchat_listviewitem, dataMainChat);
        lvMainChat.setAdapter(adapterMainChat);*/

    }

    class GetMychatTask extends AsyncTask<Integer, String, Void>{
        protected Void doInBackground(Integer... mno){
            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
            List<ChatRoom> roomList = db.getChatroomList();
            MainchatLvitem cr;

            for(int i=0;i<roomList.size();i++){
                cr = new MainchatLvitem(roomList.get(i),db.getLastMsg(roomList.get(i).getCrno()),
                        db.getTimeStamp(roomList.get(i).getCrno()));        // 수정해야함
                arChatroom.add(cr);
            }

            return null;
        }
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
            crAdapter.notifyDataSetChanged();
        }
    }

}
