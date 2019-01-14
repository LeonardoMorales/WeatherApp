package com.leonardo.weatherapp.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.leonardo.weatherapp.R;
import com.leonardo.weatherapp.model.Main;
import com.leonardo.weatherapp.model.Weather;

import java.util.List;

public class ListaAdapter extends BaseAdapter {

    //Vars
    private Context context;
    private int layout;
    private List<com.leonardo.weatherapp.model.List> list;
    private List<Main> main;
    private List<Weather> weathers;
    private Typeface weather_font;

    public ListaAdapter(Context context, int layout, List<com.leonardo.weatherapp.model.List> list, Typeface weather_font) {
        this.context = context;
        this.layout = layout;
        this.list = list;
        this.weather_font = weather_font;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        View v = LayoutInflater.from(context).inflate(R.layout.lista_item, null);

        //Vars
        Integer currentHum = list.get(position).getMain().getHumidity();
        Double currentTemp = list.get(position).getMain().getTemp();
        String currentMain = list.get(position).getWeather().get(0).getMain();
        String currentDescription = list.get(position).getWeather().get(0).getDescription();
        String currentIcon = list.get(position).getWeather().get(0).getIcon();

        //UI Components
        TextView textViewTemp = (TextView) v.findViewById(R.id.tv_temp);
        TextView textViewHum = (TextView) v.findViewById(R.id.tv_hum);
        TextView textViewMain = (TextView) v.findViewById(R.id.tv_main);
        TextView textViewDesc = (TextView) v.findViewById(R.id.tv_description);
        TextView textViewPosition = (TextView) v.findViewById(R.id.tv_position);
        TextView textViewIcon = (TextView) v.findViewById(R.id.tv_icon);

        textViewTemp.setText("Temperatura: " + currentTemp);
        textViewHum.setText("Humedad: " + currentHum);
        textViewMain.setText("Main: " + currentMain);
        textViewDesc.setText("Description: " + currentDescription);
        textViewPosition.setText("" + position);
        textViewIcon.setTypeface(weather_font);

        switch (currentIcon){
            case "01d":
                textViewIcon.setText(R.string.wi_day_sunny);
                break;
            case "02d":
                textViewIcon.setText(R.string.wi_cloudy_gusts);
                break;
            case "03d":
                textViewIcon.setText(R.string.wi_cloud_down);
                break;
            case "10d":
                textViewIcon.setText(R.string.wi_day_rain_mix);
                break;
            case "11d":
                textViewIcon.setText(R.string.wi_day_thunderstorm);
                break;
            case "13d":
                textViewIcon.setText(R.string.wi_day_snow);
                break;
            case "01n":
                textViewIcon.setText(R.string.wi_night_clear);
                break;
            case "04d":
                textViewIcon.setText(R.string.wi_cloudy);
                break;
            case "04n":
                textViewIcon.setText(R.string.wi_night_cloudy);
                break;
            case "02n":
                textViewIcon.setText(R.string.wi_night_cloudy);
                break;
            case "03n":
                textViewIcon.setText(R.string.wi_night_cloudy_gusts);
                break;
            case "10n":
                textViewIcon.setText(R.string.wi_night_cloudy_gusts);
                break;
            case "11n":
                textViewIcon.setText(R.string.wi_night_rain);
                break;
            case "13n":
                textViewIcon.setText(R.string.wi_night_snow);
                break;
        }

        return v;

    }
}
