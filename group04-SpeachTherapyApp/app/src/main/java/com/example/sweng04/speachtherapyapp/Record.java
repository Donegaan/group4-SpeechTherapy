package com.example.sweng04.speachtherapyapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Record extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        Intent record = getIntent();
    }

    public void backToHome(View view){ // Back button
        Intent intent = new Intent(this,Homepage.class);
        startActivity(intent);
    }

    boolean firstPress=true;
    public void recording(View view){ // Changes the buttons appearance in the record activity. Code to begin recording will go here.
        if (firstPress) {
            firstPress=false;
            ImageView img = (ImageView) findViewById(R.id.start_rec);
            img.setImageResource(R.drawable.stop_button);
            Button btn = (Button) findViewById(R.id.record);
            btn.setBackgroundColor(Color.RED);
        }else{ // This is where the code to end the recording will be.
            
        }
    }


}
