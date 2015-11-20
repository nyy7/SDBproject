package com.example.nyy.accidentrecommender;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;



public class ResultActivity extends Activity {
    String uri_report;
    String url1;
    String addr1;
    String date1;
    String url2;
    String addr2;
    String date2;
    String url3;
    String addr3;
    String date3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        uri_report = intent.getStringExtra(ReportActivity.EXTRA_uri);
        Log.d("Yanyan",uri_report);
        if (isOnline()) {
            requestData(uri_report);

        } else {
            Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void click_url1(View view){
        Uri uri = Uri.parse(url1);
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }

    public void click_url2(View view){
        Uri uri = Uri.parse(url2);
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }

    public void click_url3(View view){
        Uri uri = Uri.parse(url3);
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }

    private void requestData(String uri) {
        ReportTask task = new ReportTask(this);
        task.execute(uri);
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }



    public class ReportTask extends AsyncTask<String, String, String> {
        private Activity activity;

        public ReportTask(Activity activity) {
            this.activity = activity;
        }

        @Override
        protected String doInBackground(String... params) {

            // TODO: retrieve accident info XML via HttpManager.
            String content = HttpManager.getData(params[0]);
            return content;


        }

        @Override
        protected void onPostExecute(String result) {
            // TODO: parse accident information from XML.
            Log.d("Yanyan",result);
            List<Top3Info> report = Top3Info_XMLParser.parseFeed(result);
            Log.d("Yanyan","haha");
            Top3Info acc1 = new Top3Info();
            Top3Info acc2 = new Top3Info();
            Top3Info acc3 = new Top3Info();
            try {
                acc1 = report.get(0);
                acc2 = report.get(1);
                acc3 = report.get(2);
            }catch(NullPointerException e){
                Log.d("Yanyan","null");
            }

            // TODO: display the information

            addr1 = acc1.get_road()+ ", "+acc1.get_city();
            addr2 = acc2.get_road()+ ", "+acc2.get_city();
            addr3 = acc3.get_road()+ ", "+acc3.get_city();
            TextView a1 = (TextView) findViewById(R.id.textView_address1);
            a1.setText(addr1);
            TextView a2 = (TextView) findViewById(R.id.textView_address2);
            a2.setText(addr2);
            TextView a3 = (TextView) findViewById(R.id.textView_address3);
            a3.setText(addr3);

            date1 = acc1.get_date();
            date2 = acc2.get_date();
            date3 = acc3.get_date();
            TextView d1 = (TextView) findViewById(R.id.textView_time1);
            d1.setText(date1);
            TextView d2 = (TextView) findViewById(R.id.textView_time2);
            d2.setText(date2);
            TextView d3 = (TextView) findViewById(R.id.textView_time3);
            d3.setText(date3);

            url1 = acc1.get_link();
            url2 = acc2.get_link();
            url3 = acc3.get_link();
        }
    }
}
