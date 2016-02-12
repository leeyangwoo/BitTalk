package com.example.bit_user.bitchatting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by bit-user on 2016-02-12.
 */
public class ChatmsgAdapter extends BaseAdapter {
    /*
    private LayoutInflater inflater;
    private ArrayList<ChatroomLvitem> arChatMsg;
    private int layout;

    public ChatmsgAdapter(Context mainCon, int layout, ArrayList<ChatroomLvitem> arMainChat) {
        this.inflater = (LayoutInflater)mainCon.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arChatMsg = arChatMsg;
        this.layout = layout;
    }

    @Override
    public int getCount() { return arChatMsg.size(); }

    @Override
    public ChatMsg getItem(int position) {
        return new ChatMsg(position, arChatMsg.get(position).getChatMsgInstance().getCrno(),
                arChatMsg.get(position).getChatMsgInstance().getSenderNo(),
                arChatMsg.get(position).getTxtMember(), arChatMsg.get(position).getTxtMsg()/*,
                arChatMsg.get(position).getChatMsgInstance().getSentDate()*//*);
    }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }

        ChatroomLvitem lvItem = arChatMsg.get(position);

        TextView txtMember = (TextView)convertView.findViewById(R.id.chatroomLv_txtMember);
        TextView txtMsg = (TextView)convertView.findViewById(R.id.chatroomLv_txtMsg);

        // 각 텍스트뷰에 아이템의 텍스트를 넣는다.
        txtMember.setText(arChatMsg.get(position).getTxtMember());
        txtMsg.setText(arChatMsg.get(position).getTxtMsg());

        return convertView;
    }
    */

    private Context mainCon;
    private LayoutInflater inflater;
    private ArrayList<ChatroomLvitem> arChatMsg;
    private int layout;

    public ChatmsgAdapter(Context mainCon, int layout, ArrayList<ChatroomLvitem> arChatMsg) {
        this.mainCon = mainCon;
        this.arChatMsg = arChatMsg;
        this.layout = layout;
        this.inflater = LayoutInflater.from(mainCon);
    }

    public int getCount() { return arChatMsg.size(); }
    public Object getItem(int position) { return arChatMsg.get(position).getTxtMember(); }
    public long getItemId(int position) { return position; }

    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        if(convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }
        // 리스트뷰 안에 넣을 아이템 변수 파일을 선언하고 초기화
        TextView txtMember = (TextView)convertView.findViewById(R.id.chatroomLv_txtMember);
        TextView txtMsg = (TextView)convertView.findViewById(R.id.chatroomLv_txtMsg);
        txtMember.setText(arChatMsg.get(position).getTxtMember());
        txtMember.setText(arChatMsg.get(position).getTxtMsg());

        return convertView;
    }
}
