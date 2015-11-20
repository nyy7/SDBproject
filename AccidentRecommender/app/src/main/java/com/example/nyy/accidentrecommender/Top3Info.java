package com.example.nyy.accidentrecommender;

import java.util.ArrayList;

/**
 * Created by nyy on 11/12/15.
 */
public class Top3Info {
    private int caseId;
    private String state;
    private String city;
    private String road;
    private String link;
    private String topics;
    private String date;
    private double similarity;

    public int get_caseId(){return caseId;}
    public void set_caseId(int caseId){this.caseId = caseId;}
    public String get_state(){return state;}
    public void set_state(String state){this.state = state;}
    public String get_city(){return city;}
    public void set_city(String city){this.city = city;}
    public String get_road(){return road;}
    public void set_road(String road){this.road = road;}
    public String get_link(){return link;}
    public void set_link(String link){this.link = link;}
    public String get_topics(){return topics;}
    public void set_topics(String topics){this.topics = topics;}
    public String get_date(){return date;}
    public void set_date(String date){this.date=date;}
    public double get_similarity(){return similarity;}
    public void set_similarity(double similarity){this.similarity = similarity;}
}
