package com.example.bit_user.bitchatting;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by bit-user on 2016-02-17.
 */
public class FragmentMainchat extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Test
        Toast.makeText(getActivity().getApplicationContext(), "onCreate() 실행됨", Toast.LENGTH_LONG).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_fragment_mainchat, container, false);

        // Fragment가 제대로 열렸는지 Test
        Toast.makeText(getActivity().getApplicationContext(), "onCreateView() 실행됨", Toast.LENGTH_LONG).show();

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();

        // Test
        Toast.makeText(getActivity().getApplicationContext(), "onPause() 실행됨", Toast.LENGTH_LONG).show();
    }
}
