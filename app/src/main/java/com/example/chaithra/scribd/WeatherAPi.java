package com.example.chaithra.scribd;

import com.google.gson.JsonObject;

import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
public interface WeatherAPi {
    @GET("data/2.5/forecast?")
    Call<WeatherResponse> getCurrentWeatherData(@Query("q") String cityname, @Query("APPID") String app_id);

}
