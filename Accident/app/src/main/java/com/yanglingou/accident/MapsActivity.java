package com.yanglingou.accident;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import java.util.List;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            mMap.getUiSettings().setZoomControlsEnabled(true);
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
       // String url="http://52.24.92.50/cgi-bin/DataAnalytics/getmarkers.cgi";
        Intent intent=getIntent();

        String url= intent.getStringExtra(MapForm.EXTRA_uri);
        Log.d("url", url);
        try {
            xmlParser xp = new xmlParser();
            xp.execute(url);
            geoInfo geo= xp.get();

            List<GeoRecord> geos=geo.getGeoList();
            LatLng userposition=geo.getUserPosition();

            mMap.addMarker(new MarkerOptions().position(userposition).title("Interested Position"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userposition,7));


            IconGenerator factory=new IconGenerator(this);
            Bitmap iconBitmap=null;


            for(int i=0;i<geos.size();i++){
                switch (geos.get(i).id) {
                    case 1:

                        factory.setColor(Color.parseColor("red"));
                        iconBitmap=factory.makeIcon("1");
                        mMap.addMarker(new MarkerOptions().position(geos.get(i).geo)
                                .icon(BitmapDescriptorFactory.fromBitmap(iconBitmap)).title(geos.get(i).type).snippet(geos.get(i).url));
                        break;
                    case 2:
                        factory.setColor(Color.parseColor("blue"));
                        iconBitmap=factory.makeIcon("2");
                        mMap.addMarker(new MarkerOptions().position(geos.get(i).geo)
                                .icon(BitmapDescriptorFactory.fromBitmap(iconBitmap)).title(geos.get(i).type).snippet(geos.get(i).url));
                        break;
                    case 3:
                        factory.setColor(Color.parseColor("green"));
                        iconBitmap=factory.makeIcon("3");
                        mMap.addMarker(new MarkerOptions().position(geos.get(i).geo)
                                .icon(BitmapDescriptorFactory.fromBitmap(iconBitmap)).title(geos.get(i).type).snippet(geos.get(i).url));
                        break;

                    case 4:
                        factory.setColor(Color.parseColor("black"));
                        iconBitmap=factory.makeIcon("4");
                        mMap.addMarker(new MarkerOptions().position(geos.get(i).geo)
                                .icon(BitmapDescriptorFactory.fromBitmap(iconBitmap)).title(geos.get(i).type).snippet(geos.get(i).url));
                        break;

                    case 5:
                        factory.setColor(Color.parseColor("white"));
                        iconBitmap=factory.makeIcon("5");
                        mMap.addMarker(new MarkerOptions().position(geos.get(i).geo)
                                .icon(BitmapDescriptorFactory.fromBitmap(iconBitmap)).title(geos.get(i).type).snippet(geos.get(i).url));
                        break;

                    case 6:

                        factory.setColor(Color.parseColor("gray"));
                        iconBitmap=factory.makeIcon("6");
                        mMap.addMarker(new MarkerOptions().position(geos.get(i).geo)
                                .icon(BitmapDescriptorFactory.fromBitmap(iconBitmap)).title(geos.get(i).type).snippet(geos.get(i).url));
                        break;
                    case 7:

                        factory.setColor(Color.parseColor("cyan"));
                        iconBitmap=factory.makeIcon("7");
                        mMap.addMarker(new MarkerOptions().position(geos.get(i).geo)
                                .icon(BitmapDescriptorFactory.fromBitmap(iconBitmap)).title(geos.get(i).type).snippet(geos.get(i).url));
                        break;
                    case 8:

                        factory.setColor(Color.parseColor("yellow"));
                        iconBitmap=factory.makeIcon("8");
                        mMap.addMarker(new MarkerOptions().position(geos.get(i).geo)
                                .icon(BitmapDescriptorFactory.fromBitmap(iconBitmap)).title(geos.get(i).type).snippet(geos.get(i).url));
                        break;
                    case 9:

                        factory.setColor(Color.parseColor("purple"));
                        iconBitmap=factory.makeIcon("9");
                        mMap.addMarker(new MarkerOptions().position(geos.get(i).geo)
                                .icon(BitmapDescriptorFactory.fromBitmap(iconBitmap)).title(geos.get(i).type).snippet(geos.get(i).url));
                        break;
                    case 10:

                        factory.setColor(Color.parseColor("silver"));
                        iconBitmap=factory.makeIcon("10");
                        mMap.addMarker(new MarkerOptions().position(geos.get(i).geo)
                                .icon(BitmapDescriptorFactory.fromBitmap(iconBitmap)).title(geos.get(i).type).snippet(geos.get(i).url));
                        break;
                    case 11:

                        factory.setColor(Color.parseColor("olive"));
                        iconBitmap=factory.makeIcon("11");
                        mMap.addMarker(new MarkerOptions().position(geos.get(i).geo)
                                .icon(BitmapDescriptorFactory.fromBitmap(iconBitmap)).title(geos.get(i).type).snippet(geos.get(i).url));
                        break;
                    case 12:

                        factory.setColor(Color.parseColor("fuchsia"));
                        iconBitmap=factory.makeIcon("12");
                        mMap.addMarker(new MarkerOptions().position(geos.get(i).geo)
                                .icon(BitmapDescriptorFactory.fromBitmap(iconBitmap)).title(geos.get(i).type).snippet(geos.get(i).url));
                        break;
                    case 13:

                        factory.setColor(Color.parseColor("navy"));
                        iconBitmap=factory.makeIcon("13");
                        mMap.addMarker(new MarkerOptions().position(geos.get(i).geo)
                                .icon(BitmapDescriptorFactory.fromBitmap(iconBitmap)).title(geos.get(i).type).snippet(geos.get(i).url));
                        break;


                }

            }







        }catch (Exception e){
            e.printStackTrace();

        }

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                String url = marker.getSnippet();
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });

    }




}
