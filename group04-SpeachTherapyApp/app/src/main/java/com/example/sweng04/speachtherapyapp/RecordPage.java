package com.example.sweng04.speachtherapyapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.sweng04.speachtherapyapp.Record.Record;

public class RecordPage extends AppCompatActivity {

    Record recordObject = new Record();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_page);
        //path for this should be in a settings file somewhere
        //and filename randomly generated
//        String filefullpath = getExternalFilesDir(null).getAbsolutePath() +"/lol.m4a";
//        Log.e(ExtAudioRecorder.class.getName(),filefullpath);
//        recordObject.setup(filefullpath);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override // Goes to parent activity - Back button
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void backToHome(View view){ // Back button
        if (!firstPress){
            //end recording and delete it.
        }
        Intent intent = new Intent(this,Homepage.class);
        startActivity(intent);
    }

    boolean firstPress=true;
    public void recording(View view){
        // Changes the buttons appearance in the record activity. Code to begin recording will go here.
        if (firstPress) {
            firstPress=false;
            ImageView img = (ImageView) findViewById(R.id.start_rec);
            img.setImageResource(R.drawable.stop_button);
            Button btn = (Button) findViewById(R.id.record);
            btn.setBackgroundColor(Color.RED);
            //recordObject.startRecording();
        }else{ // This is where the code to end the recording will be.
            try {
                recordObject.stopRecording();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(this, EditRecording.class);
            startActivity(intent);
            firstPress=true;
        }
    }


}
