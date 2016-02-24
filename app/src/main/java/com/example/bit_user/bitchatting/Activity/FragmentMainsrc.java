package com.example.bit_user.bitchatting.Activity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.bit_user.bitchatting.Adapter.SearchResultAdapter;
import com.example.bit_user.bitchatting.DB.DatabaseHandler;
import com.example.bit_user.bitchatting.DTO.SearchResult;
import com.example.bit_user.bitchatting.R;

import java.util.ArrayList;

/**
 * Created by bit-user on 2016-02-19.
 */
public class FragmentMainsrc extends Fragment implements OnClickListener {

    // Fields
    private ArrayList<SearchResult> arResult;
    private Button btnSrc;
    private EditText edtvSrc;
    private SearchResultAdapter srAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_mainsrc, container, false);

        arResult = new ArrayList<>();
        edtvSrc = (EditText) view.findViewById(R.id.mainSrcFragment_edt_search);
        btnSrc = (Button) view.findViewById(R.id.mainSrcFragment_btn_search);
        DatabaseHandler db = new DatabaseHandler(getActivity());
        int myMno = Integer.parseInt(db.getUserDetails().get("mno").toString());

        btnSrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = edtvSrc.getText().toString();
                if(query.isEmpty()){
                    edtvSrc.requestFocus();
                    return;
                }
                srAdapter.new SearchTask().execute(edtvSrc.getText().toString());
            }
        });

        srAdapter = new SearchResultAdapter(getActivity(), R.layout.search_result, arResult, myMno);

        ListView list = (ListView) view.findViewById(R.id.mainSrcFragment_listView);
        list.setAdapter(srAdapter);

        return view;
    }

    @Override
    public void onClick(View v) {
        srAdapter.new SearchTask().execute(edtvSrc.getText().toString());
    }

}
