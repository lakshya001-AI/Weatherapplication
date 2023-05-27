package com.example.weatherapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class mainpage extends AppCompatActivity {

    private TextView city,temperature,weathercondition,humidity,maxTemperature,minTemperature,pressure,wind;
    private ImageView imageView;
    private FloatingActionButton fab;

    LocationManager locationManager;
    LocationListener locationListener;
    double lat,lon;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mainpage);

        ActionBar actionBar = getSupportActionBar();


        ColorDrawable colorDrawable = new
                ColorDrawable(Color.BLACK);

        actionBar.setBackgroundDrawable(colorDrawable);


        city = findViewById(R.id.city_textview);
        weathercondition = findViewById(R.id.image_side_text);
        temperature = findViewById(R.id.image_side_temperature);
        humidity = findViewById(R.id.Humidityvalue);
        maxTemperature = findViewById(R.id.Maxtempvalue);
        minTemperature = findViewById(R.id.Mintempvalue);
        pressure = findViewById(R.id.Pressurevalue);
        wind = findViewById(R.id.Windvalue);


        imageView = findViewById(R.id.image);

        fab = findViewById(R.id.floatingActionButton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mainpage.this,secondpage.class);
                startActivity(i);

            }
        });

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {

                lat = location.getLatitude();
                lon = location.getLongitude();

                Log.e("lat : " , String.valueOf(lat));
                Log.e("lon : " , String.valueOf(lon));

                getWeatherData(lat,lon);




            }
        };

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);

        }

        else
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,500,50,locationListener);

        }





    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1 && permissions.length>0 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
        PackageManager.PERMISSION_GRANTED)
        {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,500,50,locationListener);


        }
    }

    public void getWeatherData(double lat, double lon){
        WeatherAPI weatherAPI = RetrofitWeather.getclient().create(WeatherAPI.class);
        Call<OpenweatherMap> call = weatherAPI.getWeatherWithLocation(lat,lon);

        call.enqueue(new Callback<OpenweatherMap>() {
            @Override
            public void onResponse(Call<OpenweatherMap> call, Response<OpenweatherMap> response) {
                city.setText(response.body().getName()+"  ,  "+response.body().getSys().getCountry());
                temperature.setText(response.body().getMain().getTemp()+" °F");
                weathercondition.setText(response.body().getWeather().get(0).getDescription());
                humidity.setText(" : "+response.body().getMain().getHumidity()+"%");
                maxTemperature.setText(" : "+response.body().getMain().getTempMax()+" °F");
                minTemperature.setText(" : "+response.body().getMain().getTempMin()+" °F");
                pressure.setText(" : "+response.body().getMain().getPressure());
                wind.setText(" : "+response.body().getWind().getSpeed());


                String iconCode = response.body().getWeather().get(0).getIcon();
                Picasso.get().load(" https://openweathermap.org/img/wn/"+iconCode+"@2x.png").
                        placeholder(R.drawable.ic_baseline_cloud_24).into(imageView);


            }

            @Override
            public void onFailure(Call<OpenweatherMap> call, Throwable t) {

            }
        });
    }
}