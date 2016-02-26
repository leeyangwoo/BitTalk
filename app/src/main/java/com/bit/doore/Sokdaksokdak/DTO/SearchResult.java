package com.bit.doore.Sokdaksokdak.DTO;

/**
 * Created by bit-user on 2016-02-19.
 */
public class SearchResult {
    int mno;
    String name;                                     // 회원의 name만 표시

    public SearchResult(String name, int mno){
        this.name = name;
        this.mno = mno;
    }
    public int getMno(){ return this.mno; }
    public String getName() { return this.name; }
    public void setMno(int mno){  this.mno = mno;   }
    public void setName(String name){ this.name = name; }
}
