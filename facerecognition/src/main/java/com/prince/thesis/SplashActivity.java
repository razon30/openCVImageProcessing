package com.prince.thesis;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.prince.thesis.utils.NetworkAvailable;
import com.prince.thesis.utils.SharePreferenceSingleton;
import com.prince.thesis.utils.ShowNetworkError;

import org.opencv.cultoftheunicorn.marvel.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        if (!new NetworkAvailable().isNetworkAvailable(this)){

          //  if (!SharePreferenceSingleton.getInstance().getBoolean("login")){

                new ShowNetworkError(this);
                return;

         //   }
        }



        final Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, ActivityMain.class));
            }
        };

        handler.postDelayed(runnable, 2000);


    }
}
