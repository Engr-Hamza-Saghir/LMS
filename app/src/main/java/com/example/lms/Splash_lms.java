package com.example.lms;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class Splash_lms extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_lms);
        getSupportActionBar().hide();
        ProgressBar progress=(ProgressBar)findViewById(R.id.progress_bar);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                startActivity(new Intent(Splash_lms.this, MainActivity.class));
                finish();
            }
        },3000);
    }
}