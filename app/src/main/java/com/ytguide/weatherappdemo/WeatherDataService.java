package com.ytguide.weatherappdemo;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class WeatherDataService {
    private static final String QUERY_GOT_CITY_ID = "https://www.metaweather.com/api/location/search/?query=";
    private static final String QUERY_FOT_WEATHER_BY_ID = "https://www.metaweather.com/api/location/";
    Context context;
    String cityID;
    public WeatherDataService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);

        void onResponse(String cityID);
    }
    public void getCityID(String cityName, VolleyResponseListener volleyResponseListener) {
        String url =QUERY_GOT_CITY_ID + cityName ;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                cityID = "";
                try {
                    JSONObject cityInfo = response.getJSONObject(0);
                    cityID = cityInfo.getString("woeid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //Toast.makeText(context, "City ID = " + cityID, Toast.LENGTH_SHORT).show();
                volleyResponseListener.onResponse(cityID);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error occured ", Toast.LENGTH_SHORT).show();
                volleyResponseListener.onError("Error occurred ");
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(request);

       // return cityID;

    }



    public void getCityForecastByID(String cityID) {
        List<WeatherReportModel> report = new ArrayList<>();
        String url = QUERY_FOT_WEATHER_BY_ID + cityID;
        //get the json object
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }


        );



                //get the property called 'consolodated_weather' (array)


        //get each item in array -> model

        MySingleton.getInstance(context).addToRequestQueue(request);

    }

//
//    public List<WeatherReportModel> getCityForecastByID(String cityID) {
//
//    }
}
