package com.example.login;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.os.Bundle;

public class ConnectionDetector {

    Context context;

    public ConnectionDetector(Context context) {
        this.context = context;

    }
    public boolean isConnected(){
        ConnectivityManager connectivity = (ConnectivityManager)
                context.getSystemService(Service.CONNECTIVITY_SERVICE);
        if(connectivity !=null){
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if(info !=null){
                if(info.getState() == NetworkInfo.State.CONNECTED){
                    return true;
                }
            }

        }
        return false;
    }
}
