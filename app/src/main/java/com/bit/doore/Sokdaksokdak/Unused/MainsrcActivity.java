package com.bit.doore.Sokdaksokdak.Unused;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.bit.doore.Sokdaksokdak.Adapter.SearchResultAdapter;
import com.bit.doore.Sokdaksokdak.Constants;
import com.bit.doore.Sokdaksokdak.DB.DatabaseHandler;
import com.bit.doore.Sokdaksokdak.DTO.SearchResult;
import com.bit.doore.Sokdaksokdak.R;

import java.util.ArrayList;

public class MainsrcActivity extends AppCompatActivity {
    ArrayList<SearchResult> arResult;
    Button btnSrc;
    EditText edtvSrc;
    SearchResultAdapter srAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainsrc);
        setTitle(Constants.MAINSRC_TITLE);

        arResult = new ArrayList<>();
        edtvSrc = (EditText) findViewById(R.id.mainSrc_edt_search);
        btnSrc = (Button) findViewById(R.id.mainSrc_btn_search);
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        int myMno = Integer.parseInt(db.getUserDetails().get("mNo").toString());

        btnSrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                       //검색버튼 리스너
                SearchResultAdapter.SearchTask searchTask = srAdapter.new SearchTask();  //Adapter내의 AsyncTask
                searchTask.execute(edtvSrc.getText().toString());
            }
        });

        srAdapter = new SearchResultAdapter(this,R.layout.search_result, arResult, myMno);

        ListView list = (ListView) findViewById(R.id.mainSrc_listView);
        list.setAdapter(srAdapter);

    }

}
