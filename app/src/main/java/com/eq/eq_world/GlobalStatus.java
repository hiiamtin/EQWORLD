package com.eq.eq_world;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import com.eq.eq_world.Model.CampUser;
import com.google.firebase.auth.FirebaseUser;


public class GlobalStatus {

    public static String myRoleInThisCamp="",myRole="",currentCamp="";
    public static CampUser myAccount;
    public final String VERSION = "003";

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

    public static void clearUserData(){
        myRoleInThisCamp = "";
        myRole="";
        currentCamp="";
        myAccount = null;
    }

}
