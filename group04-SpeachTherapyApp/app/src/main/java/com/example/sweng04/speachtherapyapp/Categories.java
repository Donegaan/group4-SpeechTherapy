package com.example.sweng04.speachtherapyapp;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;

import static android.R.id.message;

public class Categories extends AppCompatActivity implements OnItemClickListener {
    ArrayList <DatabaseOperations.Category> categories = new ArrayList<DatabaseOperations.Category>();
    private LinearLayout mLayout;
    private EditText mEditText;
    final DatabaseOperations db = new DatabaseOperations(Categories.this);
    int recID;
    MyAdapter adapter = new MyAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mEditText = (EditText) findViewById(R.id.editText);
        recID=getIntent().getIntExtra("addToCat",-1); // If a recording ID is passed it means it should be added to the category.
        categories = db.getAllCategories();
        ListView list = (ListView) findViewById(R.id.rec_list);



        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
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
        Toast.makeText(this, catName+" has been added to Categories", Toast.LENGTH_SHORT).show();
        adapter.notifyDataSetChanged();
    }
    //Default Category for unassigned recordings
    /*public void addUnassignedCat(){
        db.getWritableDatabase();
        DatabaseOperations.Category defaultCat = new DatabaseOperations.Category();
        String defaultName = (String)getText(R.string.Unassigned);
        defaultCat.setCatName(defaultName);
        db.createCategory(defaultCat);
    }*/
    //Favourites Category
    public void addFavouritesCat(){
        db.getWritableDatabase();
        DatabaseOperations.Category favourites = new DatabaseOperations.Category();
        String favName = (String)getText(R.string.Favourites);
        favourites.setCatName(favName);
        db.createCategory(favourites);
    }
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (recID==-1){
            //Show recordings
            Intent intent = new Intent(Categories.this, Recordings.class);
            intent.putExtra("catID", categories.get(position).getId());
            intent.putExtra("key", "Categories"); // Passing previous activity
            startActivity(intent);
        }else{ //Add recording to this category.

            db.createCatRec(categories.get(position).getId(),recID);
            Toast.makeText(Categories.this,"Recording added to " + categories.get(position).getCatName() + " category.",Toast.LENGTH_LONG).show();

        }
    }

    public void unassignedClicked(View view){
        Intent intent = new Intent(Categories.this,Recordings.class);
        intent.putExtra("unassignedRecs",true);
        startActivity(intent);
    }
}
