package com.example.sweng04.speachtherapyapp;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Edit extends AppCompatActivity implements AdapterView.OnItemClickListener {
    List<String> categories = new ArrayList<String>();
    boolean editCats = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ListView list = (ListView) findViewById(R.id.edit_list);

        categories.add("Hello");
        categories.add("Bye");

        MyAdapter adapter = new MyAdapter();
        list.setAdapter(adapter);
        //list.setOnItemClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return categories.size();
        }

        @Override
        public Object getItem(int position) {
            return categories.get(position);
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
            recording.setText(categories.get(position));
            return convertView;
        }
    }

    public void editCatPress(View view){
        if (editCats==false) {
            editCats = true;
        }else{
            editCats=false;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        if (editCats){ // If the edit category button has been pressed then go to the edit category page for this specific category.

        }else{

        }
    }
}
