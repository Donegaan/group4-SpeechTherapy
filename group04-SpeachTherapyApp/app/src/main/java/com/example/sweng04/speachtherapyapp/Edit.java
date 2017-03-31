package com.example.sweng04.speachtherapyapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import java.util.ArrayList;

public class Edit extends AppCompatActivity implements OnItemClickListener {
    ArrayList <DatabaseOperations.Category> categories = new ArrayList<DatabaseOperations.Category>();
    boolean editCats = false;
    final DatabaseOperations db = new DatabaseOperations(Edit.this);
    boolean favClick=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ListView list = (ListView) findViewById(R.id.edit_list); // Make list for categories.
        MyAdapter adapter = new MyAdapter();
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
        categories = db.getAllCategories();
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
            TextView category = (TextView) convertView.findViewById(R.id.rec_text);
            String catName = categories.get(position).getCatName();
            //Log.d("Recording ID", categories.get(position).getId()+"");
            category.setText(catName);
            return convertView;
        }
    }

    public void editCatPress(View view){ // The top button has been pressed which indicates the user wants to edit a category.
        if (editCats==false) {
            editCats = true;
            Button btn = (Button) findViewById(R.id.edit_categories_button);
            btn.setText("Tap a category to edit");
            btn.setTextColor(Color.argb(255,41, 98, 255));
            btn.setBackgroundColor(Color.WHITE);
        }else{
            editCats=false;
            Button btn = (Button) findViewById(R.id.edit_categories_button);
            btn.setText("Tap to edit Categories");
            btn.setTextColor(Color.WHITE);
            btn.setBackgroundColor(Color.argb(255,41, 98, 255));
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
        if(categories.get(position).getCatName()!=null) {
            if (categories.get(position).getCatName().equals("Favourites") && editCats) {
                Toast.makeText(Edit.this, "Cannot edit Favourites category", Toast.LENGTH_LONG).show();
                favClick=true;
            }
        }
        if (!favClick){
            Intent intent;
            if (editCats) { // If the edit category button has been pressed then go to the edit category page for this specific category.
                intent = new Intent(this, EditCategory.class);
                intent.putExtra("catName", categories.get(position).getCatName());
            } else {
                intent = new Intent(this, Recordings.class);
                intent.putExtra("key", "Edit");
            }
            intent.putExtra("catID", categories.get(position).getId());
            startActivity(intent);
        }
        favClick=false;
    }

    public void unassignedClicked(View view){
        Intent intent = new Intent(Edit.this,Recordings.class);
        intent.putExtra("unassigned",true);
        intent.putExtra("key", "Edit");
        startActivity(intent);
    }

    public void doneEdit(View view){
        startActivity(new Intent(Edit.this,Homepage.class));
    }
}
