package com.thenneem.omnitrail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class TrailDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private BottomSheetBehavior mBottomSheetBehavior;
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail_detail);

      //  View bottomSheet = findViewById(R.id.bottomSheetScroll);
        mapView = findViewById(R.id.mapView);
        mapView.getMapAsync(this);
        mapView.onCreate(savedInstanceState);
//        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
//        mBottomSheetBehavior.setPeekHeight(300);
//        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
     }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

    }
}