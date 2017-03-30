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
        final DatabaseOperations db = new DatabaseOperations(this);
        int favCatID = db.getAllCategories().get(0).getId();
        // Show list of favourite recordings.
        // Will need to figure out how to get favourites ID.
        Intent intent = new Intent(this, Recordings.class);
        intent.putExtra("key","Categories");
        intent.putExtra("catID",favCatID);
        startActivity(intent);
    }

    public void edit (View view){
        Intent intent = new Intent(this, Edit.class);
        startActivity(intent);
    }



}

