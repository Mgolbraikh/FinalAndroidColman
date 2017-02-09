package com.example.owner.winez.Utils;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.owner.winez.MyApplication;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by owner on 09-Feb-17.
 */

public class Utils {

    private static Utils _instance;

    private Utils(){}

    Utils getInstance() {
        if(_instance == null){
            _instance = new Utils();
        }
        return _instance;
    }

    private boolean isNetworkAvailable(MyApplication context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public void hasActiveInternetConnection(MyApplication context,final CheckConnectivity connectivityLisenner) {
        if (isNetworkAvailable(context)) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                connectivityLisenner.onResult(urlc.getResponseCode() == 200);
            } catch (IOException e) {
                Log.e("Check", "Error checking internet connection", e);
            }
        } else {
            Log.d("Check", "No network available!");
        }
        connectivityLisenner.onResult(false);
    }

    public interface CheckConnectivity{
        Boolean onResult(boolean result);
    }
}

