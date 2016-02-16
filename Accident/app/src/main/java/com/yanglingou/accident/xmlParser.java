package com.yanglingou.accident;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanglingou on 11/15/15.
 */
public class xmlParser extends AsyncTask<String,Void,geoInfo> {

    geoInfo geo=new geoInfo();
    List<Double> lng=new ArrayList<>();
    List<Double> lat=new ArrayList<>();
    List<String> type=new ArrayList<>();
    List<String> url=new ArrayList<>();
    List<Integer> id=new ArrayList<>();
    Double userlng;
    Double userlat;

    @Override
    protected geoInfo doInBackground(String... urls) {
        try {


            URL url = new URL(urls[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            InputStream in=new BufferedInputStream(con.getInputStream());
            Log.d("xml", in.toString());

            XmlPullParserFactory pullParserFactory;
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);


            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    // Modify the tags

                    if (parser.getName().equals("UserLng")) {
                        String userlngstr = parser.nextText();
                        this.userlng=Double.parseDouble(userlngstr);
                    }else if (parser.getName().equals("UserLat")){
                        String userlatstr = parser.nextText();
                        this.userlat=Double.parseDouble(userlatstr);

                    }

                    else if (parser.getName().equals("lng")) {
                        String lngstr = parser.nextText();
                        this.lng.add(Double.parseDouble(lngstr));
                    } else if (parser.getName().equals("lat")) {
                        String latstr = parser.nextText();
                        this.lat.add(Double.parseDouble(latstr));

                    }else if (parser.getName().equals("name")) {
                        String namestr = parser.nextText();
                        this.type.add(namestr);
                    }else if (parser.getName().equals("id")) {
                        String idstr = parser.nextText();
                        this.id.add(Integer.parseInt(idstr));
                    }else if (parser.getName().equals("url")) {
                        String urlstr = parser.nextText();
                        this.url.add(urlstr);
                    }

                }

                eventType = parser.next();
            }



        }catch(Exception e){
            Log.e("error:","Parse Error!");

        }

        if(this.lng.size()!=this.lat.size()){
            Log.i("error:","lng is not equal to lat");

        }else{
            for(int i=0;i<lng.size();i++){
                LatLng g=new LatLng(this.lat.get(i),this.lng.get(i));

                this.geo.addGeoRecord(new GeoRecord(g, type.get(i), id.get(i),url.get(i)));

            }

        }
        LatLng up=new LatLng(this.userlat,this.userlng);

        this.geo.setUserPosition(up);


        return this.geo;





    }






 }

