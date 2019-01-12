package com.leonardo.weatherapp.Service;

import com.leonardo.weatherapp.model.List;
import com.leonardo.weatherapp.model.WeatherData;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface APIManager {

    @GET("forecast")
    Call<WeatherData> getWeatherInfo(
            @Query("q") String city,
            @Query("appid") String appid,
            @Query("units") String units
    );

    @GET("forecast")
    Call<WeatherData> getWeatherInfo(@QueryMap Map<String, String> parameters);
}
