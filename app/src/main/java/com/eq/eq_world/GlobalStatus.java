package com.eq.eq_world;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.eq.eq_world.Model.CampUser;


public class GlobalStatus {

    public static String myRoleInThisCamp="",myRole="",currentCamp="";
    public static CampUser myAccount;

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

}
