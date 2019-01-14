package com.leonardo.weatherapp.interfaces;


import com.leonardo.weatherapp.model.Weather;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HttpCallback {

    //public void onSucess(Weather response);

    //public void onFailure(String error);

    public void getWeather(String city, String units);
}
