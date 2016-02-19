package com.example.bit_user.bitchatting;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bit-user on 2016-02-17.
 */
public class FragmentMainchat extends Fragment implements OnItemClickListener {

    // Fields
    private ArrayList<MainchatLvitem> arChatroom;
    private DatabaseHandler db;
    private int myMno;
    private MainchatAdapter crAdapter;
    private ListView lvMainChat;

    private GetMychatTaskInFragment getMychatTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_fragment_mainchat, container, false);

        arChatroom = new ArrayList<>();
        DatabaseHandler db = new DatabaseHandler(getActivity());
        myMno = Integer.parseInt(db.getUserDetails().get("mNo").toString());
        crAdapter = new MainchatAdapter(getActivity(), R.layout.mainchat_listviewitem, arChatroom);
        ListView lvMainChat = (ListView)view.findViewById(R.id.mainChatFragment_listView1);
        lvMainChat.setAdapter(crAdapter);

        // ItemClickListener 구현
        lvMainChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long ig) {
                MainchatLvitem tmpChatLvItem = (MainchatLvitem) crAdapter.getItem(position);
                String strTmp = tmpChatLvItem.getChatroomInstance().getCrName() + "\n"
                        + tmpChatLvItem.getLastMsg();
            }
        });
        getMychatTask = new GetMychatTaskInFragment();
        getMychatTask.execute(myMno);

        // Fragment가 제대로 열렸는지 Test
        //Toast.makeText(getActivity(), "onCreateView() 실행됨", Toast.LENGTH_LONG).show();

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();

        // Test - Success
        //Toast.makeText(getActivity().getApplicationContext(), "onPause() 실행됨", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long ig) {
        MainchatLvitem tmpChatLvItem = (MainchatLvitem) crAdapter.getItem(position);
        String strTmp = tmpChatLvItem.getChatroomInstance().getCrName() + "\n"
                + tmpChatLvItem.getLastMsg();
    }

    class GetMychatTaskInFragment extends AsyncTask<Integer, String, Void> {
        protected Void doInBackground(Integer... mno){
            DatabaseHandler db = new DatabaseHandler(getActivity());
            List<ChatRoom> roomList = db.getChatroomList();
            MainchatLvitem cr;

            for(int i=0;i<roomList.size();i++){
                cr = new MainchatLvitem(roomList.get(i),"dd","dd");        // 수정해야함
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
