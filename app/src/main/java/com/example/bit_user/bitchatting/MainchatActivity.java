package com.example.bit_user.bitchatting;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainchatActivity extends AppCompatActivity {
    ArrayList<MainchatLvitem> arChatroom;
    MainchatAdapter crAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainchat);
        setTitle("채팅방 목록");

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
                String strTmp = tmpChatLvItem.getCrName() + "\n"
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
            JSONArray responseJSONarr;
            HttpURLConnection conn = null;
            try{
                URL url = new URL("http://192.168.1.35/BitTalkServer/mychat.jsp?mno="+mno[0]);
                conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("GET");

                conn.connect();

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while((line=br.readLine()) != null){
                    if(sb.length() > 0){
                        sb.append("\n");
                    }
                    sb.append(line);
                }
                br.close();
                responseJSONarr = new JSONArray(sb.toString());
                Log.i("JSON",responseJSONarr.toString());

                for(int i=0;i<responseJSONarr.length();i++){
                    Log.i("for",responseJSONarr.getJSONObject(i).get("crno").toString());
                }

            }catch(Exception e){
                e.printStackTrace();
            }finally {
                if(conn != null){
                    conn.disconnect();
                }
            }
            return null;
        }
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
            Log.i("post","post");
        }
    }
}
