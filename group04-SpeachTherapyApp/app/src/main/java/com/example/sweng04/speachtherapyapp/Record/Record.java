package com.example.sweng04.speachtherapyapp.Record;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sweng04.speachtherapyapp.R;

public class Record extends AppCompatActivity {
    ExtAudioRecorder extAudioRecorder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
    }

    public void startRecording(String recording_destination){
        extAudioRecorder = ExtAudioRecorder.getInstance(false); // Uncompressed recording (WAV)

        extAudioRecorder.setOutputFile( recording_destination );
        extAudioRecorder.prepare();
        extAudioRecorder.start();
    }

    private void stopRecording() throws Exception{
        extAudioRecorder.stop();
        extAudioRecorder.release();
    }
}
