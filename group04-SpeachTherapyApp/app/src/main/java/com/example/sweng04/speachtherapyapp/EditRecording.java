package com.example.sweng04.speachtherapyapp;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.File;

public class EditRecording extends AppCompatActivity implements OnItemClickListener {
    final DatabaseOperations db = new DatabaseOperations(EditRecording.this);
    String[] settingsArray = {"Name Recording", "Preview Recording", "Delete Recording", "Record Again","Add to a Category",
            "Save Recording"};
    int [] icons = {R.drawable.name_rec,R.drawable.play_button,R.drawable.delete_button,R.drawable.replay_rec,R.drawable.add_button,
            R.drawable.save_rec};
    String filename = "";
    String key=""; // For back button
    int recID;
    String recCorrectName;
    DatabaseOperations.Record rec;
    boolean init = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_new_recording);
        ListView listView = (ListView) findViewById(R.id.edit_new_list); // Display Edit settings for new recording.
        MyAdapter adapter = new MyAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        filename = getIntent().getStringExtra("FILENAME"); // getting filename we are editing
        key = getIntent().getStringExtra("Key");
        recID = getIntent().getIntExtra("recID",-1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    class MyAdapter extends BaseAdapter{ // Adapter for the list view of all the settings.

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
            if (view==null) {
                view = getLayoutInflater().inflate(R.layout.edit_rec_row, parent, false);
            }
            ImageView icon = (ImageView) view.findViewById(R.id.edit_icon);
            TextView setting = (TextView) view.findViewById(R.id.edit_rec_text);
            setting.setText(settingsArray[position]);
            icon.setImageResource(icons[position]);
            return view;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        db.getWritableDatabase();
        if (recID==-1) {
            if(init == true) {
                rec = new DatabaseOperations.Record();

                init = false;
            }
        }else{
            if (key.equals("Edit")){
                rec=db.getRec(recID);
            }else {
                rec.setId(recID);
            }
        }

        switch (position) { // Does something according to what setting is selected.
            case 0: // Name the new recording the user made

                final AlertDialog.Builder nameBuilder = new AlertDialog.Builder(EditRecording.this); // Dialog box to enter new name.
                View nameView = getLayoutInflater().inflate(R.layout.name_recording, null);
                final EditText nameRec = (EditText) nameView.findViewById(R.id.name_rec_box);
                Button saveName = (Button) nameView.findViewById(R.id.save_name);
                nameBuilder.setView(nameView);
                final AlertDialog dialog = nameBuilder.create();
                dialog.show();
                saveName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!nameRec.getText().toString().isEmpty()) {
                            Toast.makeText(EditRecording.this, "Recording name saved", Toast.LENGTH_LONG).show();
                            //Log.d("New Rec name", nameRec.getText().toString());
                            Log.d("??????????",nameRec.getText().toString() +"----------------------------------");
                            rec.setRecName(nameRec.getText().toString()); // Save recording name
                            Log.d("Rec name",rec.getRecName() +"----------------------------------");
                            recCorrectName = rec.getRecName();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(EditRecording.this, "Enter a recording name", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                break;
            case 1:
                //Play back recording
                Log.e("Playback filename", filename);
                MediaPlayer playback = MediaPlayer.create(this, Uri.parse(getExternalFilesDir(null).getAbsolutePath() +"/"+filename+".wav"));
                playback.setLooping(true);
                playback.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                playback.start();
                break;
            case 2:
                AlertDialog.Builder alert = new AlertDialog.Builder(this); // Delete alert dialog
                alert.setTitle("Delete Recording");
                alert.setMessage("Are you sure you want to delete?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        File file = new File(Uri.parse(getExternalFilesDir(null).getAbsolutePath() +"/"+filename+".wav").getPath());
                        file.delete();
                        if(file.exists()){
                            getApplicationContext().deleteFile(file.getName());

                        }

                        // continue with delete
                        db.deleteRec(rec.id);
                        if (key.equals("RecordPage")) {
                            startActivity(new Intent(EditRecording.this, RecordPage.class));
                        }else if (key.equals("Edit")){
                            //Will go back to previous page to display the list of recordings.
                            Intent intent = new Intent(EditRecording.this, Recordings.class);
                            intent.putExtra("key", "Edit");
                            startActivity(intent);
                        }
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
            case 3:
                AlertDialog.Builder alert2 = new AlertDialog.Builder(this); // Record again alert dialog
                alert2.setTitle("Record again");
                alert2.setMessage("Are you sure you want to record again and delete the current recording?");
                alert2.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        db.deleteRec(rec.id);
                        Intent intent1 = new Intent(EditRecording.this, RecordPage.class); // Record again
                        startActivity(intent1);
                    }
                });
                alert2.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // close dialog
                        dialog.cancel();
                    }
                });
                alert2.show();
                break;
            case 4:
                //Add to a category
                Intent intent2 = new Intent(this, Categories.class);
                intent2.putExtra("addToCat", rec.getId());
                Log.d("Rec ID when add to cat",rec.getId()+"");
                startActivity(intent2);
                break;
            case 5:
                if (recID==-1 ) { // If its a new recording.
                    Log.d("Rec name",rec.getRecName() +"?????????????????????????");
                    //rec.setRecName(recCorrectName);
                    db.createRecord(rec);
                }else{
                    db.updateRecord(rec);
                }
                Log.d("Rec ID", rec.getId()+"");
                //Save the recording and go back to home screen.
                Toast.makeText(this, "Recording Saved", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, Homepage.class);
                startActivity(intent);
                break;
        }
    }

}


