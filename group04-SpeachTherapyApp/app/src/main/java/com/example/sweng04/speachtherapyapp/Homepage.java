package com.example.sweng04.speachtherapyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class Homepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
//        Button recButton=(Button) findViewById(R.id.recButton);
//        int width = getResources().getDisplayMetrics().widthPixels;
//        int hei=getResources().getDisplayMetrics().heightPixels/4;
//        recButton.setLayoutParams(new RelativeLayout.LayoutParams(width,hei));
//        Button editButton=(Button) findViewById(R.id.editButton);
//        editButton.setLayoutParams(new RelativeLayout.LayoutParams(width,hei));
//        Button catButton=(Button) findViewById(R.id.allRecs);
//        catButton.setLayoutParams(new RelativeLayout.LayoutParams(width,hei));
//        Button favButton=(Button) findViewById(R.id.favButton);
//        favButton.setLayoutParams(new RelativeLayout.LayoutParams(width,hei));
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

