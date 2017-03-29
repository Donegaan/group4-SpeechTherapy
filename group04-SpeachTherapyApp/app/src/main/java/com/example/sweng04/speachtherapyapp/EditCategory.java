package com.example.sweng04.speachtherapyapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import java.io.File;

public class EditCategory extends AppCompatActivity implements OnItemClickListener {
    final DatabaseOperations db = new DatabaseOperations(EditCategory.this);
    String[] catSettings = {"Rename Category", "Delete Category", "Save Category"};
    int[] icons = {R.drawable.name_rec, R.drawable.delete_button,R.drawable.save_rec};
    int catID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);
        catID = getIntent().getIntExtra("catID",0);
        ListView listView = (ListView) findViewById(R.id.edit_cat_list); // Display Edit settings for new recording.

        MyAdapter adapter = new MyAdapter();

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        db.getWritableDatabase();
        final DatabaseOperations.Category cat = db.getCat(catID);
        switch (position){
            case 0: // Rename the category
                final AlertDialog.Builder nameBuilder = new AlertDialog.Builder(EditCategory.this); // Dialog box to enter new name.
                View nameView = getLayoutInflater().inflate(R.layout.name_category, null);
                final EditText nameCat = (EditText) nameView.findViewById(R.id.name_cat_box);
                Button saveName = (Button) nameView.findViewById(R.id.save_name);
                nameBuilder.setView(nameView);
                final AlertDialog dialog = nameBuilder.create();
                dialog.show();

                saveName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!nameCat.getText().toString().isEmpty()) {
                            Toast.makeText(EditCategory.this, "Category name saved", Toast.LENGTH_LONG).show();
                            Log.d("New cat name", nameCat.getText().toString());
                            cat.setCatName(nameCat.getText().toString()); // Save recording name
                            dialog.dismiss();
                        } else {
                            Toast.makeText(EditCategory.this, "Enter a category name", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                break;
            case 1: // Delete Category
                AlertDialog.Builder alert = new AlertDialog.Builder(this); // Delete alert dialog
                alert.setTitle("Delete Category");
                alert.setMessage("Are you sure you want to delete this Category?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteCat(catID);
                        startActivity(new Intent(EditCategory.this, Edit.class));
                    }
                });
                alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // close dialog
                        dialog.cancel();
                    }
                });
                alert.show();
                break;
            case 2: // Save Category
                db.updateCategory(cat);
                Toast.makeText(this, "Category Saved", Toast.LENGTH_LONG).show();
                startActivity(new Intent(EditCategory.this,Edit.class));
                break;
        }

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
