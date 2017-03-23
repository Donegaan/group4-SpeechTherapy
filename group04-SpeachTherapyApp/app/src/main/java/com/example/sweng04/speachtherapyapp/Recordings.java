package com.example.sweng04.speachtherapyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Recordings extends AppCompatActivity {
    List<String>recordings = new ArrayList<String>();
    //String[] test = {"Name Recording", "Preview Recording", "Delete Recording", "Record Again","Add to a Category", "Save Recording"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordings);

        ListView list = (ListView) findViewById(R.id.rec_list);

        recordings.add("Hello");
        recordings.add("Bye");

        recAdapter adapter = new recAdapter();
        list.setAdapter(adapter);

    }

    class recAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return recordings.size();
        }

        @Override
        public Object getItem(int position) {
            return recordings.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView==null) {
                convertView = getLayoutInflater().inflate(R.layout.recordings_row, parent, false);
            }
            TextView recording = (TextView) convertView.findViewById(R.id.rec_text);
            recording.setText(recordings.get(position));
            return convertView;
        }
    }
}
