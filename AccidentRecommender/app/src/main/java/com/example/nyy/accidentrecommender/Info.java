package com.example.nyy.accidentrecommender;

import java.util.ArrayList;

/**
 * Created by nyy on 11/12/15.
 */
public class Info {
    private int caseId;
    private String state;
    private String city;
    private String road;
    private String topics;
    private String url;

    public int get_caseId(){return caseId;}
    public void set_caseId(int caseId){this.caseId = caseId;}
    public String get_state(){return state;}
    public void set_state(String state){this.state = state;}
    public String get_city(){return city;}
    public void set_city(String city){this.city = city;}
    public String get_road(){return road;}
    public void set_road(String road){this.road = road;}
    public String get_topics(){return topics;}
    public void set_topics(ArrayList<String> topics){
        this.topics = topics.get(0);
        for (int i = 1; i < topics.size(); i++) {
            this.topics += "&" + topics.get(i);
        }
    }
    public String get_url(){return url;}
    public void set_url(String url){this.url = url;}

}
