package com.example.bit_user.bitchatting;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainsrcActivity extends AppCompatActivity {
    ArrayList<SearchResult> arResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainsrc);
        setTitle("대화상대 찾기");

        arResult = new ArrayList<SearchResult>();
        SearchResult sr;

        Button btnSrc= (Button)findViewById(R.id.mainSrc_btn_search);
        btnSrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "검색", Toast.LENGTH_SHORT).show();
            }
        });

        sr = new SearchResult("김동영");
        arResult.add(sr);
        sr = new SearchResult("이양우");
        arResult.add(sr);
        sr = new SearchResult("최장원");
        arResult.add(sr);

        SearchResultAdapter srAdapter = new SearchResultAdapter(this,
                R.layout.search_result, arResult);

        ListView list = (ListView)findViewById(R.id.mainSrc_listView);
        list.setAdapter(srAdapter);

        /*final String[] searchResult = {"김동영", "이양우", "최장원", "이동욱", "정재영", "강경미"};
        ListView list = (ListView)findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, searchResult);
        list.setAdapter(adapter);*/


    }
}

class SearchResult{
    String name;

    SearchResult(String name){
        this.name = name;
    }
}

class SearchResultAdapter extends BaseAdapter{
    Context mainCon;
    LayoutInflater inflater;
    ArrayList<SearchResult> arSrc;
    int layout;

    public SearchResultAdapter(Context context, int aLayout, ArrayList<SearchResult> aarSrc){
        mainCon = context;
        arSrc = aarSrc;
        layout = aLayout;
        inflater = LayoutInflater.from(mainCon);
    }

    public int getCount(){
        return arSrc.size();
    }
    public Object getItem(int position){
        return arSrc.get(position).name;
    }
    public long getItemId(int position){
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        final int pos = position;
        if(convertView == null){
            convertView = inflater.inflate(layout, parent, false);
        }
        TextView txt = (TextView)convertView.findViewById(R.id.search_result_name);
        txt.setText(arSrc.get(position).name);

        Button btn = (Button)convertView.findViewById(R.id.search_result_btn_add);
        btn.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                String str = arSrc.get(pos).name + " 추가";
                Toast.makeText(mainCon, str, Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }
}