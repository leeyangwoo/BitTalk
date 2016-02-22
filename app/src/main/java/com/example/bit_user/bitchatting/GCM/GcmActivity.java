package com.example.bit_user.bitchatting.GCM;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.bit_user.bitchatting.Constants;
import com.example.bit_user.bitchatting.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * Created by bit-user on 2016-02-19.
 */
public class GcmActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(checkPlayServices()){

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPlayServices();
    }

    private boolean checkPlayServices(){
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if(resultCode != ConnectionResult.SUCCESS){
            if(GooglePlayServicesUtil.isUserRecoverableError(resultCode)){
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        Constants.PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }else{
                Log.i("PLAYSERVICES","NOT SUPPORTED");
                finish();
            }
            return false;
        }
        return true;
    }
}
