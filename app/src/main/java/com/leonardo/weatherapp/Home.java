package com.leonardo.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.leonardo.weatherapp.Service.APIManager;
import com.leonardo.weatherapp.interfaces.HttpCallback;
import com.leonardo.weatherapp.model.WeatherData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Home extends AppCompatActivity {

    //UI Components
    private TextView tvWeather;
    private EditText etCity;
    private Button btn;

    //Vars
    private APIManager apiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvWeather = findViewById(R.id.tv);
        etCity = findViewById(R.id.etCity);
        btn = findViewById(R.id.btn);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        apiManager = retrofit.create(APIManager.class);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String city = etCity.getText().toString();

                if(city.trim().isEmpty()){
                    Toast.makeText(Home.this, "Introduzca una ciudad", Toast.LENGTH_SHORT).show();
                } else {
                    getWeather(city);
                }

            }
        });

    }

    private void getWeather(String city) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("q", city);
        parameters.put("appid", "49a80dbd8a6453aa50d240c9c1425502");
        parameters.put("units", "metric");

        Call<WeatherData> call = apiManager.getWeatherInfo(parameters);

        call.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                if(!response.isSuccessful()){
                    tvWeather.setText("Code: " + response.code());
                    return;
                }

                WeatherData post = response.body();

                String content = "";
                content += "Cod: "+ post.getCod() + "\n";
                content += "Message: " + post.getMessage() + "\n";
                content += "Cnt: " + post.getCnt() + "\n";
                content += "City: " + post.getCity() + "\n";
                content += "List: " + post.getList() + "\n\n";

                tvWeather.append(content);

            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                tvWeather.setText(t.getMessage());
            }
        });

    }
}
