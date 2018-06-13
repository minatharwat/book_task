package com.example.user.task_gg;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.task_gg.Adapters.User_Adapter;
import com.example.user.task_gg.Models.ReadPages;
import com.example.user.task_gg.Models.User;

import java.util.ArrayList;
import java.util.List;

import static com.example.user.task_gg.DatabaseContract.bookEntry.DE_TABLE_NAME;
import static com.example.user.task_gg.DatabaseContract.bookEntry.TABLE_NAME;

public class MainActivity extends AppCompatActivity {

    public static SQLiteDatabase database;
    List<ReadPages> mm;
    List<User> user_list;
    TextView perc;
    EditText input;
    Button b;
    ListView ls;
    User_Adapter u;
    TextView order_user;
    boolean O = false;

    public static long adduser(String name, int u_id, int total_r) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseContract.bookEntry.U_COLUMN_ID, u_id);
        cv.put(DatabaseContract.bookEntry.U_COLUMN_NAME, name);
        cv.put(DatabaseContract.bookEntry.total_readPages, total_r);

        return database.insert(TABLE_NAME, null, cv);
    }

    public static long addSession(int to, int from, int read_pg_id) {
        ContentValues cv_r = new ContentValues();
        cv_r.put(DatabaseContract.bookEntry.U_id_readpages, read_pg_id);
        cv_r.put(DatabaseContract.bookEntry.TO, to);
        cv_r.put(DatabaseContract.bookEntry.From, from);

        return database.insert(DE_TABLE_NAME, null, cv_r);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        perc = findViewById(R.id.perc_id);
        input = findViewById(R.id.editText2);
        b = findViewById(R.id.button);
        ls = findViewById(R.id.list_s);
        order_user = findViewById(R.id.order_user);


        Book_DB_helper dbHelper = new Book_DB_helper(this);
        database = dbHelper.getWritableDatabase();

        database.delete(TABLE_NAME, "1", null);

        database.delete(DE_TABLE_NAME, null, null);


        adduser("waleed", 1, 42);
        adduser("kate ", 2, 12);
        adduser("john ", 3, 26);
        adduser("omar ", 4, 55);


        addSession(1, 20, 1);
        addSession(1, 4, 2);
        addSession(9, 30, 1);
        addSession(2, 6, 2);
        addSession(3, 9, 2);
        addSession(12, 37, 3);
        addSession(5, 46, 4);
        addSession(4, 16, 4);

        List<User> v = new ArrayList<>();
        mm = new ArrayList<>();
        user_list = new ArrayList<>();


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                EditText input = findViewById(R.id.editText2);
                int x = 0;
                String s = String.valueOf(input.getText());
                if (s.isEmpty()) {

                    input.setError("please enter user id ");
                    O = true;
                } else {

                    x = Integer.parseInt(s);
                }
                if (x == 0) {
                    input.setError("there's no id = 0");
                    O = true;


                } else {
                    O = false;
                }

                if (!O) {

                    //calculate count
                    String countQuery = "SELECT  * FROM " + DE_TABLE_NAME + " Where " + DatabaseContract.bookEntry.U_id_readpages + " =" + x;
                    //database = this.getReadableDatabase();
                    Cursor cursorz = database.rawQuery(countQuery, null);
                    int count_session = cursorz.getCount();
                    cursorz.close();


                    //
                    mm.clear();
                    Cursor cursor = database.query(DE_TABLE_NAME,
                            null,
                            DatabaseContract.bookEntry.U_id_readpages + "=?",
                            new String[]{String.valueOf(x)}, null, null, null, null);


                    float count = 0;
                    int iii = 0;
                    if (count_session < 3) {
                        if (cursor.moveToFirst()) {
                            ls.setVisibility(View.VISIBLE);
                            perc.setVisibility(View.VISIBLE);
                            order_user.setVisibility(View.VISIBLE);
                            do {
                                ReadPages r = new ReadPages();

                                r.setRd_id(cursor.getInt(cursor.getColumnIndex(DatabaseContract.bookEntry.U_id_readpages)));
                                r.setTo(cursor.getInt(cursor.getColumnIndex(DatabaseContract.bookEntry.TO)));
                                r.setFrom(cursor.getInt(cursor.getColumnIndex(DatabaseContract.bookEntry.From)));

                                count = r.getFrom() - r.getTo() + count;

                                iii++;
                                mm.add(r);
                            } while (cursor.moveToNext());


                        } else {
                            input.setError("user doesn't exist ");
                            ls.setVisibility(View.GONE);
                            perc.setVisibility(View.GONE);
                            order_user.setVisibility(View.GONE);
                        }
                        if (iii == 1) {
                            count = count + 1;
                        } else {
                            count = count + 2;
                        }
                    } else {

                        //get maximum number in to readpages coloumn

                        String countQuery_max = "SELECT  MAX(" + DatabaseContract.bookEntry.TO + ") FROM " + DE_TABLE_NAME + " Where " + DatabaseContract.bookEntry.U_id_readpages + " =" + x;
                        Cursor cursorz_mx = database.rawQuery(countQuery_max, null);
                        cursorz_mx.moveToFirst();
                        int max = cursorz_mx.getInt(0);
                        cursorz_mx.close();


                        //get minimum number in from readpages coloumn

                        String countQuery_min = "SELECT  MIN(" + DatabaseContract.bookEntry.From + ") FROM " + DE_TABLE_NAME + " Where " + DatabaseContract.bookEntry.U_id_readpages + " =" + x;
                        //database = this.getReadableDatabase();
                        Cursor cursorz_mi = database.rawQuery(countQuery_min, null);

                        cursorz_mi.moveToFirst();
                        int min = cursorz_mi.getInt(0);
                        cursorz_mi.close();

                        int count_to = 0, count_from = 0;

                        if (cursor.moveToFirst()) {
                            ls.setVisibility(View.VISIBLE);
                            perc.setVisibility(View.VISIBLE);
                            order_user.setVisibility(View.VISIBLE);
                            do {
                                ReadPages r = new ReadPages();

                                r.setRd_id(cursor.getInt(cursor.getColumnIndex(DatabaseContract.bookEntry.U_id_readpages)));
                                r.setTo(cursor.getInt(cursor.getColumnIndex(DatabaseContract.bookEntry.TO)));
                                r.setFrom(cursor.getInt(cursor.getColumnIndex(DatabaseContract.bookEntry.From)));

                                count_to = count_to + max - r.getTo();
                                count_from = count_from + r.getFrom() - min;

                                mm.add(r);
                            } while (cursor.moveToNext());


                        } else {
                            input.setError("user doesn't exist");
                            ls.setVisibility(View.GONE);
                            perc.setVisibility(View.GONE);
                            order_user.setVisibility(View.GONE);
                        }
                        count = count_from + count_to + (min - max) + 1;


                    }


                    count = count / 70;
                    String ss = " " + count;
                    perc.setText(ss);


//order and get order user


                    Cursor or = database.query(TABLE_NAME, null, null, null, null, null, DatabaseContract.bookEntry.total_readPages + " ASC");

                    user_list.clear();
                    if (or.moveToFirst()) {
                        do {
                            User r = new User();

                            r.setId(or.getInt(or.getColumnIndex(DatabaseContract.bookEntry.U_COLUMN_ID)));
                            r.setName(or.getString(or.getColumnIndex(DatabaseContract.bookEntry.U_COLUMN_NAME)));

                            user_list.add(r);
                        } while (or.moveToNext());


                    }
                    u = new User_Adapter(getApplicationContext(), user_list);
                    adapter();


                    for (int i = 0; i < user_list.size(); i++) {

                        if (x == user_list.get(i).getId()) {

                            int inp = i;
                            inp++;
                            order_user.setText("order_user=" + inp);

                        }
                    }


                } else {
                    input.setError("user doesn't exist or invalid id");
                    ls.setVisibility(View.GONE);
                    perc.setVisibility(View.GONE);
                    order_user.setVisibility(View.GONE);
                }
            }

        });


    }

    public void adapter() {
        ls.setAdapter(u);
    }


}
