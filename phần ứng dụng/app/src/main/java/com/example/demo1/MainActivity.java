package com.example.demo1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    Button btnLogin;
    EditText edName, edPass;

    ProgressDialog pd;
    String urllink = "http://192.168.1.42:4000/listdangnhap";
    List<User1> lsuList =   new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new myAsyanTask().execute(urllink);


        btnLogin = (Button) findViewById(R.id.btnLogin);
        edName = (EditText) findViewById(R.id.edName);
        edPass = (EditText) findViewById(R.id.edPass);

        lsuList = new ArrayList<>();
        User1 user = new User1();
        lsuList.add(user);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dklocgin();


            }
        });
    }
    private class myAsyanTask extends AsyncTask< String, String, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Please wail ...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String strJon = readJonOnline(strings[0]);
            try {
                Log.d("//====", strJon);
                JSONArray jsonArray = new JSONArray(strJon);
                Log.d("//=====size===", jsonArray.length()+"");
                for (int i = 0; 1 < jsonArray.length(); i++){
//                    User u = new User();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                    u.setTenDn(jsonObject.getString("TenDn"));
//                    u.setMatkhau(jsonObject.getString("Matkhau"));
                    Log.d("TenDn", jsonObject.getString("TenDn"));
                    Log.d("Matkhau", jsonObject.getString("Matkhau"));
//                    lsuList.add(u);
//                    Log.i("thongtin"+);
                    String name = edName.getText().toString();
                    String password = edPass.getText().toString();
                    String tendn = jsonObject.getString("TenDn");
                    String mk = jsonObject.getString("Matkhau");
                    User1 user = new User1();
                    user.setTenDn(tendn);
                    user.setMatkhau(mk);
                    lsuList.add(user);


                }


            }catch (Exception ex){
                Log.d("Error: ", ex.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (pd.isShowing()){
                pd.dismiss();
            }

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
    public void dklocgin() {
        String name = edName.getText().toString();
        String password = edPass.getText().toString();

        boolean isMatched = false;
        for (User1 user : lsuList) {
            if (name != null && password != null && name.equals(user.getTenDn()) && password.equals(user.getMatkhau())) {
                isMatched = true;
                break;
            }
        }

        if (isMatched) {
            Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
            // trong MainActivity
            Intent intent = new Intent(this, Thongtin.class);
            startActivity(intent);

        } else {
            Toast.makeText(getApplicationContext(), "Thất bại", Toast.LENGTH_SHORT).show();
        }
    }


}