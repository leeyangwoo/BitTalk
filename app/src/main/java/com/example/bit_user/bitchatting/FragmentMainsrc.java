package com.example.bit_user.bitchatting;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by bit-user on 2016-02-19.
 */
public class FragmentMainsrc extends Fragment {

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
        int myMno = Integer.parseInt(db.getUserDetails().get("mNo").toString());

        btnSrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchResultAdapter.SearchTask searchTask = srAdapter.new SearchTask();  //Adapter내의 AsyncTask
                searchTask.execute(edtvSrc.getText().toString());
            }
        });

        srAdapter = new SearchResultAdapter(getActivity(), R.layout.search_result, arResult, myMno);

        ListView list = (ListView) view.findViewById(R.id.mainSrcFragment_listView);
        list.setAdapter(srAdapter);

        return view;
    }
}
