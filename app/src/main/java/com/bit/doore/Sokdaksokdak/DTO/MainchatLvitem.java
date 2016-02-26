package com.bit.doore.Sokdaksokdak.DTO;

/**
 * Created by bit-user on 2016-02-11.
 */
public class MainchatLvitem {

    // Fields

    private String lastMsg;
    private String timestamp;
    private ChatRoom chatroomInstance;

    // Constructor
    public MainchatLvitem(ChatRoom chatroomInstance, String lastMsg, String timestamp){
        this.chatroomInstance = chatroomInstance;
        this.lastMsg = lastMsg;
        this.timestamp = timestamp;
    }

    // Getter/Setter
    //   1. Getter

    public String getLastMsg() { return lastMsg; }
    public ChatRoom getChatroomInstance() { return chatroomInstance; }
    public String getTimestamp() { return timestamp; }

    //   2. Setter
    public void setLastMsg(String lastMsg) { this.lastMsg = lastMsg; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

}
