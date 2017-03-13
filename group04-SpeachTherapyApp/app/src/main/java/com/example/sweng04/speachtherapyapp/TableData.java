package com.example.sweng04.speachtherapyapp;

import android.provider.BaseColumns;

/**
 * Created by williamclinton on 13/03/2017.
 */

public class TableData {
    public TableData(){

    }

    public static abstract class Recordings implements BaseColumns {

        public static final int REC_ID = 1;
        public static final String REC_NAME = "rec_name";

    }

    public static abstract class Categories implements BaseColumns{

        public static final int CAT_ID = 1;
        public static final String CAT_NAME = "catZ_name";

    }
}
