package com.example.weatherapplication;

//SO , WE ARE GOING TO MAKE THIS PROJECT BY USING THE RETROFIT LIBRARY //

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i  = new Intent(MainActivity.this,mainpage.class);
                startActivity(i);
                finish();
            }
        },5000);

        ActionBar actionBar = getSupportActionBar();


        ColorDrawable colorDrawable = new
                ColorDrawable(Color.BLACK);

        actionBar.setBackgroundDrawable(colorDrawable);


    }
}