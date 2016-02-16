package com.yanglingou.accident;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.ui.IconGenerator;

import java.util.List;

public class ClusterMapActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private ClusterManager<ClusterMapItem> mClusterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cluster_map);
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

        setUpClusterer();
    }

    private void setUpClusterer() {
        // Declare a variable for the cluster manager.


        // Position the map.


        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<ClusterMapItem>(this, mMap);

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        mMap.setOnCameraChangeListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);

        // Add cluster items (markers) to the cluster manager.
        // String url="http://52.24.92.50/cgi-bin/DataAnalytics/getmarkers.cgi";
        Intent intent=getIntent();

        String url= intent.getStringExtra(MapForm.EXTRA_uri);
        String mile = intent.getStringExtra(MapForm.EXTRA_mile);
        Log.d("mile",mile);

        int scopelevel=1;

        int  mileint=Integer.parseInt(mile);

        if (mileint<=50)
            scopelevel=10;
        else if (mileint<=200)
            scopelevel=7;
        else if (mileint<=500)
            scopelevel=5;
        else if (mileint>=800)
            scopelevel=3;




        Log.d("url", url);
        try {
            xmlParser xp = new xmlParser();
            xp.execute(url);
            geoInfo geo= xp.get();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(geo.getUserPosition(), scopelevel));
            mMap.addMarker(new MarkerOptions().position(geo.getUserPosition()).title("Interested Position"));
            List<GeoRecord> geos=geo.getGeoList();

            IconGenerator factory=new IconGenerator(this);
            Bitmap iconBitmap=null;


            for(int i=0;i<geos.size();i++){
                ClusterMapItem item = new ClusterMapItem(geos.get(i).geo.latitude,geos.get(i).geo.longitude);


                mClusterManager.addItem(item);


            }








        }catch (Exception e){
            e.printStackTrace();

        }
    }
}
