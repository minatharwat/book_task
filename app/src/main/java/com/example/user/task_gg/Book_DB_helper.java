package com.example.user.task_gg;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ${Mina} on 10/06/2018.
 */

public class Book_DB_helper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "book.db";
    private static final int DATABASE_VERSION = 1;

    public Book_DB_helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_USER_TABLE = "CREATE TABLE " + DatabaseContract.bookEntry.TABLE_NAME + " ("
                + DatabaseContract.bookEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DatabaseContract.bookEntry.U_COLUMN_NAME + " TEXT NOT NULL,"
                + DatabaseContract.bookEntry.total_readPages + " TEXT NOT NULL,"
                + DatabaseContract.bookEntry.U_COLUMN_ID + " INTEGER NOT NULL" + ");";


        final String SQL_CREATE_Readpages_TABLE = "CREATE TABLE " + DatabaseContract.bookEntry.DE_TABLE_NAME + " ("
                + DatabaseContract.bookEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DatabaseContract.bookEntry.TO + " INTEGER NOT NULL,"
                + DatabaseContract.bookEntry.From + " INTEGER NOT NULL,"
                + DatabaseContract.bookEntry.U_id_readpages + " INTEGER NOT NULL" + ");";


        db.execSQL(SQL_CREATE_USER_TABLE);
        db.execSQL(SQL_CREATE_Readpages_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        if (i1 > i) {
            db.execSQL("ALTER TABLE " + DatabaseContract.bookEntry.TABLE_NAME + " ADD COLUMN " + DatabaseContract.bookEntry.total_readPages + " INTEGER DEFAULT 0");
        }
        db.execSQL(" DROP TABLE IF Exists " + DatabaseContract.bookEntry.TABLE_NAME);
        db.execSQL(" DROP TABLE IF Exists " + DatabaseContract.bookEntry.DE_TABLE_NAME);

        onCreate(db);

    }
}
