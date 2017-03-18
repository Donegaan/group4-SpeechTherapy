package com.example.sweng04.speachtherapyapp;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class AddRecToCat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rec_to_cat);

        DatabaseOperations db = new DatabaseOperations(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        ListView list = (ListView) findViewById(R.id.category_list); // Display list of categories
//
//        ArrayList<DatabaseOperations.Category> categories = db.getAllCategories();
//
//        ArrayAdapter<DatabaseOperations.Category> carAdapter = new ArrayAdapter<DatabaseOperations.Category>(getApplicationContext(),
//                android.R.layout.simple_list_item_1, categories);
//
//        list.setAdapter(carAdapter);
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
}
