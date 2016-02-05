package com.example.user.ex;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private AutoCompleteTextView mIdView;
    private EditText mPasswordView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mIdView = (AutoCompleteTextView) findViewById(R.id.id);
        mPasswordView = (EditText) findViewById(R.id.password);


        Button mSignInButton = (Button) findViewById(R.id.login_sign_in_button);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*                Intent mainsrc = new Intent(getApplicationContext(),
                        MainsrcActivity.class);
                startActivity(mainsrc);*/

            }
        });
        Button mJoinInButton = (Button)findViewById(R.id.login_sign_in_button1);
        mJoinInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent joinac = new Intent(getApplicationContext(),
                        JoinActivity.class);
                startActivity(joinac);*/

            }
        });

    }
}
