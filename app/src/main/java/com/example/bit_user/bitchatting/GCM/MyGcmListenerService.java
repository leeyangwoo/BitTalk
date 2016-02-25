package com.example.bit_user.bitchatting.GCM;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.bit_user.bitchatting.Activity.MainActivity;
import com.example.bit_user.bitchatting.Constants;
import com.example.bit_user.bitchatting.DB.DatabaseHandler;
import com.example.bit_user.bitchatting.R;
import com.google.android.gms.gcm.GcmListenerService;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by saltfactory on 6/8/15.
 */
public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";

    /**
     *
     * @param from SenderID 값을 받아온다.
     * @param data Set형태로 GCM으로 받은 데이터 payload이다.
     */
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String title = data.getString("title");
        String message = data.getString("message");
        int crno = Integer.parseInt(data.getString("crno"));
        int senderNo = Integer.parseInt(data.getString("senderNo"));

        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Title: " + title);
        Log.d(TAG, "Message: " + message);

        // GCM으로 받은 메세지를 디바이스에 알려주는 sendNotification()을 호출한다.
        sendNotification(title, message, crno, senderNo);
    }


    /**
     * 실제 디바에스에 GCM으로부터 받은 메세지를 알려주는 함수이다. 디바이스 Notification Center에 나타난다.
     * @param title
     * @param message
     */
    private void sendNotification(String title, String message, int crno, int senderNo) {
        Intent intent = new Intent(this, MainActivity.class);
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.talk)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        if(db.existChatroom(crno) == 0){                  //삭제하고 채팅방레코드가 없는 경우
            try {                      //////////
                JSONObject responseJSON = getName(senderNo);
                if (responseJSON.get("result").equals("success")) {
                    db.addChatroom(crno, 2, responseJSON.get(Constants.KEY_MNAME).toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Intent pushIntent = new Intent(QuickstartPreferences.MAINCHAT_PUSH_RECEIVE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(pushIntent);
        db.addMessage(crno, senderNo, message);
    }
    private JSONObject getName(int mno){
        HttpURLConnection conn = null;
        JSONObject responseJSON = null;
        try{
            URL url = new URL(Constants.CHAT_SERVER_URL + Constants.JSP_WHO + "?" + Constants.KEY_MNO + "="+mno);
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while((line = br.readLine()) != null) {
                if (sb.length() > 0) {
                    sb.append("\n");
                }
                sb.append(line);
            }
            br.close();
            responseJSON = new JSONObject(sb.toString());
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(conn != null){
                conn.disconnect();
            }
        }

        return responseJSON;
    }
}