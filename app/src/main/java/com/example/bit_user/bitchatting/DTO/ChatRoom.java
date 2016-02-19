package com.example.bit_user.bitchatting.DTO;

/**
 * Created by bit-user on 2016-02-12.
 */
public class ChatRoom {

    private int crno;
    private int nump;  // 참가자 수
    private String crName;

    public ChatRoom(){}
    public ChatRoom(int crno, int nump, String crName){
        this.crno = crno;
        this.nump = nump;
        this.crName = crName;
    }
    public ChatRoom(int crno, int nump){
        this.crno = crno;
        this.nump = nump;
    }

    // Getter/Setter
    public int getCrno() { return crno; }
    public int getNump() { return nump; }
    public String getCrName() { return crName; }
    //public int[] getArMno() { return arMno; }
    //public String getLastMsg() { return lastMsg; }
    public void setCrno(int crno) { this.crno = crno; }
    public void setNump(int participantNum) { this.nump = participantNum; }
    public void setCrName(String crName) { this.crName = crName; }

}
