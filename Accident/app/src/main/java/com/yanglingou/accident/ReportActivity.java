package com.yanglingou.accident;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;


public class ReportActivity extends Activity {
    public final static String EXTRA_uri = "com.example.nyy.test.uri";

    EditText road_text;
    EditText city_text;
    EditText state_text;

    MultiSelectionSpinner topic_spinner;
    ProgressBar pb;

    List<String> topics = new ArrayList<String>();
    Info report = new Info();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        road_text = (EditText) findViewById(R.id.editText_road);
        city_text = (EditText) findViewById(R.id.editText_city);
        state_text = (EditText) findViewById(R.id.editText_state);

        String[] topics = {"Rollover","Children/Minors Involved","Head-on","Alcohol or Drug Use","Teenaged Driver","Road Departure",
                "Ejected from Vehicle","Seat Belt Usage","High-Speed/Excessive Speed","Hit and Run","Car Accident","Wrong-way Crash","Red Light/Stop Sign"};
        topic_spinner = (MultiSelectionSpinner) findViewById(R.id.multispinner_topic_selection);
        topic_spinner.setItems(topics);


    }

    public void submit(View view){
        //set address
        report.set_road(road_text.getText().toString());
        report.set_city(city_text.getText().toString());
        report.set_state(state_text.getText().toString());

        //set topics
        topics = topic_spinner.getSelectedItemsAsList();
        String uri_submit = "http://52.24.92.50/cgi-bin/DataAnalytics/main.cgi?"
                +"topics="+report.get_topics()
                +"road="+report.get_road()
                +"city="+report.get_city()
                +"state="+report.get_state();
        for (int i = 0; i < topics.size();i++) {
            Log.d("MyTask", topics.get(i));
        }
        Intent report_intent = new Intent(this,ResultActivity.class);
        report_intent.putExtra(EXTRA_uri,uri_submit);
        startActivity(report_intent);


    }

}
