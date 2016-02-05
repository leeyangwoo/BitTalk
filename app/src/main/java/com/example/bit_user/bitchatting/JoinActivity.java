package com.example.bit_user.bitchatting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class JoinActivity extends AppCompatActivity {

    Button btn1;
    Button btn2;
    EditText edtText1,edtText2,edtText3,edtText4;

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


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(myIntent);
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
}
