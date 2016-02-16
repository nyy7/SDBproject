package com.yanglingou.accident;

import java.util.List;

/**
 * Created by nyy on 11/12/15.
 */
public class Info {
    private int caseId;
    private String state="VA";
    private String city="Falls%20Church";
    private String road="Haycock%20Road";
    private String topics="Rollover";
    private String time="2015-11-11%2000:00:00";
    private String scope="50";

    public int get_caseId(){return caseId;}
    public void set_caseId(int caseId){this.caseId = caseId;}
    public String get_state(){return state;}
    public void set_state(String state){this.state = state;}
    public String get_city(){return city;}
    public void set_city(String city){
        this.city = city.replace(" ","%20");
    }
    public String get_road(){return road;}
    public void set_road(String road){
        this.road = road.replace(" ","%20");
    }
    public String get_topics(){return topics;}
    public void set_topics(List<Integer> topics){
        this.topics = ""+topics.get(0);
        for (int i = 1; i < topics.size(); i++) {
            this.topics += "%2C" + topics.get(i);
        }
    }
    public String get_time(){return time;}
    public void set_time(String time){
        time = time.replace(" ","%20");
        this.time = time;
    }

    public String get_scope(){return scope;}
    public void set_scope(String scope){
        if(scope=="")
            scope="80450";

        long distance = Long.valueOf(scope);
        distance=1609*distance;
        this.scope = Long.toString(distance);

    }
}