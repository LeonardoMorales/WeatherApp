package com.leonardo.weatherapp;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.leonardo.weatherapp.Adapters.ListaAdapter;
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
    private ListView list_list;
    private Switch swUnits;

    //Vars
    private APIManager apiManager;
    private String city;
    private String units = "metric";
    private Typeface weatherFont;
    private final static String PATH_TO_WEATHER_FOT = "fonts/weather.ttf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvWeather = findViewById(R.id.tv);
        etCity = findViewById(R.id.etCity);
        btn = findViewById(R.id.btn);
        list_list = findViewById(R.id.list_list);
        swUnits = findViewById(R.id.swUnits);

        weatherFont = Typeface.createFromAsset(getAssets(), PATH_TO_WEATHER_FOT);

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

                city = etCity.getText().toString();

                if(city.trim().isEmpty()){
                    Toast.makeText(Home.this, "Introduzca una ciudad", Toast.LENGTH_SHORT).show();
                } else {
                    getWeather(city, units);
                }

            }
        });

        swUnits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(swUnits.isChecked()){
                    units = "imperial";
                    getWeather(city, units);
                } else {
                    units = "metric";
                    getWeather(city, units);
                }
            }
        });

    }

    /*private void getWeather(String city) {
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

                final List<com.leonardo.weatherapp.model.List> lista = post.getList();

                //ArrayAdapter<com.leonardo.weatherapp.model.List> adapter = new ArrayAdapter<com.leonardo.weatherapp.model.List>(getApplication(), android.R.layout.simple_list_item_1, lista);
                ListaAdapter listaAdapter = new ListaAdapter(getApplication(), R.layout.lista_item, lista);

                String content = "";
                content += "Cod: "+ post.getCod() + "\n";
                content += "Message: " + post.getMessage() + "\n";
                content += "Cnt: " + post.getCnt() + "\n";
                content += "City: " + post.getCity() + "\n";

                list_list.setAdapter(listaAdapter);

                tvWeather.append(content);

                list_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        Toast.makeText(Home.this, "Posición: " + lista.get(position).getDtTxt(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                tvWeather.setText(t.getMessage());
            }
        });

    }*/

    private void getWeather(String city, String units) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("q", city);
        parameters.put("appid", "49a80dbd8a6453aa50d240c9c1425502");
        parameters.put("units", units);

        Call<WeatherData> call = apiManager.getWeatherInfo(parameters);

        call.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                if(!response.isSuccessful()){
                    tvWeather.setText("Code: " + response.code());
                    return;
                }

                WeatherData post = response.body();

                final List<com.leonardo.weatherapp.model.List> lista = post.getList();

                //ArrayAdapter<com.leonardo.weatherapp.model.List> adapter = new ArrayAdapter<com.leonardo.weatherapp.model.List>(getApplication(), android.R.layout.simple_list_item_1, lista);
                ListaAdapter listaAdapter = new ListaAdapter(getApplication(), R.layout.lista_item, lista, weatherFont);

                String content = "";
                content += "Cod: "+ post.getCod() + "\n";
                content += "Message: " + post.getMessage() + "\n";
                content += "Cnt: " + post.getCnt() + "\n";
                content += "City: " + post.getCity() + "\n";

                list_list.setAdapter(listaAdapter);

                tvWeather.setText("");
                tvWeather.append(content);

                list_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        Toast.makeText(Home.this, "Posición: " + lista.get(position).getDtTxt(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                tvWeather.setText(t.getMessage());
            }
        });
    }
}