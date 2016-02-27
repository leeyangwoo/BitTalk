package com.bit.doore.Sokdaksokdak.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bit.doore.Sokdaksokdak.DB.DatabaseHandler;
import com.bit.doore.Sokdaksokdak.R;

/**
 * Created by bit-user on 2016-02-16.
 */
public class SplashActivity extends Activity {

    private Thread splashThread;

    public void onAttachedToWindow(){
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        startAnimations();

    }
    private void startAnimations(){

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        RelativeLayout l=(RelativeLayout) findViewById(R.id.splash_linear);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.imageView);
        iv.clearAnimation();
        iv.startAnimation(anim);

        splashThread = new Thread(){
            @Override
            public void run() {
                try {
                    DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 2500) {
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent = null;
                    if(db.getUserDetails().size() > 0){
                        intent = new Intent(SplashActivity.this,
                                MainActivity.class);
                    }else if(db.getUserDetails().size() ==0){
                        intent = new Intent(SplashActivity.this,
                                LoginActivity.class);
                    }
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    SplashActivity.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    SplashActivity.this.finish();
                }
            }
        };
        splashThread.start();
    }


}
