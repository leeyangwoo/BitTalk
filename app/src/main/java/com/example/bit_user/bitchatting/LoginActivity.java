package com.example.bit_user.bitchatting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

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
                Intent mainSrc = new Intent(getApplicationContext(),
                        MainSrcActivity.class);
                startActivity(mainSrc);

            }
        });
        Button mJoinInButton = (Button)findViewById(R.id.login_btn2);
        mJoinInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent join = new Intent(getApplicationContext(),
                        JoinActivity.class);
                startActivity(join);

            }
        });

    }
}
