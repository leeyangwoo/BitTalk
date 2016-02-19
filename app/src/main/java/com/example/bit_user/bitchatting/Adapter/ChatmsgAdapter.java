package com.example.bit_user.bitchatting.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bit_user.bitchatting.DTO.ChatroomLvitem;
import com.example.bit_user.bitchatting.R;

import java.util.ArrayList;

/**
 * Created by bit-user on 2016-02-12.
 */
public class ChatmsgAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<ChatroomLvitem> arChatMsg;
    private int layout;

    public ChatmsgAdapter(Context mainCon, int layout, ArrayList<ChatroomLvitem> arChatMsg) {
        this.inflater = (LayoutInflater)mainCon.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arChatMsg = arChatMsg;
        this.layout = layout;

    }

    @Override
    public int getCount() { return arChatMsg.size(); }

    @Override
    public Object getItem(int position) { return arChatMsg.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }

        ChatroomLvitem lvItem = arChatMsg.get(position);

        // 리스트뷰 안에 넣을 아이템 변수 파일을 선언하고 초기화
        TextView txtMember = (TextView)convertView.findViewById(R.id.chatroomLv_txtMember);
        TextView txtMsg = (TextView)convertView.findViewById(R.id.chatroomLv_txtMsg);
        txtMember.setText(arChatMsg.get(position).getSenderName());
        txtMsg.setText(arChatMsg.get(position).getChatMsgInstance().getMessage());

        return convertView;
    }
}
