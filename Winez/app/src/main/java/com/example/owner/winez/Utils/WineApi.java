package com.example.owner.winez.Utils;

import android.util.JsonReader;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.owner.winez.Model.Model;
import com.example.owner.winez.MyApplication;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WineApi {
    public final int RED_WINE = 124;
    public final int WHITE_WINE = 125;
    public final int CHAMPANGE_SPARKLINGS = 123;
    public final int ROSE_WINE = 126;
    public final int DESERET_WINE = 128;
    public final int SAKE_WINE = 134;
    private final String apikey = "5c27d5820f7f571efd0b10dad310fcb7";
    private RequestQueue queue;


    private static WineApi targetURL;
    private URL wines_url;

    private WineApi() {
        this.queue = Volley.newRequestQueue(MyApplication.getAppContext());
    }

    public static WineApi getInstance() {
        if (targetURL == null) {
            targetURL = new WineApi();
        }
        return targetURL;
    }

    /**
     * @param category - the id of the category selected
     */
    public void GetWinesByCategory(int category) {

        String url = "http://services.wine.com/api/beta2/service.svc/JSON/catalog?sort=(rating|ascending)&size=100&apikey=" + this.apikey;

// Request a string response from the provided URL.
        //StringRequest stringRequest = new StringRequest(
                JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        Log.d("Response:", "onResponse: this worked  +  " + response);
                    }
                }, new Response.ErrorListener() {
                            @Override
            public void onErrorResponse(VolleyError error) {
                                Log.d("ERROR:", "Error lisiner on response : " + error.getMessage());
            }
        });

        this.queue.add(jor);

//        try {
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            // conn.
//            conn.setRequestProperty("Accept", "application/json");
//
//            if (conn.getResponseCode() != 200) {
//                throw new RuntimeException("Failed : HTTP error code : "
//                        + conn.getResponseCode());
//            }
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(
//                    (conn.getInputStream())));
//
//            String output;
//            System.out.println("Output from Server .... \n");
//            while ((output = br.readLine()) != null) {
//                System.out.println(output);
//            }
//
//            conn.disconnect();
//
//        } catch (MalformedURLException e) {
//
//            e.printStackTrace();
//
//        } catch (IOException e) {
//
//            e.printStackTrace();
//        }
    }

    public int getRED_WINE() {
        return RED_WINE;
    }

    public int getWHITE_WINE() {
        return WHITE_WINE;
    }

    public int getCHAMPANGE_SPARKLINGS() {
        return CHAMPANGE_SPARKLINGS;
    }

    public int getROSE_WINE() {
        return ROSE_WINE;
    }

    public int getDESERET_WINE() {
        return DESERET_WINE;
    }

    public int getSAKE_WINE() {
        return SAKE_WINE;
    }
}
