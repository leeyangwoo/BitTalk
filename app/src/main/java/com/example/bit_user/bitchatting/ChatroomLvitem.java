package com.example.bit_user.bitchatting;

/**
 * Created by bit-user on 2016-02-12.
 */
public class ChatroomLvitem {

    // Fields
    private static ChatRoom room;
    private String txtMember;
    private String txtMsg;
    private ChatMsg chatMsgInstance;

    // Constructor
    public ChatroomLvitem(ChatRoom room, ChatMsg chatMsgInstance) {
        this.room = new ChatRoom(room.getCrno(), room.getArMno(), room.getParticipantNum(), room.getCrName(), room.getLastMsg());
        try {
            if(this.room.getCrno() != chatMsgInstance.getCrno())
                throw new Exception();
            this.chatMsgInstance = new ChatMsg(
                    chatMsgInstance.getCmno(),
                    chatMsgInstance.getCrno(),
                    chatMsgInstance.getSenderNo(),
                    chatMsgInstance.getSenderName(),
                    chatMsgInstance.getMessage()
            );
        } catch(Exception ex) {

        }

    }

    // Getter/Setter
    //   1. Getter
    public String getTxtMember() { return "[" + txtMember + "]"; }
    public String getTxtMsg() { return txtMsg; }
    public ChatMsg getChatMsgInstance() { return chatMsgInstance; }

    //   2. Setter
    public void setTxtMember(String txtMember) { this.txtMember = txtMember; }
    public void setTxtMsg(String txtMsg) { this.txtMsg = txtMsg; }
    public void setChatMsgInstance(ChatMsg chatMsgInstance) {
        this.chatMsgInstance = null;
        this.chatMsgInstance = new ChatMsg(chatMsgInstance);
    }
}
