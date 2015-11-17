package com.yanglingou.accident;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by yanglingou on 11/16/15.
 */
public class geoInfo {
    public LatLng geo;
    public String type;
    public int id;
    geoInfo(LatLng g,String t,int d){
        this.geo=g;
        this.type=t;
        this.id=d;
    }
}
