package com.example.sweng04.speachtherapyapp.Record;


import android.media.MediaRecorder;
import java.io.IOException;
import android.util.Log;


public class Record{
    private MediaRecorder recorder = null;
    private String filepath = null;

    public void setup(String recording_destination){
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        //recorder.setAudioEncoder(MediaRecorder.getAudioSourceMax());
        recorder.setAudioEncodingBitRate(16);
        recorder.setAudioSamplingRate(44100);
        filepath = recording_destination;
        recorder.setOutputFile(filepath);
        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e("Recording", "prepare() failed");
        }
    }
    public void startRecording(){
        recorder.start();
    }

    public void stopRecording() throws Exception{
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    public String getLocation(){
        return filepath;
    }
}
