package com.example.bit_user.bitchatting.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bit_user.bitchatting.DTO.MainchatLvitem;
import com.example.bit_user.bitchatting.R;

import java.util.ArrayList;

/**
 * Created by bit-user on 2016-02-11.
 */
public class MainchatAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<MainchatLvitem> arChatroom;
    private int layout;

    public MainchatAdapter(Context mainCon, int layout, ArrayList<MainchatLvitem> arMainChat) {
        this.inflater = (LayoutInflater)mainCon.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arChatroom = arMainChat;
        this.layout = layout;
    }

    @Override
    public int getCount() { return arChatroom.size(); }

    @Override
    public Object getItem(int position) {
        // 테스트 코드에서 채팅방 일련번호는 position,
        // 자기 자신의 일련번호는 1, 상대방 일련번호는 position+2로 간주
        return arChatroom.get(position);
    }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }
        TextView crName = (TextView)convertView.findViewById(R.id.mainChat_tv_crname);
        TextView lastMsg = (TextView)convertView.findViewById(R.id.mainChat_tv_lastMsg);
        TextView nump = (TextView)convertView.findViewById(R.id.mainChat_tv_nump);
        TextView timestamp = (TextView)convertView.findViewById(R.id.mainChat_tv_time);
        crName.setText(arChatroom.get(position).getChatroomInstance().getCrName());
        lastMsg.setText(arChatroom.get(position).getLastMsg());
        nump.setText(arChatroom.get(position).getChatroomInstance().getNump()+"명");
        timestamp.setText(arChatroom.get(position).getTimestamp().substring(2,16));


        return convertView;
    }
}
