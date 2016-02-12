package com.example.bit_user.bitchatting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by bit-user on 2016-02-11.
 */
public class MainchatAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<MainchatLvitem> arMainChat;
    private int layout;

    public MainchatAdapter(Context mainCon, int layout, ArrayList<MainchatLvitem> arMainChat) {
        this.inflater = (LayoutInflater)mainCon.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arMainChat = arMainChat;
        this.layout = layout;
    }

    @Override
    public int getCount() { return arMainChat.size(); }

    @Override
    public String getItem(int position) {
        // 테스트 코드에서 채팅방 일련번호는 position,
        // 자기 자신의 일련번호는 1, 상대방 일련번호는 position+2로 간주
        return arMainChat.get(position).getCrName();
    }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }

        MainchatLvitem lvItem = arMainChat.get(position);

        TextView txtCrName = (TextView)convertView.findViewById(R.id.txtCrName);
        TextView txtLastMsg = (TextView)convertView.findViewById(R.id.txtLastMsg);

        // 각 텍스트뷰에 아이템의 텍스트를 넣는다.
        txtCrName.setText(arMainChat.get(position).getCrName());
        txtLastMsg.setText(arMainChat.get(position).getLastMsg());

        return convertView;
    }

    public ChatRoom getChatRoomInfo(int position) {
        // 테스트 코드에서 채팅방 일련번호는 position,
        // 자기 자신의 일련번호는 1, 상대방 일련번호는 position+2로 간주
        return new ChatRoom(position, 1, position + 2,
                arMainChat.get(position).getCrName(), arMainChat.get(position).getLastMsg());
    }

}
