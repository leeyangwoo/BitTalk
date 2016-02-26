package com.bit.doore.Sokdaksokdak.Unused;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.bit.doore.Sokdaksokdak.Constants;
import com.bit.doore.Sokdaksokdak.R;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;


public class SocketchatActivity extends Activity {


    private int mno;
    private int crno;
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(Constants.NODE_SERVER_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        mno  = intent.getIntExtra(Constants.KEY_MNO,  0);
        crno = intent.getIntExtra(Constants.KEY_CRNO, 0);

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
