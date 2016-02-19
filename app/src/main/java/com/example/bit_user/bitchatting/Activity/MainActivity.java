package com.example.bit_user.bitchatting.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

import com.example.bit_user.bitchatting.R;

/**
 * Created by bit-user on 2016-02-19.
 */
public class MainActivity extends Activity {

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

        FragmentManager fm = getFragmentManager();  // Fragment를 관리하기 위한 객체 생성
        FragmentTransaction fragmentTransaction = fm.beginTransaction();    // FragmentTransaction 열기
        fragmentTransaction.replace(R.id.ll_fragment, fr);  //  프래그먼트 옮겨오기
        fragmentTransaction.commit();   // 명령 commit
    }
}
