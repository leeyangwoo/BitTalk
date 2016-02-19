package com.example.bit_user.bitchatting.DTO;

/**
 * Created by bit-user on 2016-02-13.
 */
public class ChatMember {
    // Fields
    private int mno;
    private String mid;
    private String mname;

    // Constructor
    public ChatMember(int mno, String mid, String mname) {
        this.mno = mno;
        this.mid = mid;
        this.mname = mname;
    }

    // Getter/Setter
    //  1. Getter
    public int getMno() { return mno; }
    public String getMid() { return mid; }
    public String getMname() { return mname; }
    //   2. Setter
    public void setMno(int mno) { this.mno = mno; }
    public void setMid(String mid) { this.mid = mid; }
    public void setMname(String mname) { this.mname = mname; }
}
