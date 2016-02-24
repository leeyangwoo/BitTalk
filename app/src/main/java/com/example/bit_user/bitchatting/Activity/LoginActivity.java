package com.example.bit_user.bitchatting.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bit_user.bitchatting.Constants;
import com.example.bit_user.bitchatting.DB.DatabaseHandler;
import com.example.bit_user.bitchatting.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    private AutoCompleteTextView id;
    private EditText password;
    JSONObject responseJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        id = (AutoCompleteTextView) findViewById(R.id.login_edtText1);
        password = (EditText) findViewById(R.id.login_edtText2);

        Button mSignInButton = (Button) findViewById(R.id.login_btn1);
        Button mJoinInButton =(Button)findViewById(R.id.login_btn2);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoginTask loginTask = new LoginTask();
                loginTask.execute(id.getText().toString(), password.getText().toString());

            }
        });
        mJoinInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent joinIntent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(joinIntent);

            }
        });
    }

    class LoginTask extends AsyncTask<String, String, Void>{
        protected Void doInBackground(String... param){
            HttpURLConnection conn = null;
            try {
                URL url = new URL(Constants.CHAT_SERVER_URL + Constants.JSP_LOGIN); //요청 URL을 입력
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST"); //요청 방식을 설정 (default : GET)
                conn.setDoInput(true); //input을 사용하도록 설정 (default : true)
                conn.setDoOutput(true); //output을 사용하도록 설정 (default : false)
                conn.setConnectTimeout(1000); //타임아웃 시간 설정 (default : 무한대기)

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8")); //캐릭터셋 설정

                writer.write(Constants.KEY_MID + "=" + param[0] + "&" + Constants.KEY_MPASSWORD + "=" + param[1]); //요청 파라미터를 입력
                writer.flush();
                writer.close();
                os.close();
                Log.i("login",param[0]+param[1]);

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
                br.close();
                responseJSON = new JSONObject(sb.toString());

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {
                        try {

                            if(responseJSON.get("result").equals("success")){

                                Toast.makeText(getApplicationContext(), Constants.TOASTMSG_LOGIN_SUCCESS,
                                        Toast.LENGTH_SHORT).show();

                                DatabaseHandler db = new DatabaseHandler(getApplicationContext());

                                db.resetTables();


                                //유저를 한명 추가해서 세션처럼 사용함.
                                db.addUser(Integer.parseInt(responseJSON.getJSONObject(Constants.TABLE_LOGIN).get(Constants.KEY_MNO).toString()),
                                        (responseJSON.getJSONObject(Constants.TABLE_LOGIN).get(Constants.KEY_MID)).toString(),
                                        (responseJSON.getJSONObject(Constants.TABLE_LOGIN).get(Constants.KEY_MPASSWORD)).toString(),
                                        (responseJSON.getJSONObject(Constants.TABLE_LOGIN).get(Constants.KEY_MNAME)).toString());

                                //확인차 콘솔창에서 출력.
                                System.out.println(db.getUserDetails().values());

                                Intent myIntent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(myIntent);
                                finish();

                            }else{
                                Toast.makeText(getApplicationContext(), Constants.TOASTMSG_WRONG_PWD,
                                        Toast.LENGTH_SHORT).show();
                                id.setText("");
                                password.setText("");
                                id.requestFocus();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(conn != null) {
                    conn.disconnect();
                }
            }
            return null;
        }
        protected void onPostExecute(Void result){
            super.onPostExecute(result);

        }
    }



}