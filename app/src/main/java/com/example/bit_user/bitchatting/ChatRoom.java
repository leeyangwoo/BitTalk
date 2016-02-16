package com.example.bit_user.bitchatting;

/**
 * Created by bit-user on 2016-02-12.
 */
public class ChatRoom {
    //public final int MAX_PARTICIPANTS = 2;
    private int crno;
    //private int[] arMno;  // 참가자들의 회원번호를 배열에 저장
    private int nump;  // 참가자 수
    private String crName;
    //private String lastMsg;
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
    /*public ChatRoom(int crno, int[] arMno, int participantNum, String crName, String lastMsg) {

        // Fileds
        this.arMno = new int[MAX_PARTICIPANTS];
        this.crno = crno;
        this.crName = crName;
        this.lastMsg = lastMsg;
        this.participantNum = participantNum;
        try {
            if(participantNum > MAX_PARTICIPANTS || arMno.length > MAX_PARTICIPANTS)
                throw new Exception("오류: 불러올 참가자 목록의 레코드 수가 참가자 최대 수보다 많습니다.");
            for(int i = 0; i < MAX_PARTICIPANTS; i++) {
                if(i < arMno.length)
                    this.arMno[i] = arMno[i];
                else
                    this.arMno[i] = -1;
            }

        } catch(Exception ex) {

        }
    }*/

    // Getter/Setter
    public int getCrno() { return crno; }
    //public int[] getArMno() { return arMno; }
    public int getNump() { return nump; }
    public String getCrName() { return crName; }
    //public String getLastMsg() { return lastMsg; }
    public void setCrno(int crno) { this.crno = crno; }
    /*public void setArMno(int[] arMno ) {
        try {
            if(arMno.length > MAX_PARTICIPANTS) throw new Exception("오류: 불러올 참가자 목록의 레코드 수가 참가자 최대 수보다 많습니다.");
            for(int i = 0; i < arMno.length; i++) {
                this.arMno[i] = arMno[i];
            }
        } catch(Exception ex) {

        }
    }*/
    public void setNump(int participantNum) { this.nump = participantNum; }
    public void setCrName(String crName) { this.crName = crName; }
    //public void setLastMsg(String lastMsg) { this.lastMsg = lastMsg; }
}
