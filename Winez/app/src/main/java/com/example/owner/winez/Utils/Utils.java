package com.example.owner.winez.Utils;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.owner.winez.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

public class Utils {

    private static Utils _instance;
    private RequestQueue queue;
    private Utils(){ this.queue = Volley.newRequestQueue(MyApplication.getAppContext()); }

    public static Utils getInstance() {
        if(_instance == null){
            _instance = new Utils();
        }
        return _instance;
    }

//    private boolean isNetworkAvailable() {
//        ConnectivityManager connectivityManager
//                = (ConnectivityManager) this.queue.ger.getSystemService(context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        return activeNetworkInfo != null;
//    }

    public void hasActiveInternetConnection(final CheckConnectivity connectivityLisenner) {
        String url = "http://clients3.google.com/generate_200";

        // Request a string response from the provided URL.
        //StringRequest stringRequest = new StringRequest(
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getInt("status") == 0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                connectivityLisenner.onResult(true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        this.queue.add(jor);
    }

    public interface CheckConnectivity{
        Boolean onResult(boolean result);
    }
}

