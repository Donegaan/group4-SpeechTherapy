package com.example.sweng04.speachtherapyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class EditCategory extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String[] catSettings = {"Rename Category", "Delete Category", "Save Category"};
    int[] icons = {R.drawable.name_rec, R.drawable.delete_button,R.drawable.save_rec};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);

        ListView listView = (ListView) findViewById(R.id.edit_cat_list); // Display Edit settings for new recording.

        MyAdapter adapter = new MyAdapter();

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    class MyAdapter extends BaseAdapter { // Adapter for the list view of all the settings.

        @Override
        public int getCount() {
            return icons.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView (int position, View view, ViewGroup parent){
            view = getLayoutInflater().inflate(R.layout.edit_rec_row,parent,false);

            ImageView icon = (ImageView) view.findViewById(R.id.edit_icon);
            TextView setting = (TextView) view.findViewById(R.id.edit_rec_text);
            setting.setText(catSettings[position]);
            icon.setImageResource(icons[position]);
            return view;
        }
    }
}
