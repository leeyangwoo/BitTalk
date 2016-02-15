package com.example.bit_user.bitchatting;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
        setTitle("대화상대 찾기");

        arResult = new ArrayList<>();
        edtvSrc = (EditText) findViewById(R.id.mainSrc_edt_search);
        btnSrc = (Button) findViewById(R.id.mainSrc_btn_search);

        btnSrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                       //검색버튼 리스너
                SearchResultAdapter.SearchTask searchTask = srAdapter.new SearchTask();  //Adapter내의 AsyncTask
                searchTask.execute(edtvSrc.getText().toString());
            }
        });

        srAdapter = new SearchResultAdapter(this,R.layout.search_result, arResult);

        ListView list = (ListView) findViewById(R.id.mainSrc_listView);
        list.setAdapter(srAdapter);

    }
}

class SearchResult{                                  // 리스트뷰에 들어갈 클래스
    int mno;
    String name;                                     // 회원의 name만 표시

    SearchResult(String name, int mno){
        this.name = name;
        this.mno = mno;
    }
}

class SearchResultAdapter extends BaseAdapter{      //BaseAdapter를 상속받는 adapter
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

    public View getView(int position, View convertView, ViewGroup parent){    //getView
        final int pos = position;
        if(convertView == null){
            convertView = inflater.inflate(layout, parent, false);
        }
        TextView txt = (TextView)convertView.findViewById(R.id.search_result_name);
        txt.setText(arSrc.get(position).name);

        Button btn = (Button)convertView.findViewById(R.id.search_result_btn_add);
        btn.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                String str = arSrc.get(pos).name + arSrc.get(pos).mno + " 추가";
                Toast.makeText(mainCon, str, Toast.LENGTH_SHORT).show();
                StartTalkTask startTalkTask = new StartTalkTask();
                startTalkTask.execute(1,arSrc.get(pos).mno);
            }
        });
        return convertView;
    }

    class StartTalkTask extends AsyncTask<Integer, String, String>{
        protected String doInBackground(Integer... mno){
            HttpURLConnection conn = null;
            JSONObject responseJSON;
            String result="";
            try{                                               //GET방식
                URL url = new URL("http://192.168.1.35/BitTalkServer/talk.jsp?mno1="+mno[0]+"&mno2="+mno[1]);
                Log.i("URL", url.toString());
                conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

                StringBuilder sb = new StringBuilder();
                String line = null;
                while((line = br.readLine()) != null){
                    if(sb.length()>0){
                        sb.append("\n");
                    }
                    sb.append(line);
                }
                br.close();
                responseJSON = new JSONObject(sb.toString());
                result = responseJSON.get("result").toString();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(conn!=null){
                    conn.disconnect();
                }
            }
            return result;
        }
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            if(result.equals("success")){
                Log.i("POST","success");
                mainCon.startActivity(new Intent(mainCon, ChatroomActivity.class));
            }else{
                Log.i("POST","fail");
            }
        }
    }

    class SearchTask extends AsyncTask<String, String, Void>{            // 검색 AsyncTask
        protected Void doInBackground(String... query){
            HttpURLConnection conn = null;
            JSONArray responseJSONarr;
            ArrayList<SearchResult> arResult = new ArrayList<>();
            SearchResult sr;
            try {                                                     //GET방식인데 POST로 바꿔야함
                URL url = new URL("http://192.168.1.35/BitTalkServer/search.jsp?mid="+query[0]); //요청 URL을 입력
                Log.i("URL", url.toString());
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET"); //요청 방식을 설정 (default : GET)
                conn.connect();

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8")); //캐릭터셋 설정

                StringBuilder sb = new StringBuilder();          //Reader로 읽어옴
                String line = null;
                while ((line = br.readLine()) != null) {
                    if(sb.length() > 0) {
                        sb.append("\n");
                    }
                    sb.append(line);
                }
                br.close();
                responseJSONarr = new JSONArray(sb.toString());     //JSON array로 읽어옴
                Log.i("JSON",responseJSONarr.toString());

                for (int i = 0; i < responseJSONarr.length(); i++) {     //JSON array result에 추가
                    Log.i("FOR", responseJSONarr.getJSONObject(i).get("mname").toString());
                    sr = new SearchResult(responseJSONarr.getJSONObject(i).get("mname").toString(),
                                Integer.parseInt(responseJSONarr.getJSONObject(i).get("mno").toString()));
                    arResult.add(sr);
                }
                arSrc.clear();
                arSrc.addAll(arResult);


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(conn != null) {
                    conn.disconnect();
                }
            }
            return null;
        }

        protected void onPostExecute(Void result){
            super.onPostExecute(result);
            Log.i("PostExe", "POSTEXE");
            SearchResultAdapter.this.notifyDataSetChanged();           //ResultSet을 갱신
        }
    }
}