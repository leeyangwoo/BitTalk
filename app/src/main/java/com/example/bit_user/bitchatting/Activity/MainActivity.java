package com.example.bit_user.bitchatting.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;

import com.example.bit_user.bitchatting.DB.DatabaseHandler;
import com.example.bit_user.bitchatting.GCM.QuickstartPreferences;
import com.example.bit_user.bitchatting.GCM.RegistrationIntentService;
import com.example.bit_user.bitchatting.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by bit-user on 2016-02-19.
 */
public class MainActivity extends Activity {

    DatabaseHandler db;
    String myMno;

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_main);

        registBroadcastReceiver();
        getInstanceIdToken();
        Fragment fr = new FragmentMainchat();
        FragmentManager fm = getFragmentManager();  // Fragment를 관리하기 위한 객체 생성
        FragmentTransaction fragmentTransaction = fm.beginTransaction();    // FragmentTransaction 열기
        fragmentTransaction.replace(R.id.ll_fragment, fr);  //  프래그먼트 옮겨오기
        fragmentTransaction.commit();   // 명령 commit
    }

    public void selectFrag(View view) {
        Fragment fr = new FragmentMainchat();

        if(view == findViewById(R.id.btnMainsrcFragment)) {
            fr = new FragmentMainsrc();
        } else {
            fr = new FragmentMainchat();
        }

        FragmentManager fm = getFragmentManager();  // Fragment를 관리하기 위한 객체 생성
        FragmentTransaction fragmentTransaction = fm.beginTransaction();    // FragmentTransaction 열기
        fragmentTransaction.replace(R.id.ll_fragment, fr);  //  프래그먼트 옮겨오기
        fragmentTransaction.commit();   // 명령 commit
    }

    public void getInstanceIdToken() {
        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i("SplashActivity: ", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    public void registBroadcastReceiver(){
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(action.equals(QuickstartPreferences.REGISTRATION_READY)){
                    // 액션이 READY일 경우

                } else if(action.equals(QuickstartPreferences.REGISTRATION_GENERATING)){
                    // 액션이 GENERATING일 경우

                } else if(action.equals(QuickstartPreferences.REGISTRATION_COMPLETE)){
                    // 액션이 COMPLETE일 경우
                    db = new DatabaseHandler(getApplicationContext());

                    final String token = intent.getStringExtra("token");
                    myMno =  db.getUserDetails().get("mno").toString();

                    new Thread() {
                        @Override
                        public void run() {

                            HttpURLConnection conn = null;
                            try {

                                URL url = new URL("http://192.168.1.35/BitTalkServer/settoken.jsp"); //요청 URL을 입력
                                conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("POST"); //요청 방식을 설정 (default : GET)
                                conn.setDoInput(true); //input을 사용하도록 설정 (default : true)
                                conn.setDoOutput(true); //output을 사용하도록 설정 (default : false)
                                conn.setConnectTimeout(600); //타임아웃 시간 설정 (default : 무한대기)

                                OutputStream os = conn.getOutputStream();
                                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8")); //캐릭터셋 설정

                                writer.write("mno=" + myMno + "&mtoken=" + token); //요청 파라미터를 입력
                                writer.flush();
                                writer.close();
                                os.close();
                                conn.connect();

                                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8")); //캐릭터셋 설정
                                StringBuilder sb = new StringBuilder();
                                String line = null;
                                while ((line = br.readLine()) != null) {
                                    if (sb.length() > 0) {
                                        sb.append("\n");
                                    }
                                    sb.append(line);
                                }
                                final JSONObject responseJSON = new JSONObject(sb.toString());

                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                if (conn != null) {
                                    conn.disconnect();
                                }
                            }
                        }
                    }.start();
                }
            }
        };
    }

    /**
     * 앱이 실행되어 화면에 나타날때 LocalBoardcastManager에 액션을 정의하여 등록한다.
     */
    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_READY));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_GENERATING));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));

    }

    /**
     * 앱이 화면에서 사라지면 등록된 LocalBoardcast를 모두 삭제한다.
     */
    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }




}
