package com.example.bit_user.bitchatting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by bit-user on 2016-02-16.
 */
public class SplashActivity extends Activity {
    int SPLASH_TIME = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());

                if(db.getUserDetails().size()>0) {
                    overridePendingTransition(0, android.R.anim.fade_in);
                    startActivity(new Intent(SplashActivity.this, MainsrcActivity.class));
                    finish();
                }else if(db.getUserDetails().size()==0){
                    overridePendingTransition(0, android.R.anim.fade_in);
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }
        },SPLASH_TIME);
    }
}
