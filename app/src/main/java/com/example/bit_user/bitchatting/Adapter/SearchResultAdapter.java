package com.example.bit_user.bitchatting.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.bit_user.bitchatting.Activity.ChatroomActivity;
import com.example.bit_user.bitchatting.Constants;
import com.example.bit_user.bitchatting.DTO.SearchResult;
import com.example.bit_user.bitchatting.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by bit-user on 2016-02-19.
 */
public class SearchResultAdapter extends BaseAdapter {
    Context mainCon;
    LayoutInflater inflater;
    ArrayList<SearchResult> arSrc;
    int layout;
    int myMno;


    public SearchResultAdapter(Context context, int aLayout, ArrayList<SearchResult> aarSrc, int amyMno){
        mainCon = context;
        arSrc = aarSrc;
        layout = aLayout;
        inflater = LayoutInflater.from(mainCon);
        myMno = amyMno;
    }

    public int getCount(){
        return arSrc.size();
    }
    public Object getItem(int position){
        return arSrc.get(position).getName();
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
        txt.setText(arSrc.get(position).getName());

        Button btn = (Button)convertView.findViewById(R.id.search_result_btn_add);
        btn.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                StartTalkTask startTalkTask = new StartTalkTask();
                startTalkTask.execute(myMno,arSrc.get(pos).getMno());
            }
        });
        return convertView;
    }

    class StartTalkTask extends AsyncTask<Integer, String, JSONObject> {
        protected JSONObject doInBackground(Integer... mno){
            HttpURLConnection conn = null;
            JSONObject responseJSON=null;
            try{                                               //GET방식
                URL url = new URL(Constants.CHAT_SERVER_URL + "talk.jsp?mno1="+mno[0]+"&mno2="+mno[1]);
                Log.i("talkURL", url.toString());
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

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(conn!=null){
                    conn.disconnect();
                }
            }
            return responseJSON;
        }
        protected void onPostExecute(JSONObject result){
            super.onPostExecute(result);
            try {
                if (result.get("result").equals("success")) {
                    Log.i("talkPOST", "success");
                    Intent i = new Intent(mainCon, ChatroomActivity.class);
                    i.putExtra("mno", Integer.parseInt(result.get("mno").toString()));
                    i.putExtra("crno", Integer.parseInt(result.get("crno").toString()));
                    ///////////////////////////////////////////////////////
                    if(result.get("detail").equals("exist")){
                        i.putExtra("detail", "exist");
                    }else if(result.get("detail").equals("new")){
                        i.putExtra("detail", "new");
                    }
                    ///////////////////////////////////////////////////////
                    mainCon.startActivity(i);
                } else {
                    Log.i("POST", "fail");
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    public class SearchTask extends AsyncTask<String, String, Void>{            // 검색 AsyncTask
        protected Void doInBackground(String... query){
            HttpURLConnection conn = null;
            JSONArray responseJSONarr;
            ArrayList<SearchResult> arResult = new ArrayList<>();
            SearchResult sr;
            try {                                                     //GET방식인데 POST로 바꿔야함
                URL url = new URL(Constants.CHAT_SERVER_URL + "search.jsp?mid="+query[0]); //요청 URL을 입력
                Log.i("searchURL", url.toString());
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

                for (int i = 0; i < responseJSONarr.length(); i++) {     //JSON array result에 추가
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
            SearchResultAdapter.this.notifyDataSetChanged();           //ResultSet을 갱신
        }
    }
}
