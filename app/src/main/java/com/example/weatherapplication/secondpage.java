package com.example.weatherapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class secondpage extends AppCompatActivity {

    private TextView  cityWeather,temperatureWeather,condition,
            humidityWeather,maxTemperatureWeather,minTemperatureWeather,pressureWeather,windWeather;
    private ImageView imageViewWeather;
    private Button search;
    private EditText editText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondpage);


        ActionBar actionBar = getSupportActionBar();


        ColorDrawable colorDrawable = new
                ColorDrawable(Color.BLACK);

        actionBar.setBackgroundDrawable(colorDrawable);


        cityWeather = findViewById(R.id.city_textview_secondpage);
        temperatureWeather = findViewById(R.id.Temperaturetextview_Secondpage_sideimage);
        condition = findViewById(R.id.textview_besideImage);
        humidityWeather = findViewById(R.id.humidity_value_secondpage);
        maxTemperatureWeather = findViewById(R.id.Maxvalue_value_secondpage);
        minTemperatureWeather = findViewById(R.id.Minvalue_value_secondpage);
        pressureWeather = findViewById(R.id.Pressure_value_secondpage);
        windWeather = findViewById(R.id.Wind_value_secondpage);


        imageViewWeather = findViewById(R.id.Imageview_Secondpage);

        search = findViewById(R.id.Search_Button);

        editText = findViewById(R.id.Edittext_for_city);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cityName = editText.getText().toString();

                getWeatherData(cityName);

                editText.setText("");





            }
        });

    }

    public void getWeatherData(String name){
        WeatherAPI weatherAPI = RetrofitWeather.getclient().create(WeatherAPI.class);
        Call<OpenweatherMap> call = weatherAPI.getWeatherWithCityName(name);

        call.enqueue(new Callback<OpenweatherMap>() {
            @Override
            public void onResponse(Call<OpenweatherMap> call, Response<OpenweatherMap> response) {

                if(response.isSuccessful() )
                {
                    cityWeather.setText(response.body().getName()+"  ,  "+response.body().getSys().getCountry());
                    temperatureWeather.setText(response.body().getMain().getTemp()+" °F");
                    condition.setText(response.body().getWeather().get(0).getDescription());
                    humidityWeather.setText(" : "+response.body().getMain().getHumidity()+"%");
                    maxTemperatureWeather.setText(" : "+response.body().getMain().getTempMax()+" °F");
                    minTemperatureWeather.setText(" : "+response.body().getMain().getTempMin()+" °F");
                    pressureWeather.setText(" : "+response.body().getMain().getPressure());
                    windWeather.setText(" : "+response.body().getWind().getSpeed());


                    String iconCode = response.body().getWeather().get(0).getIcon();
                    Picasso.get().load(" https://openweathermap.org/img/wn/"+iconCode+"@2x.png").
                            placeholder(R.drawable.ic_baseline_cloud_24).into(imageViewWeather);


                }
                else {
                    Toast.makeText(secondpage.this,"City not found , Please try again",Toast.LENGTH_LONG).show();

                }



            }

            @Override
            public void onFailure(Call<OpenweatherMap> call, Throwable t) {

            }
        });
    }
}