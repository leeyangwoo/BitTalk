package com.example.bit_user.bitchatting.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.bit_user.bitchatting.Adapter.ChatmsgAdapter;
import com.example.bit_user.bitchatting.DTO.ChatroomLvitem;
import com.example.bit_user.bitchatting.Constants;
import com.example.bit_user.bitchatting.DTO.ChatMsg;
import com.example.bit_user.bitchatting.DB.DatabaseHandler;
import com.example.bit_user.bitchatting.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by bit-user on 2016-02-05.
 */
public class ChatroomActivity extends Activity {


    InputMethodManager imm;     // 키보드 화면 제어를 위한 변수
    ArrayList<ChatroomLvitem> arMsg;  // 채팅메세지 담을 배열
    ChatmsgAdapter msgAdapter;        // 채팅메세지 adapter
    ListView lvChatMsg;               // 채팅메세지 리스트뷰
    EditText edtvMsg;                 // 메세지 입력창
    Button btnSend;                   // 메세지 입력버튼
    DatabaseHandler db;
    int new_checker = 0;
    private Socket mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        setTitle("채팅방");

        Intent intent = getIntent();              //Intent로 상대회원번호, 방번호, 새로운 방인지 기존의 방인지를 받아옴
        final int mno = intent.getIntExtra("mno", 0);
        final int crno = intent.getIntExtra("crno",0);
        final String detail = intent.getStringExtra("detail");
        Log.i("intent", "mno: "+mno + " crno: " + crno + " " + detail);
        ///////////////////////////////////////////
        try {
            mSocket = IO.socket(Constants.CHAT_SERVER_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        mSocket.on("new message", onNewMessage);
        mSocket.connect();
        //////////////////////////////////////////
        arMsg = new ArrayList<>();
        db = new DatabaseHandler(getApplicationContext());
        msgAdapter = new ChatmsgAdapter(this, R.layout.chatroom_listviewitem, arMsg);
        lvChatMsg = (ListView)findViewById(R.id.chatroom_lvChatMsg);
        edtvMsg = (EditText)findViewById(R.id.chatroom_edtvMsg);
        btnSend = (Button)findViewById(R.id.chatroom_btnSubmit);
        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        lvChatMsg.setAdapter(msgAdapter);

        if(detail.equals("exist")) {
            GetMsgTask getMsgTask = new GetMsgTask();           //내장DB에 저장된 메세지리스트를 불러오는 Task
            getMsgTask.execute(crno);
            mSocket.emit("join room", crno);
        }else if(detail.equals("new")){
            mSocket.emit("create room", crno);
        }


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {         //전송버튼 리스너
                if (!mSocket.connected()) return;
                String msg = edtvMsg.getText().toString();
                if (msg.isEmpty()) {
                    edtvMsg.requestFocus();
                    return;
                }
                new_checker = db.existChatroom(crno);

                if (detail.equals("new") || new_checker == 0) {                   //상대방이 없는 방인 경우 상대방을 초대(MySQL DB 갱신)
                    JSONObject responseJSON = null;
                    new InviteTask().execute(mno, crno);
                    try {
                        responseJSON = new GetMnameTask().execute(mno).get();
                        if (responseJSON.get("result").equals("success")) {
                            db.addChatroom(crno, 2, responseJSON.get("mname").toString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    new_checker = 1;
                }
                db.addMessage(crno, Integer.parseInt(db.getUserDetails().get("mNo").toString()),
                        msg);      //메세지를 내장DB에 저장
                edtvMsg.setText("");
                JSONObject sendInfo = new JSONObject();
                try {
                    sendInfo.put("crno", crno);
                    sendInfo.put("msg", msg);
                    sendInfo.put("senderName", db.getUserDetails().get("mName"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mSocket.emit("new message", sendInfo);
            }
        });

    }

    class GetMsgTask extends AsyncTask<Integer, String, Void>{     //내장DB의 메세지 불러오는 Task

        @Override
        protected Void doInBackground(Integer... crno) {
            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
            List<ChatMsg> msgList = db.getMessageList(crno[0]);
            ChatroomLvitem msg;


            for(int i=0; i<msgList.size(); i++){
                msg = new ChatroomLvitem(msgList.get(i));
                msg.setSenderName(db.getUserDetails().get("mName").toString());  //sendername도 저장
                arMsg.add(msg);
            }

            return null;
        }

        protected void onPostExecute(Void result){
            super.onPostExecute(result);
            msgAdapter.notifyDataSetChanged();
        }
    }

    class InviteTask extends AsyncTask<Integer, String, JSONObject>{    // 상대방 초대하는 Task
        @Override
        protected JSONObject doInBackground(Integer... params) {
            HttpURLConnection conn = null;
            JSONObject responseJSON = null;
            try{
                URL url = new URL("http://192.168.1.35/BitTalkServer/invite.jsp?mno="+params[0]+"&crno="+params[1]);
                Log.i("invite URL",url.toString());
                conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while((line = br.readLine()) != null){
                    if(sb.length() > 0){
                        sb.append("\n");
                    }
                    sb.append(line);
                }
                br.close();
                responseJSON = new JSONObject(sb.toString());
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(conn != null){
                    conn.disconnect();
                }
            }

            return responseJSON;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
            try {
                if(result.get("result").equals("success")){
                    Log.i("invite","success");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    class GetMnameTask extends AsyncTask<Integer, String, JSONObject>{
        @Override
        protected JSONObject doInBackground(Integer... params) {
            HttpURLConnection conn = null;
            JSONObject responseJSON = null;
            try{
                URL url = new URL("http://192.168.1.35/BitTalkServer/who.jsp?mno="+params[0]);
                Log.i("who URL",url.toString());
                conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while((line = br.readLine()) != null) {
                    if (sb.length() > 0) {
                        sb.append("\n");
                    }
                    sb.append(line);
                }
                br.close();
                responseJSON = new JSONObject(sb.toString());
            }catch(Exception e){
                e.printStackTrace();
            }finally {
                if(conn != null){
                    conn.disconnect();
                }
            }

            return responseJSON;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
        }
    }
    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                    ChatroomLvitem msg;
                    Log.i("onNewMessage Listener", args[0].toString());

                    try {
                        //msg = data.getString("message");
                        ChatMsg msgInstance = new ChatMsg();
                        msgInstance.setCrno(Integer.parseInt(data.getString("crno")));
                        msgInstance.setMessage(data.getString("message"));
                        msg = new ChatroomLvitem(msgInstance);
                        msg.setSenderName(data.getString("username"));
                        arMsg.add(msg);
                        msgAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    };

}

