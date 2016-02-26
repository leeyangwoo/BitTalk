package com.bit.doore.Sokdaksokdak.DTO;

/**
 * Created by bit-user on 2016-02-12.
 */
public class ChatroomLvitem {

    // Fields
    private String senderName;
    private ChatMsg chatMsgInstance;

    // Constructor
    public ChatroomLvitem(ChatMsg chatMsgInstance){
        this.chatMsgInstance = chatMsgInstance;
    }

    // Getter/Setter
    //   1. Getter
    public String getSenderName() { return senderName; }
    public ChatMsg getChatMsgInstance() { return chatMsgInstance; }

    //   2. Setter
    public void setSenderName(String senderName){
        this.senderName = senderName;
        this.chatMsgInstance.setSenderName(this.senderName);
    }
    public void setChatMsgInstance(ChatMsg chatMsgInstance) {
        this.chatMsgInstance = null;
        this.chatMsgInstance = new ChatMsg(chatMsgInstance);
    }


}
