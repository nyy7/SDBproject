package com.example.nyy.accidentrecommender;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by nyy on 11/11/15.
 */
public class ReportActivity extends Activity {
    public final static String EXTRA_uri = "com.example.nyy.test.uri";

    EditText road_text;
    EditText city_text;
    EditText state_text;
    EditText time_text;

    MultiSelectionSpinner topic_spinner;
    HashMap<String,Integer> topic_map = new HashMap<String,Integer>();


    List<String> topics = new ArrayList<String>();
    Info report = new Info();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        road_text = (EditText) findViewById(R.id.editText_road);
        city_text = (EditText) findViewById(R.id.editText_city);
        state_text = (EditText) findViewById(R.id.editText_state);
        time_text = (EditText) findViewById(R.id.editText_time);


        String[] topics = {"Rollover","Children/Minors Involved","Head-on","Alcohol or Drug Use","Teenaged Driver","Road Departure",
                "Ejected from Vehicle","Seat Belt Usage","High-Speed/Excessive Speed","Hit and Run","Car Accident","Wrong-way Crash","Red Light/Stop Sign"};
        topic_map.put("Rollover",63);
        topic_map.put("Children/Minors Involved",80);
        topic_map.put("Head-on",10);
        topic_map.put("Alcohol or Drug Use",76);
        topic_map.put("Teenaged Driver",20);
        topic_map.put("Road Departure",9);
        topic_map.put("Ejected from Vehicle",79);
        topic_map.put("Seat Belt Usage",72);
        topic_map.put("High-Speed/Excessive Speed",31);
        topic_map.put("Hit and Run",98);
        topic_map.put("Car Accident",85);
        topic_map.put("Wrong-way Crash",36);
        topic_map.put("Red Light/Stop Sign",75);

        topic_spinner = (MultiSelectionSpinner) findViewById(R.id.multispinner_topic_selection);
        topic_spinner.setItems(topics);


    }

    public void submit(View view){
        //set address
        report.set_road(road_text.getText().toString());
        report.set_city(city_text.getText().toString());
        report.set_state(state_text.getText().toString());
        report.set_time(time_text.getText().toString());

        //set topics
        topics = topic_spinner.getSelectedItemsAsList();
        List topicsList = new ArrayList();
        for (int i = 0; i < topics.size();i++) {
            topicsList.add(topic_map.get(topics.get(i)));
        }
        report.set_topics(topicsList);
        String uri_submit = "http://52.24.92.50/cgi-bin/DataAnalytics/main.cgi?"
                +"&road="+report.get_road()
                +"&city="+report.get_city()
                +"&state="+report.get_state()
                +"&time="+report.get_time()
                +"&topics="+report.get_topics();
        for (int i = 0; i < topics.size();i++) {
            Log.d("MyTask",topics.get(i));
        }
        Intent report_intent = new Intent(this,ResultActivity.class);
        report_intent.putExtra(EXTRA_uri,uri_submit);
        startActivity(report_intent);


    }

}
