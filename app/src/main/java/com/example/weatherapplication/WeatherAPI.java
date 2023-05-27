package com.example.weatherapplication;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI {

    @GET("weather?appid=ef9b65b1d103459d87c77dcb76d66efa")
    Call<OpenweatherMap>getWeatherWithLocation(@Query("lat")double lat,@Query("lon")double lon);


    @GET("weather?appid=ef9b65b1d103459d87c77dcb76d66efa")
    Call<OpenweatherMap>getWeatherWithCityName(@Query("q")String name);

}
