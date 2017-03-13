package com.example.sweng04.speachtherapyapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;


public class EditNewRecording extends AppCompatActivity implements OnItemClickListener {

    //String [] settingsArray;
    String[] settingsArray = {"Name Recording", "Preview Recording", "Delete Recording", "Record Again","Add to a Category",
            "Save Recording"};
    int [] icons = {R.drawable.name_rec,R.drawable.play_button,R.drawable.delete_button,R.drawable.replay_rec,R.drawable.add_button,
            R.drawable.save_rec};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_new_recording);

        ListView listView = (ListView) findViewById(R.id.edit_new_list); // Display Edit settings for new recording.

        MyAdapter adapter = new MyAdapter();

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(EditNewRecording.this);

    }

    class MyAdapter extends BaseAdapter{


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
            setting.setText(settingsArray[position]);
            icon.setImageResource(icons[position]);
            return view;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){ // Does something according to what setting is selected.
            case 0: // Name the new recording the user made
                final AlertDialog.Builder nameBuilder = new AlertDialog.Builder(EditNewRecording.this); // Dialog box to enter new name.
                View nameView = getLayoutInflater().inflate(R.layout.name_recording,null);
                final EditText nameRec = (EditText) nameView.findViewById(R.id.name_rec_box);
                Button saveName = (Button) nameView.findViewById(R.id.save_name);
                nameBuilder.setView(nameView);
                final AlertDialog dialog = nameBuilder.create();
                dialog.show();

                saveName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!nameRec.getText().toString().isEmpty()){
                            Toast.makeText(EditNewRecording.this, "Recording name saved", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }else{
                            Toast.makeText(EditNewRecording.this,"Enter a recording name",Toast.LENGTH_LONG).show();
                        }

                    }
                });
                break;
            case 1:
                //Play back recording
                break;
            case 2:
                //Delete Recording. Will make alert dialog for this.
                break;
            case 3:
                Intent intent1 = new Intent(this, RecordPage.class);
                startActivity(intent1);
                break;
            case 4:
                //Add to a category
                break;
            case 5:
                //Save the recording and go back to home screen.
                Toast.makeText(this, "Recording Saved", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this,Homepage.class);
                startActivity(intent);
                break;
        }
    }
}


