package com.example.sweng04.speachtherapyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class stopRecording extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_recording);
    }

    public void backToHome(View view){ // Back button
        Intent intent = new Intent(this,Homepage.class);
        startActivity(intent);
    }
}
