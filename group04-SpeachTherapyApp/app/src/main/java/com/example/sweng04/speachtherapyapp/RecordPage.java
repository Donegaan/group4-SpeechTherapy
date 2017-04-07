package com.example.sweng04.speachtherapyapp;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioFormat;
import android.media.MediaRecorder;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.sweng04.speachtherapyapp.OmRecorder.AudioSource;
import com.example.sweng04.speachtherapyapp.OmRecorder.PullTransport;
import com.example.sweng04.speachtherapyapp.OmRecorder.Recorder;
import com.example.sweng04.speachtherapyapp.OmRecorder.OmRecorder;
import com.example.sweng04.speachtherapyapp.OmRecorder.AudioChunk;
import java.io.File;

public class RecordPage extends AppCompatActivity {
    Recorder recorder;
    String filename = "";

    private AudioSource mic() {
        return new AudioSource.Smart(MediaRecorder.AudioSource.MIC, AudioFormat.ENCODING_PCM_16BIT,
                AudioFormat.CHANNEL_IN_MONO, 44100);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_page);
        RandomString randStringGenerator = new RandomString(6);
        filename = randStringGenerator.nextString();
        //TODO: add a check to make sure filename doesn't already exist in db
        //path for this should be in a settings file somewhere
        String filefullpath = getExternalFilesDir(null).getAbsolutePath() +"/" + filename + ".wav";
        //Log.e(ExtAudioRecorder.class.getName(),filefullpath);
        //TODO Uncomment this
        recorder = OmRecorder.wav(
                new PullTransport.Default(mic(), new PullTransport.OnAudioChunkPulledListener() {
                    @Override public void onAudioChunkPulled(AudioChunk audioChunk) {}}), new File(filefullpath));
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


    boolean firstPress=true;
    public void recording(View view){
        // Changes the buttons appearance in the record activity. Code to begin recording will go here.
        if (firstPress) {
            firstPress=false;
            ImageView img = (ImageView) findViewById(R.id.start_rec);
            img.setImageResource(R.drawable.stop_button);
            Button btn = (Button) findViewById(R.id.record);
            btn.setBackgroundColor(Color.RED);
            btn.setText("Tap to Stop Recording");
            //TODO Uncomment this
            recorder.startRecording();
        }else{ // This is where the code to end the recording will be.
            //TODO Uncomment this
            try {
                recorder.stopRecording();
            } catch (Exception e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(this, EditRecording.class);
            intent.putExtra("FILENAME", filename); // passes the filename to the next page
            intent.putExtra("Key", "RecordPage");
            startActivity(intent);
            ImageView img = (ImageView) findViewById(R.id.start_rec);
            img.setImageResource(R.drawable.record_button);
            Button btn = (Button) findViewById(R.id.record);
            btn.setBackgroundColor(getResources().getColor(R.color.buttonColour));
            btn.setText("Tap to Record");
            firstPress=true;
        }
    }
}