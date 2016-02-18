package com.example.bit_user.bitchatting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;


public class MainActivity extends Activity {


    private int mno;
    private int crno;
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(Constants.CHAT_SERVER_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        mno  = intent.getIntExtra("mno",  0);
        crno = intent.getIntExtra("crno", 0);

        Log.i("getCrNo: ", String.valueOf(crno));
        Log.i("getMNo: ", String.valueOf(mno));

        mSocket.emit("create room", crno);
        Log.i("create room Method", "good");

        setContentView(R.layout.activity_main);
    }

    public int getCrno() {
        return crno;
    }

    public int getMno() {
        return mno;
    }

}
