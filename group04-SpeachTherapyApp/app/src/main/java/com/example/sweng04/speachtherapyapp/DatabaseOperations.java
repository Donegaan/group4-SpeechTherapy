package com.example.sweng04.speachtherapyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by williamclinton on 13/03/2017.
 */

public class DatabaseOperations extends SQLiteOpenHelper {


    // Logcat tag
    private static final String LOG = "DatabaseOperations";

    private static final int database_version = 1;
    // Database Name
    private static final String DATABASE_NAME = "RecordPhase";
    // Table Name

    private static final String TABLE_CAT_REC = "category_recording";

    //-----------------CATEGORY TABLE-----------------
    private static final String TABLE_CATEGORY = "category";
    private static final String CAT_ID = "cat_id";
    private static final String CAT_NAME = "cat_name";

    //---||||||| TABLE CREATION |||||||---
    private static final String CREATE_TABLE_CAT = "CREATE TABLE "
            + TABLE_CATEGORY + " (" + CAT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CAT_NAME + " TEXT )";
    //---||||||| END OF TABLE CREATION |||||||---

    //-----------------RECORDING TABLE-----------------
    private static final String TABLE_RECORDING = "recording";
    private static final String REC_ID = "rec_id";
    private static final String REC_NAME = "rec_name";
    private static final String REC_LOC = "rec_location";

    //---||||||| TABLE CREATION |||||||---
    private static final String CREATE_TABLE_REC = "CREATE TABLE "
            + TABLE_RECORDING + " (" + REC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + REC_NAME + " TEXT )";
    //---||||||| END OF TABLE CREATION |||||||---

    //-----------------CAT - REC TABLE-----------------
    private static final String CREATE_TABLE_CAT_REC = "CREATE TABLE "
            + TABLE_CAT_REC + " ( " + CAT_ID + " INTEGER NOT NULL REFERENCES "
            + TABLE_CATEGORY + " ( " + CAT_ID + " ), "
            + REC_ID + " INTEGER NOT NULL REFERENCES "
            + TABLE_RECORDING + " ( " + REC_ID + " ), "
            + "PRIMARY KEY( " + CAT_ID + ", " + REC_ID + " ))";

    //---- END OF ALL TABLE CREATION ---- END OF ALL TABLE CREATION ---- END OF ALL TABLE CREATION ----

    public DatabaseOperations(Context context) {

        super(context, TableData.Recordings.REC_NAME, null, database_version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CAT);
        db.execSQL(CREATE_TABLE_REC);
        db.execSQL(CREATE_TABLE_CAT_REC);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        //
    }

    //--------------------REC--------------------REC--------------------REC--------------------


    public static class Record {

        int id;
        String rec_name;
        String location;

        public Record() {
        }

        ;

        public Record(String rec_name) {
            this.rec_name = rec_name;
        }

        // setter
        public void setId(int id) {
            this.id = id;
        }

        public void setRecName(String rec_name) {
            this.rec_name = rec_name;
        }

        // getter
        public int getId() {
            return this.id;
        }

        public String getRecName() {
            return this.rec_name;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getLocation() {
            return this.location;
        }
    }


    // inserts project into project table
    public long createRecord(Record record) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(REC_NAME, record.getRecName());
        //values.put(REC_LOC, record.getLocation());

        // insert row
        long rec_id = db.insert(TABLE_RECORDING, null, values);

        return rec_id;
    }

    // fetches list of all recordings in Record Table
    public ArrayList<Record> getAllRecords() {
        ArrayList<Record> recordings = new ArrayList<Record>();
        String selectQuery = "SELECT " + REC_ID + " , " + REC_NAME + " FROM " + TABLE_RECORDING;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Record rec = new Record();
                rec.setId(c.getInt((c.getColumnIndex(REC_ID))));
                rec.setRecName((c.getString(c.getColumnIndex(REC_NAME))));

                // adding to todo list
                recordings.add(rec);
            } while (c.moveToNext());
        }

        return recordings;
    }

    // fetch single project from project table
    public Record getRec(long rec_id) {
        String selectQuery = "SELECT " + REC_ID + " , " + REC_NAME
                + " FROM " + TABLE_RECORDING
                + " WHERE " + REC_ID + " = " + rec_id;
        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();
        }
        Record rec = new Record();
        rec.setId(c.getInt(c.getColumnIndex(REC_ID)));
        rec.setRecName(c.getString(c.getColumnIndex(REC_NAME)));

        return rec;
    }

    // delete Recording from record table
    public void deleteRec(long rec_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RECORDING, REC_ID + " = ?",
                new String[]{String.valueOf(rec_id)});
    }

    // Update name of Recording
    public int updateRecord(Record record) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(REC_NAME, record.getRecName());

        // updating row
        return db.update(TABLE_RECORDING, values, REC_ID + " = ?",
                new String[]{String.valueOf(record.getId())});
    }


    //--------------------CAT--------------------CAT--------------------CAT--------------------

    public Category newCat() {
        return new Category();
    }

    public static class Category {

        int id;
        String cat_name;

        public Category() {
        }

        ;

        public Category(String cat_name) {
            this.cat_name = cat_name;
        }

        // setter
        public void setId(int id) {
            this.id = id;
        }

        public void setCatName(String cat_name) {
            this.cat_name = cat_name;
        }

        // getter
        public int getId() {
            return this.id;
        }

        public String getCatName() {
            return this.cat_name;
        }
    }

    // inserts project into project table
    public long createCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CAT_NAME, category.getCatName());

        // insert row
        long cat_id = db.insert(TABLE_CATEGORY, null, values);

        return cat_id;
    }

    // fetches list of all recordings in Record Table
    public ArrayList<Category> getAllCategories() {
        ArrayList<Category> categorys = new ArrayList<Category>();
        String selectQuery = "SELECT * FROM " + TABLE_CATEGORY;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Category cat = new Category();
                cat.setId(c.getInt((c.getColumnIndex(CAT_ID))));
                cat.setCatName((c.getString(c.getColumnIndex(CAT_NAME))));

                // adding to todo list
                categorys.add(cat);
            } while (c.moveToNext());
        }

        return categorys;
    }

    // fetch single project from project table
    public Category getCat(long cat_id) {
        String selectQuery = "SELECT * FROM " + TABLE_CATEGORY
                + " WHERE " + CAT_ID + " = " + cat_id;
        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();
        }
        Category cat = new Category();
        cat.setId(c.getInt(c.getColumnIndex(CAT_ID)));
        cat.setCatName(c.getString(c.getColumnIndex(CAT_NAME)));
        return cat;
    }

    // delete category from table
    public void deleteCat(long cat_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CATEGORY, CAT_ID + " = ?",
                new String[]{String.valueOf(cat_id)});
    }

    // update category name
    public int updateCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CAT_NAME, category.getCatName());

        // updating row
        return db.update(TABLE_CATEGORY, values, CAT_ID + " = ?",
                new String[]{String.valueOf(category.getId())});
    }

    //--------------------CAT-REC--------------------CAT-REC--------------------CAT-REC--------------------

    // inserts new entry into category recording table
    public long createCatRec(long cat_id, long rec_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CAT_ID, cat_id);
        values.put(REC_ID, rec_id);
        // insert row
        long cat_rec_id = db.insert(TABLE_CAT_REC, null, values);

        return cat_rec_id;
    }

    // fetches all categories connected to a certain recording
    public ArrayList<Category> getCatRec(long rec_id) {
        ArrayList<Category> categories = new ArrayList<Category>();
        String selectQuery = "SELECT" + TABLE_CATEGORY + "." + CAT_ID + "," + TABLE_CATEGORY + "." + CAT_NAME
                + " FROM " + TABLE_CAT_REC + ", " + TABLE_CATEGORY
                + " WHERE " + TABLE_CAT_REC + "." + REC_ID + " = " + rec_id
                + " AND " + TABLE_CATEGORY + "." + CAT_ID + " = " + TABLE_CAT_REC + "." + CAT_ID;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Category cat = new Category();
                cat.setId(c.getInt((c.getColumnIndex(CAT_ID))));
                cat.setCatName(c.getString((c.getColumnIndex(CAT_NAME))));
                // adding to list
                categories.add(cat);
            } while (c.moveToNext());
        }
        return categories;
    }

    // fetches all recording connected to a certain categories
    public ArrayList<Record> getRecCat(long cat_id) {
        ArrayList<Record> recordings = new ArrayList<Record>();
        String selectQuery = "SELECT " + TABLE_RECORDING + "." + REC_ID + ", " + TABLE_RECORDING + "." + REC_NAME
                + " FROM " + TABLE_CAT_REC + ", " + TABLE_RECORDING
                + " WHERE " + TABLE_CAT_REC + "." + CAT_ID + " = " + cat_id
                + " AND " + TABLE_RECORDING + "." + REC_ID + " = " + TABLE_CAT_REC + "." + REC_ID;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Record rec = new Record();
                rec.setId(c.getInt((c.getColumnIndex(REC_ID))));
                rec.setRecName((c.getString(c.getColumnIndex(REC_NAME))));
                //obj.setReturn_date((c.getString(c.getColumnIndex(OBJ_RETURN_DATE))));
                // adding to todo list
                recordings.add(rec);
            } while (c.moveToNext());
        }
        return recordings;
    }

    // fetches all unassigned recordings
    public ArrayList<Record> getAssignedRec() {
        ArrayList<Record> recordings = new ArrayList<Record>();
        //String selectQuery = "SELECT " + REC_ID + " FROM " + TABLE_CAT_REC;

        String selectQuery = "SELECT " + TABLE_RECORDING + "." + REC_ID + ", " + TABLE_RECORDING + "." + REC_NAME
                + " FROM " + TABLE_CAT_REC + ", " + TABLE_RECORDING
                + " WHERE " + TABLE_CAT_REC + "." + REC_ID + " = " + TABLE_RECORDING + " . " + REC_ID;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Record rec = new Record();
                rec.setId(c.getInt((c.getColumnIndex(REC_ID))));
                rec.setRecName((c.getString(c.getColumnIndex(REC_NAME))));
                //obj.setReturn_date((c.getString(c.getColumnIndex(OBJ_RETURN_DATE))));
                // adding to todo list
                recordings.add(rec);
            } while (c.moveToNext());
        }
        return recordings;
    }

    // delete category from Category-Recording table
    public void deleteCatRec(long cat) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CAT_REC, CAT_ID + " = ?",
                new String[]{String.valueOf(cat)});
    }

    // delete recording from Category-Recording table
    public void deleteRecCat(long rec) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CAT_REC, REC_ID + " = ?",
                new String[]{String.valueOf(rec)});
    }

    // delete row from CAT_REC table depeding on ID of both recording and category
    public void deleteRecInCat(long rec, long cat) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CAT_REC, REC_ID + " = ? AND " + CAT_ID + " = ?",
                new String[]{String.valueOf(rec),String.valueOf(cat)});
    }
}