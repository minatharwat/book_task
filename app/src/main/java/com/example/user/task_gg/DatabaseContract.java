package com.example.user.task_gg;

import android.provider.BaseColumns;

/**
 * Created by ${Mina} on 10/06/2018.
 */

public class DatabaseContract {
    public static final class bookEntry implements BaseColumns {

        public static final String TABLE_NAME = "user";

        public static final String U_COLUMN_NAME = "name";
        public static final String U_COLUMN_ID = "id";
        public static final String total_readPages = "toal_r";


        public static final String DE_TABLE_NAME = "readpages";
        public static final String TO = "_to";
        public static final String From = "_from";
        public static final String U_id_readpages = "_rp";


    }
}
