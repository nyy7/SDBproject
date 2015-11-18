package com.yanglingou.accident;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by yanglingou on 11/15/15.
 */
public class HttpRequestTask extends AsyncTask<String,Void,InputStream> {

    @Override
    protected InputStream doInBackground(String... urls) {
     // This works for only one url!
        InputStream bis=null;

        try {
            URL url = new URL(urls[0]);

            try {
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                bis=new BufferedInputStream(con.getInputStream());

            }catch (IOException e){
                e.printStackTrace();
            }

        }catch (MalformedURLException e){
            e.printStackTrace();
        }

        return bis;

    }

    protected void onPostExecute(String result) {
    }



}
