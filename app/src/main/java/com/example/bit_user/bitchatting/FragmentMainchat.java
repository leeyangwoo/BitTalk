package com.example.bit_user.bitchatting;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by bit-user on 2016-02-19.
 */
public class FragmentMainchat extends Fragment {

    // Fields
    private ArrayList<MainchatLvitem> arChatroom;
    private MainchatAdapter crAdapter;

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
        int myMno = Integer.parseInt(db.getUserDetails().get("mNo").toString());

        crAdapter = new MainchatAdapter(getActivity(), R.layout.mainchat_listviewitem, arChatroom);
        ListView lvMainChat = (ListView)view.findViewById(R.id.mainChatFragment_listView1);
        lvMainChat.setAdapter(crAdapter);

        lvMainChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainchatLvitem tmpChatLvItem = (MainchatLvitem) crAdapter.getItem(position);
                String strTmp = tmpChatLvItem.getChatroomInstance().getCrName() + "\n"
                        + tmpChatLvItem.getLastMsg();
                Toast.makeText(getActivity(), strTmp, Toast.LENGTH_LONG).show();

            }
        });
        GetMychatTaskInFragment getMychatTask = new GetMychatTaskInFragment();
        getMychatTask.execute(myMno);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    class GetMychatTaskInFragment extends AsyncTask<Integer, String, Void> {
        protected Void doInBackground(Integer... mno){
            DatabaseHandler db = new DatabaseHandler(getActivity());
            List<ChatRoom> roomList = db.getChatroomList();
            MainchatLvitem cr;

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
