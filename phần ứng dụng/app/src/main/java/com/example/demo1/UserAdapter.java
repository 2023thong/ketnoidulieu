package com.example.demo1;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class UserAdapter extends BaseAdapter {

    private List<User> users;
    private LayoutInflater inflater;

    public UserAdapter(Context context, List<User> users) {
        this.users = users;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.thongtin, parent, false);
        }

        User user = users.get(position);

        TextView tvMasv = convertView.findViewById(R.id.td1);
        TextView tvTensv = convertView.findViewById(R.id.td2);
        TextView tvDiem = convertView.findViewById(R.id.td3);
        TextView tvSdt = convertView.findViewById(R.id.td4);
        TextView tvAnh = convertView.findViewById(R.id.td5);

        tvMasv.setText(user.getMasv());
        tvTensv.setText(user.getTensv());
        tvDiem.setText(user.getDiem());
        tvSdt.setText(user.getSdt());
        tvAnh.setText(user.getAnh());

        return convertView;
    }

}

