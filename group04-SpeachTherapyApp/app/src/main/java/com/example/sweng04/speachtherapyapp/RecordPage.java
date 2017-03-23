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
    String filename = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_page);
        RandomString randStringGenerator = new RandomString(6);
        filename = randStringGenerator.nextString();
        //TODO: add a check to make sure filename doesn't already exist in db
        //path for this should be in a settings file somewhere
        String filefullpath = getExternalFilesDir(null).getAbsolutePath() +"/" + filename + ".m4a";
        //Log.e(ExtAudioRecorder.class.getName(),filefullpath);
        //TODO Uncomment this
        //recordObject.setup(filefullpath);
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
            try {
                recordObject.stopRecording();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //TODO: Delete
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
            //TODO Uncomment this
            //recordObject.startRecording();
        }else{ // This is where the code to end the recording will be.
            //TODO Uncomment this
//            try {
//                recordObject.stopRecording();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            ImageView img = (ImageView) findViewById(R.id.start_rec);
            img.setImageResource(R.drawable.record_button);
            Button btn = (Button) findViewById(R.id.record);
            btn.setBackgroundColor(getResources().getColor(R.color.buttonColour));
            Intent intent = new Intent(this, EditRecording.class);
            intent.putExtra("FILENAME", filename); // passes the filename to the next page
            intent.putExtra("Key", "RecordPage");
            startActivity(intent);
            firstPress=true;
        }
    }
}