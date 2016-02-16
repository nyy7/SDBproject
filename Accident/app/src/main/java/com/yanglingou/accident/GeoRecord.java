package com.yanglingou.accident;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by yanglingou on 12/6/15.
 */
public class GeoRecord {
    public LatLng geo;
    public String type;
    public int id;
    public String url;
    GeoRecord(LatLng g,String t,int d,String u){
        this.geo=g;
        this.type=t;
        this.id=d;
        this.url=u;
    }
}
