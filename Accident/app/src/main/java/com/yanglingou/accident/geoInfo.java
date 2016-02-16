package com.yanglingou.accident;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanglingou on 11/16/15.
 */
public class geoInfo {

    private List<GeoRecord> geo_records_list;
    private LatLng user_position;

    geoInfo(){
        this.geo_records_list=new ArrayList<GeoRecord>();

    }

    public void addGeoRecord(GeoRecord georecord){
        this.geo_records_list.add(georecord);
    }

    public List<GeoRecord> getGeoList(){
        return this.geo_records_list;
    }

    public void setUserPosition(LatLng up){
        this.user_position=up;


    }

    public LatLng getUserPosition(){
        return this.user_position;
    }




}
