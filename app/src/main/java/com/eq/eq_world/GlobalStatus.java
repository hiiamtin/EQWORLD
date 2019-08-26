package com.eq.eq_world;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class GlobalStatus {

    public static String currentUid, currentUsername, currentCamp, currentImg;

    public static boolean isConnectedToNet(Context context){
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean isConnect = false;
        if(connectivityManager != null){
            NetworkInfo active = connectivityManager.getActiveNetworkInfo();
            isConnect = (active != null)&&(active.isConnectedOrConnecting());
        }
        return isConnect;
    }

    public void clearAll(){
        this.currentUid = "";
        this.currentUsername = "";
        this.currentCamp = "";
    }
}
