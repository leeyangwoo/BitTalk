package com.example.bit_user.bitchatting;

/**
 * Created by bit-user on 2016-02-11.
 */
public class MainchatLvitem {

    // Fields
    private static ChatMember member;
    private String crName;
    private String lastMsg;
    private ChatRoom chatroomInstance;

    // Constructor
    public MainchatLvitem(ChatMember member, ChatRoom chatroomInstance) {

        this.member = new ChatMember(member.getMno(), member.getMid(), member.getMname());
        try {
            if(!memberInChatroom(chatroomInstance))
                throw new Exception();
            this.chatroomInstance = new ChatRoom(
                    chatroomInstance.getCrno(), chatroomInstance.getArMno(),
                    chatroomInstance.getParticipantNum(), chatroomInstance.getCrName(),
                    chatroomInstance.getLastMsg());
            this.crName = chatroomInstance.getCrName();
            this.lastMsg = chatroomInstance.getLastMsg();
        } catch(Exception ex) {

        }
    }

    // Getter/Setter
    //   1. Getter
    public static ChatMember getMember() { return member; }
    public String getCrName() { return crName; }
    public String getLastMsg() { return lastMsg; }
    public ChatRoom getChatroomInstance() { return chatroomInstance; }

    //   2. Setter
    public void setCrName() { this.crName = crName; }
    public void setLastMsg() { this.lastMsg = lastMsg; }
    public void setChatroomInstance(ChatRoom chatroomInstance) {
        try {
            if(!memberInChatroom(chatroomInstance)) throw new Exception();
            this.chatroomInstance = null;
            this.chatroomInstance = new ChatRoom(
                    chatroomInstance.getCrno(), chatroomInstance.getArMno(),
                    chatroomInstance.getParticipantNum(), chatroomInstance.getCrName(),
                    chatroomInstance.getLastMsg());
        } catch(Exception ex) {

        }
    }

    // 멤버 클래스가 채팅방 객체 안에 포함되어 있는지 검사
    public static boolean memberInChatroom(ChatRoom chatroomInstance) {
        for(int i = 0; i < chatroomInstance.getArMno().length; i++) {
            if(member.getMno() == chatroomInstance.getArMno()[i])
                return true;
        }
        return false;
    }
}
