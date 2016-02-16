package com.yanglingou.accident;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by yanglingou on 12/6/15.
 */
public class ClusterMapItem implements ClusterItem {

        private final LatLng mPosition;

        public ClusterMapItem(double lat, double lng) {
            mPosition = new LatLng(lat, lng);
        }

        @Override
        public LatLng getPosition() {
            return mPosition;
        }

}
