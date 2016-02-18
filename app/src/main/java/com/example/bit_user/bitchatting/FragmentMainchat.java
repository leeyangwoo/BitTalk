package com.example.bit_user.bitchatting;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by bit-user on 2016-02-17.
 */
public class FragmentMainchat extends Fragment implements OnItemClickListener {

    // Fields
    ArrayList<MainchatLvitem> arChatroom;
    MainchatAdapter crAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Test - Success
        //Toast.makeText(getActivity().getApplicationContext(), "onCreate() 실행됨", Toast.LENGTH_LONG).show();



//        final Activity activity = getActivity();
//        if(activity != null) {
//            getActivity().setContentView(R.layout.activity_fragment_mainchat);
//            getActivity().setTitle("채팅방 목록");
//        }

        //arChatroom = new ArrayList<>();
        //crAdapter = new MainchatAdapter(getActivity(), R.layout.mainchat_listviewitem, arChatroom);
        //DatabaseHandler db = new DatabaseHandler(getActivity().getApplicationContext());
        //int myMno = Integer.parseInt(db.getUserDetails().get("mNo").toString());

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_fragment_mainchat, container, false);

        // Fragment가 제대로 열렸는지 Test - Success
        //Toast.makeText(getActivity().getApplicationContext(), "onCreateView() 실행됨", Toast.LENGTH_LONG).show();

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
}
