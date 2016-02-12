package com.example.bit_user.bitchatting;

/**
 * Created by bit-user on 2016-02-11.
 */
public class MainchatLvitem {
    private String crName;
    private String lastMsg;

    public MainchatLvitem(String crName, String lastMsg) {
        this.crName = crName;
        this.lastMsg = lastMsg;
    }

    public String getCrName() { return crName; }
    public String getLastMsg() { return lastMsg; }

    public void setCrName() { this.crName = crName; }
    public void setLastMsg() { this.lastMsg = lastMsg; }
}
