package com.example.sweng04.speachtherapyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Homepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
    }

    public void makeRecording(View view){
        Intent intent = new Intent(this,RecordPage.class);
        startActivity(intent);
    }

    public void viewCategories(View view){
        Intent intent = new Intent(this, Categories.class);
        startActivity(intent);
    }

    public void viewFavourite(View view){
        // Show list of favourite recordings.
        startActivity(new Intent(this, Recordings.class));

    }

    public void edit (View view){
        Intent intent = new Intent(this, Edit.class);
        startActivity(intent);
    }



}

