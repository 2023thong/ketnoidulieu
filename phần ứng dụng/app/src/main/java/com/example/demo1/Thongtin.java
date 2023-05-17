package com.example.demo1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Thongtin extends AppCompatActivity {
    TextView tvMasv;
    TextView tvTensv;
    TextView tvDiem;
    TextView tvSdt;
    TextView tvAnh;

    Button btnThongtin;



    ProgressDialog pd1;
    String urllink = "http://192.168.1.42:4000/list";
    List<User> lsuList =   new ArrayList<>();


    private ListView lvUser;


    private UserAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtin);
        new Thongtin.myAsyanTask().execute(urllink);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Thông tin chi tiết");
        }



        lvUser = findViewById(R.id.lvUser);
        adapter = new UserAdapter(this, lsuList);
        lvUser.setAdapter(adapter);




    }


    private class myAsyanTask extends AsyncTask< String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd1 = new ProgressDialog(Thongtin.this);
            pd1.setMessage("Please wail ...");
            pd1.setCancelable(false);
            pd1.show();


        }

        @Override
        protected String doInBackground(String... strings) {
            String strJon = readJonOnline(strings[0]);
            try {
                Log.d("//====", strJon);
                JSONArray jsonArray = new JSONArray(strJon);


                Log.d("//=====size===", jsonArray.length()+"");
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Log.d("Masv", jsonObject.getString("Masv"));
                    Log.d("Tensv", jsonObject.getString("Tensv"));
                    Log.d("Diem", jsonObject.getString("Diem"));
                    Log.d("Sdt", jsonObject.getString("Sdt"));
                    Log.d("Anh", jsonObject.getString("Anh"));



                    String Masv = jsonObject.getString("Masv");
                    String Tensv = jsonObject.getString("Tensv");
                    String Diem = jsonObject.getString("Diem");
                    String Sdt = jsonObject.getString("Sdt");
                    String Anh = jsonObject.getString("Anh");

                    User user1 = new User();
                    user1.setMasv(Masv);
                    user1.setTensv(Tensv);
                    user1.setDiem(Diem);
                    user1.setSdt(Sdt);
                    user1.setAnh(Anh);
                    lsuList.add(user1);

                }


            }catch (Exception ex){
                Log.d("Error: ", ex.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (pd1.isShowing()){
                pd1.dismiss();
            }
            //hiển thị

            lvUser.setAdapter(adapter);


        }
    }
    public String readJonOnline(String linkUrl){
        HttpURLConnection connection = null;
        BufferedReader bufferedReader = null;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            URL url = new URL(linkUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = bufferedReader.readLine()) !=null){
                stringBuffer.append(line+"\n");
            }
            return stringBuffer.toString();

        }
        catch (Exception ex){
            Log.d("Error: ", ex.toString());
        }
        return null;
    }

}