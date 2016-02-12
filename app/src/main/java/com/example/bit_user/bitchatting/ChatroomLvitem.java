package com.example.bit_user.bitchatting;

/**
 * Created by bit-user on 2016-02-12.
 */
public class ChatroomLvitem {
    private String txtMember;
    private String txtMsg;
    private ChatMsg chatMsgInstance;

    public ChatroomLvitem(ChatMsg chatMsgInstance) {
        //this.txtMember = txtMember;
        //this.txtMsg = txtMsg;
        //this.chatMsgInstance = chatMsgInstance;
        this.chatMsgInstance = new ChatMsg(chatMsgInstance);
        this.txtMember = chatMsgInstance.getSenderName();
        this.txtMsg = chatMsgInstance.getMessage();
    }

    public String getTxtMember() { return "[" + txtMember + "]"; }
    public String getTxtMsg() { return txtMsg; }
    public ChatMsg getChatMsgInstance() { return chatMsgInstance; }
    public void setTxtMember(String txtMember) { this.txtMember = txtMember; }
    public void setTxtMsg(String txtMsg) { this.txtMsg = txtMsg; }
    public void setChatMsgInstance(ChatMsg chatMsgInstance) {
        this.chatMsgInstance = null;
        this.chatMsgInstance = new ChatMsg(chatMsgInstance);
    }

}
