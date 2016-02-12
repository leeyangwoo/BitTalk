package com.example.bit_user.bitchatting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class JoinActivity extends AppCompatActivity {

    Button btn1;
    Button btn2;
    EditText edtText1,edtText2,edtText3,edtText4;
    JSONObject responseJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        btn1 = (Button)findViewById(R.id.join_btn1);
        btn2 = (Button)findViewById(R.id.join_btn2);
        edtText1 = (EditText)findViewById(R.id.join_edtText1);
        edtText2 = (EditText)findViewById(R.id.join_edtText2);
        edtText3 = (EditText)findViewById(R.id.join_edtText3);
        edtText4 = (EditText)findViewById(R.id.join_edtText4);


        edtText3.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == R.id.join_edtText3 || actionId == EditorInfo.IME_NULL) {
                    attemptJoin();
                    return true;

                }


                return false;
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(attemptJoin()==false) {
                    new Thread() {
                        @Override
                        public void run() {

                            HttpURLConnection conn = null;
                            try {
                                URL url = new URL("http://192.168.1.35/BitTalkServer/join.jsp"); //요청 URL을 입력
                                conn = (HttpURLConnection) url.openConnection();


                                conn.setRequestMethod("POST"); //요청 방식을 설정 (default : GET)

                                conn.setDoInput(true); //input을 사용하도록 설정 (default : true)
                                conn.setDoOutput(true); //output을 사용하도록 설정 (default : false)

                                conn.setConnectTimeout(600); //타임아웃 시간 설정 (default : 무한대기)

                                OutputStream os = conn.getOutputStream();
                                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8")); //캐릭터셋 설정

                                writer.write("mid=" + edtText1.getText().toString() + "&mpasswd=" + edtText3.getText().toString()
                                        + "&mname=" + edtText2.getText().toString()); //요청 파라미터를 입력
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


                                System.out.println("response:" + responseJSON.toString());
                                System.out.println((String) responseJSON.get("result"));


                                Handler h = new Handler(Looper.getMainLooper());
                                h.post(new Runnable() {
                                    public void run() {
                                        try {


                                            if (responseJSON.get("result").equals("success")) {

                                                Toast.makeText(getApplicationContext(), "Successfully Logged In!",
                                                        Toast.LENGTH_SHORT).show();

                                                Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);
                                                startActivity(myIntent);

                                            } else {
                                                Toast.makeText(getApplicationContext(), "ID is already exist", Toast.LENGTH_SHORT).show();
                                                edtText1.setText("");
                                                edtText3.setText("");
                                                edtText4.setText("");
                                                edtText1.requestFocus();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });



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

   /*       Intent myIntent = new Intent(getApplicationContext(),LoginActivity.class);
           startActivity(myIntent);*/
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtText1.setText("");
                edtText2.setText("");
                edtText3.setText("");
                edtText4.setText("");
                edtText1.requestFocus();
            }
        });
    }

    private boolean attemptJoin() {

        // Reset errors.
        edtText1.setError(null);
        edtText3.setError(null);

        // Store values at the time of the login attempt.
        String id = edtText1.getText().toString();
        String password1 = edtText3.getText().toString();
        String password2 = edtText4.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password1) && !isPasswordValid(password1)) {
            edtText3.setError(getString(R.string.error_invalid_password));
            focusView = edtText3;
            cancel = true;
        }

        if(isPasswordCheck(password1,password2)==false){
            edtText3.setError(getString(R.string.error_confirm_password));
            focusView=edtText3;
            cancel=true;

        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(id)) {
            edtText1.setError(getString(R.string.error_field_required));
            focusView = edtText1;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }

        return cancel;
    }

    private boolean isPasswordValid(String password1) {
        //TODO: Replace this with your own logic
        return password1.length() > 4;
    }
    private boolean isPasswordCheck(String password1,String password2){
        if(password1.equals(password2))
            return true;
        else
            return false;
    }


}