package com.example.sweng04.speachtherapyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Categories extends AppCompatActivity {
    ArrayList <DatabaseOperations.Category> categories = new ArrayList<DatabaseOperations.Category>();
    private LinearLayout mLayout;
    private EditText mEditText;
    final DatabaseOperations db = new DatabaseOperations(Categories.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        mLayout = (LinearLayout) findViewById(R.id.linearLayout);
        mEditText = (EditText) findViewById(R.id.editText);
        categories = db.getAllCategories();
        ListView list = (ListView) findViewById(R.id.rec_list);


        MyAdapter adapter = new MyAdapter();
        list.setAdapter(adapter);
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


    public void addCatClicked (View view){
        db.getWritableDatabase();
        DatabaseOperations.Category newCat = new DatabaseOperations.Category();

        String catName = mEditText.getText().toString();
        newCat.setCatName(catName);
        db.createCategory(newCat);
        categories = db.getAllCategories();


    }
    //Default Category for unassigned recordings
    public void addUnassignedCat(){
        db.getWritableDatabase();
        DatabaseOperations.Category defaultCat = new DatabaseOperations.Category();
        String defaultName = getText(R.string.Unassigned);
        defaultCat.setCatName(defaultName);
        db.createCategory(defaultCat);
    }
    //Favourites Category
    public void addFavouritesCat(){
        db.getWritableDatabase();
        DatabaseOperations.Category favourites = new DatabaseOperations.Category();
        String favName = getText(R.string.Favourites);
        favourites.setCatName(favName);
        db.createCategory(favourites);
    }
    public void catClicked(View View){
//        Intent intent = new Intent(this, Recordings.class);
//        intent.putExtra("Category", )
    }
}
