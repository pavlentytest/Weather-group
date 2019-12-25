package com.samsung.myitschool.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    Button btn;
    TextView tv; // null

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.button);
        tv = findViewById(R.id.textView);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // логика вызова класса наследника AsyncTask
                MyDownload md = new MyDownload();
                md.execute();
            }
        });
    }

    private class MyDownload extends AsyncTask<Void,Void,String> {
        HttpURLConnection httpurl;
        @Override
        protected String doInBackground(Void... ddd) {
            String url = "http://api.weatherstack.com/current?access_key=c88c008b493b902d7d6c70b4da8fe193&query=Dubai";
            try {

                URL obj_url = new URL(url);
                httpurl = (HttpURLConnection) obj_url.openConnection();
                httpurl.setRequestMethod("GET");
                httpurl.connect();

                InputStream inputStream = httpurl.getInputStream();
                Scanner scan = new Scanner(inputStream);
                StringBuffer buffer = new StringBuffer();
                while(scan.hasNextLine()) {
                    buffer.append(scan.nextLine());
                }
                return buffer.toString();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Gson g = new Gson();
            Weather w = g.fromJson(s,Weather.class);
            //w.getCurrent().getFeelslike();
            tv.setText(Integer.toString(w.getCurrent().getTemperature()));
        }
    }
}
