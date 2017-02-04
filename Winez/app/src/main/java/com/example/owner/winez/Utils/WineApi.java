package com.example.owner.winez.Utils;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.owner.winez.MyApplication;
import com.example.owner.winez.Utils.ApiClasses.WineApiClass;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URL;
import java.util.ArrayList;

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
     * @param GetOnCompliteResult - Result event
     */
    public void GetWinesByCategory(final GetResultOnRespons<WineApiClass> GetOnCompliteResult ) {

        String url = "http://services.wine.com/api/beta2/service.svc/JSON/catalog?sort=(rating|ascending)&size=100&apikey=" + this.apikey;
        final ArrayList<WineApiClass> answer = new ArrayList<>();

// Request a string response from the provided URL.
        //StringRequest stringRequest = new StringRequest(
                JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url,null,

                        new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        // Display the first 500 characters of the response string.
                        Log.d("Response:", "onResponse: this worked  +  " + response);
                        try {
                            JSONArray productsarray = response.getJSONObject("Products").getJSONArray("List");
                            for (int index = 0; index < productsarray.length(); index++){
                                WineApiClass newWine =  gson.fromJson(productsarray.get(index).toString(),WineApiClass.class);
                                newWine.setRating(productsarray.getJSONObject(index).getJSONObject("Ratings").get("HighestScore").toString());
                                answer.add(newWine);
                            }
                            GetOnCompliteResult.onResult(answer);

                        } catch (Exception e)
                        {
                            Log.d("Error:", "this is error parsing array = " + e.getMessage() );
                        }
                    }
                }, new Response.ErrorListener() {
                            @Override
            public void onErrorResponse(VolleyError error) {
                                Log.d("ERROR:", "Error lisiner on response : " + error.getMessage());
            }
        });

        this.queue.add(jor);
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

    public interface GetResultOnRespons<T>{
        public void onResult(ArrayList<T> data);
        public void onCancel();
    }
}
