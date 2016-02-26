package com.example.bit_user.bitchatting.Activity;

import android.support.v4.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bit_user.bitchatting.Adapter.MainchatAdapter;
import com.example.bit_user.bitchatting.Constants;
import com.example.bit_user.bitchatting.DB.DatabaseHandler;
import com.example.bit_user.bitchatting.DTO.ChatRoom;
import com.example.bit_user.bitchatting.DTO.MainchatLvitem;
import com.example.bit_user.bitchatting.GCM.QuickstartPreferences;
import com.example.bit_user.bitchatting.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by bit-user on 2016-02-19.
 */
public class FragmentMainchat extends Fragment implements OnItemClickListener {

    // Fields
    private ArrayList<MainchatLvitem> arChatroom;
    private MainchatAdapter crAdapter;
    private BroadcastReceiver pushReceiver;

    public FragmentMainchat(){}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_fragment_mainchat, container, false);

        arChatroom = new ArrayList<>();

        crAdapter = new MainchatAdapter(getActivity(), R.layout.mainchat_listviewitem, arChatroom);
        ListView lvMainChat = (ListView)view.findViewById(R.id.mainChatFragment_listView1);
        lvMainChat.setAdapter(crAdapter);

        lvMainChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainchatLvitem tmpChatLvItem = (MainchatLvitem) crAdapter.getItem(position);

                Intent i = new Intent(getActivity(), ChatroomActivity.class);
                i.putExtra(Constants.KEY_CRNO, tmpChatLvItem.getChatroomInstance().getCrno());
                i.putExtra("detail", "exist");
                startActivity(i);
            }
        });
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(pushReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        DatabaseHandler db = new DatabaseHandler(getActivity());
        final int myMno = Integer.parseInt(db.getUserDetails().get(Constants.KEY_MNO).toString());
        GetMychatTaskInFragment getMychatTask = new GetMychatTaskInFragment();
        getMychatTask.execute(myMno);
        pushReceiver = new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {
                new GetMychatTaskInFragment().execute(myMno);
            }
        };
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(pushReceiver,
                new IntentFilter(QuickstartPreferences.MAINCHAT_PUSH_RECEIVE));


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long ig) {
        MainchatLvitem tmpChatLvItem = (MainchatLvitem) crAdapter.getItem(position);
        String strTmp = tmpChatLvItem.getChatroomInstance().getCrName() + "\n"
                + tmpChatLvItem.getLastMsg();
        Toast.makeText(getActivity(), strTmp, Toast.LENGTH_LONG).show();
    }

    class GetMychatTaskInFragment extends AsyncTask<Integer, String, Void> {
        protected Void doInBackground(Integer... mno){
            DatabaseHandler db = new DatabaseHandler(getActivity());
            List<ChatRoom> roomList = db.getChatroomList();
            MainchatLvitem cr;
            arChatroom.clear();
            for(int i=0;i<roomList.size();i++){
                cr = new MainchatLvitem(roomList.get(i),db.getLastMsg(roomList.get(i).getCrno()),
                        db.getTimeStamp(roomList.get(i).getCrno()));
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
