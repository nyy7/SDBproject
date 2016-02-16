package com.yanglingou.accident;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MapForm extends ActionBarActivity {
    public final static String EXTRA_uri = "com.example.nyy.test.uri";
    public final static String EXTRA_mile = "com.example.nyy.test.mile";

    EditText road_text;
    EditText city_text;
    EditText state_text;
    EditText time_text;
    EditText scope_text;


    MultiSelectionSpinner topic_spinner;
    HashMap<String,Integer> topic_map = new HashMap<String,Integer>();



    List<String> topics = new ArrayList<String>();
    Info report = new Info();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_form);

        road_text = (EditText) findViewById(R.id.editText_road);
        city_text = (EditText) findViewById(R.id.editText_city);
        state_text = (EditText) findViewById(R.id.editText_state);
        time_text = (EditText) findViewById(R.id.editText_time);
        scope_text=(EditText) findViewById(R.id.editText_scope);


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
        report.set_scope(scope_text.getText().toString());

        //set topics
        topics = topic_spinner.getSelectedItemsAsList();




        List topicsList = new ArrayList();
        for (int i = 0; i < topics.size();i++) {
            topicsList.add(topic_map.get(topics.get(i)));
        }
        report.set_topics(topicsList);
        String uri_submit = "http://52.24.92.50/cgi-bin/DataAnalytics/getmarkers.cgi?"
                +"&road="+report.get_road()
                +"&city="+report.get_city()
                +"&state="+report.get_state()
                +"&scope="+report.get_scope()
                +"&topics="+report.get_topics();

        Log.d("Scope", report.get_scope());

        Intent intent = new Intent(this,MapsActivity.class);
        intent.putExtra(EXTRA_uri,uri_submit);
        startActivity(intent);




    }

    public void cluster_submit(View view){
        //set address
        report.set_road(road_text.getText().toString());
        report.set_city(city_text.getText().toString());
        report.set_state(state_text.getText().toString());
        report.set_scope(scope_text.getText().toString());

        //set topics
        topics = topic_spinner.getSelectedItemsAsList();




        List topicsList = new ArrayList();
        for (int i = 0; i < topics.size();i++) {
            topicsList.add(topic_map.get(topics.get(i)));
        }
        report.set_topics(topicsList);
        String uri_submit = "http://52.24.92.50/cgi-bin/DataAnalytics/getmarkers.cgi?"
                +"&road="+report.get_road()
                +"&city="+report.get_city()
                +"&state="+report.get_state()
                +"&scope="+report.get_scope()
                +"&topics="+report.get_topics();

        Log.d("Scope", report.get_scope());

        Intent intent = new Intent(this,ClusterMapActivity.class);
        intent.putExtra(EXTRA_uri,uri_submit);
        intent.putExtra(EXTRA_mile,scope_text.getText().toString());
        startActivity(intent);




    }



}
