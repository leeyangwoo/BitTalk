package com.example.bit_user.bitchatting;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by bit-user on 2016-02-17.
 */
public class FragmentMainsrc extends Fragment implements OnClickListener {

    // Fields
    ArrayList<SearchResult> arResult;
    Button btnSrc;
    EditText edtvSrc;
    SearchResultAdapter srAdapter;
    int myMno;
    ListView list;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_mainsrc, container, false);

        arResult = new ArrayList<>();
        edtvSrc = (EditText) view.findViewById(R.id.mainSrcFragment_edt_search);
        btnSrc = (Button)view.findViewById(R.id.mainSrcFragment_btn_search);
        DatabaseHandler db = new DatabaseHandler(getActivity());
        myMno = Integer.parseInt(db.getUserDetails().get("mNo").toString());

        btnSrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "버튼 눌림", Toast.LENGTH_LONG).show();
            }
        });

        srAdapter = new SearchResultAdapter(getActivity(), R.layout.search_result, arResult, myMno);
        list = (ListView)view.findViewById(R.id.mainSrcFragment_listView);
        list.setAdapter(srAdapter);

        // Test
        //Toast.makeText(getActivity(), "onCreateView() 실행됨", Toast.LENGTH_LONG).show();

        return view;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getActivity(), "버튼 눌림", Toast.LENGTH_LONG).show();
    }
}
