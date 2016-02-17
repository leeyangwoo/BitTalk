package com.example.bit_user.bitchatting;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

/**
 * Created by bit-user on 2016-02-17.
 */
public class FragmentMain extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_main);
    }

    public void selectFrag(View view) {
        Fragment fr;

        if(view == findViewById(R.id.btnMainsrcFragment)) {
            fr = new FragmentMainsrc();
        } else {
            fr = new FragmentMainchat();
        }

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_place1, fr);
        fragmentTransaction.commit();
    }
}


