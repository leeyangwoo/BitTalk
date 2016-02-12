package com.example.bit_user.bitchatting;

/**
 * Created by bit-user on 2016-02-12.
 */
public class ChatRoom {
    int crno;
    int mno1;
    int mno2;
    String crName;
    String lastMsg;

    public ChatRoom(int crno, int mno1, int mno2, String crName, String lastMsg) {
        this.crno = crno;
        this.mno1 = mno1;
        this.mno2 = mno2;
        this.crName = crName;
        this.lastMsg = lastMsg;
    }

    public int getCrno() { return crno; }
    public int getMno1() { return mno1; }
    public int getMno2() { return mno2; }
    public String getCrName() { return crName; }
    public String getLastMsg() { return lastMsg; }
    public void setCrno(int crno) { this.crno = crno; }
    public void setMno1(int mno1) { this.mno1 = mno1; }
    public void setMno2(int mno2) { this.mno2 = mno2; }
    public void setCrName(String crName) { this.crName = crName; }
    public void setLastMsg(String lastMsg) { this.lastMsg = lastMsg; }
}