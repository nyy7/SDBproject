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
 * Created by yanglingou on 12/1/15.
 */
public class heatxmlparser extends AsyncTask<String,Void,List<LatLng>> {

    List<LatLng> geo=new ArrayList<>();
    List<Double> lng=new ArrayList<>();
    List<Double> lat=new ArrayList<>();

    @Override
    protected List<LatLng> doInBackground(String... urls) {
        try {


            URL url = new URL(urls[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            InputStream in=new BufferedInputStream(con.getInputStream());

            XmlPullParserFactory pullParserFactory;
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);


            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    // Modify the tags

                    if (parser.getName().equals("lng")) {
                        String lngstr = parser.nextText();
                        this.lng.add(Double.parseDouble(lngstr));
                    } else if (parser.getName().equals("lat")) {
                        String latstr = parser.nextText();
                        this.lat.add(Double.parseDouble(latstr));

                    }

                }

                eventType = parser.next();
            }



        }catch(Exception e){
            Log.e("error:", "Parse Error!");

        }

        if(this.lng.size()!=this.lat.size()){
            Log.i("error:","lng is not equal to lat");

        }else{
            for(int i=0;i<lng.size();i++){
                LatLng g=new LatLng(this.lat.get(i),this.lng.get(i));

                this.geo.add(g);

            }

        }

        return this.geo;





    }

}
