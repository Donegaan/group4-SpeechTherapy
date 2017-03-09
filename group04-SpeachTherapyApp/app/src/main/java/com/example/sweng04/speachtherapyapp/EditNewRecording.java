package com.example.sweng04.speachtherapyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class EditNewRecording extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_new_recording);

        ListView listView = (ListView) findViewById(R.id.edit_new_list); // Display Edit settings for new recording.

        String[] settingsArray = {"Name Recording", "Preview Recording", "Delete Recording", "Record Again", "Save Recording"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1,settingsArray);

        listView.setAdapter(adapter);

    }
}


