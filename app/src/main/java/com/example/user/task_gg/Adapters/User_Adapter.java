package com.example.user.task_gg.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.user.task_gg.Models.User;
import com.example.user.task_gg.R;

import java.util.List;

/**
 * Created by ${Mina} on 13/06/2018.
 */

public class User_Adapter extends BaseAdapter {

    TextView num;
    TextView name;


    private LayoutInflater inflater;
    private Context mcontext;//context
    private List<User> results;

    public User_Adapter(Context mcontext, List<User> results) {
        this.mcontext = mcontext;
        this.results = results;
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public User getItem(int i) {
        return results.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View converView, ViewGroup viewGroup) {
        inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (converView == null) {

            converView = inflater.inflate(R.layout.list_item, null);
        }


        num = converView.findViewById(R.id.num);
        num.setText(" " + getItem(position).getId());
        name = converView.findViewById(R.id.nameee);
        name.setText(getItem(position).getName());


        return converView;
    }
}