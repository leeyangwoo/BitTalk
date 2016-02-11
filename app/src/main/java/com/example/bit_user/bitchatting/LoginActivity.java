package com.example.bit_user.bitchatting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        id = (AutoCompleteTextView) findViewById(R.id.login_edtText1);
        password = (EditText) findViewById(R.id.login_edtText2);


        Button mSignInButton = (Button) findViewById(R.id.login_btn1);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new Thread(){
                    @Override
                    public void run() {
                        HttpURLConnection conn = null;
                        try {
                            URL url = new URL("http://192.168.1.35/BitTalkServer/login.jsp"); //요청 URL을 입력
                            conn = (HttpURLConnection) url.openConnection();


                            conn.setRequestMethod("POST"); //요청 방식을 설정 (default : GET)

                            conn.setDoInput(true); //input을 사용하도록 설정 (default : true)
                            conn.setDoOutput(true); //output을 사용하도록 설정 (default : false)

                            conn.setConnectTimeout(60); //타임아웃 시간 설정 (default : 무한대기)

                            OutputStream os = conn.getOutputStream();
                            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8")); //캐릭터셋 설정

                            writer.write("mid=" + id.getText().toString() + "&mpasswd=" + password.getText().toString()); //요청 파라미터를 입력
                            writer.flush();
                            writer.close();
                            os.close();

                            conn.connect();

                            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8")); //캐릭터셋 설정

                            StringBuilder sb = new StringBuilder();
                            String line = null;
                            while ((line = br.readLine()) != null) {
                                if(sb.length() > 0) {
                                    sb.append("\n");
                                }
                                sb.append(line);
                            }
                            JSONObject responseJSON = new JSONObject(sb.toString());



                            System.out.println(responseJSON.get("result"));

                            if(responseJSON.get("result").equals("success")){

                            }else{
                                Toast.makeText(getApplicationContext(),"Incorrect Id and Password",
                                        Toast.LENGTH_SHORT).show();
                                id.setText("");
                                password.setText("");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if(conn != null) {
                                conn.disconnect();
                            }
                        }


                    }
                }.start();


            }
        });
    }


}
