package com.example.bit_user.bitchatting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


/**
 * A login screen that offers login via username.
 */
public class Confirm extends AppCompatActivity {


    private String username;
    DatabaseHandler db;
//    private int roomNum;

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
        setContentView(R.layout.activity_confirm);
        
        attemptLogin();
        Log.i("Attempt Login: ","reached");
        mSocket.on("login", onLogin);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mSocket.off("login", onLogin);
    }

    /**
     * Attempts to sign in the account specified by the login form.
     * If there are form errors (invalid username, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        db = new DatabaseHandler(getApplicationContext());
        Log.i("userName: ",(db.getUserDetails().get("mName")).toString());

        username = (db.getUserDetails().get("mName")).toString();


        mSocket.emit("add user", username);
    }

    private Emitter.Listener onLogin = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];

            int numUsers;

            try {
                numUsers = data.getInt("numUsers");
            } catch (JSONException e) {
                return;
            }
            Intent intent = new Intent();
            intent.putExtra("username", username);
            intent.putExtra("numUsers", numUsers);
            Log.i("onLogin finished:","success");
            setResult(RESULT_OK, intent);
            finish();
        }
    };

}



