package com.thenneem.omnitrail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;


import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.thenneem.omnitrail.adapter.TrailAdapter;
import com.thenneem.omnitrail.adapter.TrailChildAdapter;
import com.thenneem.omnitrail.model.TrailChild;
import com.thenneem.omnitrail.retrofit.ApiClient;
import com.thenneem.omnitrail.retrofit.ApiInterface;
import com.thenneem.omnitrail.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TrailDetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    RecyclerView trailChildRecycler;
    private List<TrailChild> trailChildList;
    private ArrayList<LatLng> temples;
    private TrailChildAdapter childAdapter;
    private BottomSheetBehavior mBottomSheetBehavior;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail_detail);

        View bottomSheet = findViewById(R.id.bottomSheetScroll);
        trailChildRecycler = findViewById(R.id.TempleMapRecycler);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setPeekHeight(250);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        childAdapter = new TrailChildAdapter(this, trailChildList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TrailDetailActivity.this);
        trailChildRecycler.setLayoutManager(layoutManager);
        trailChildRecycler.setItemAnimator(new DefaultItemAnimator());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        getTrailChildren(1);
    }

    public void getTrailChildren(int trailId) {
        if (Utils.isConnected(TrailDetailActivity.this)) {
            Utils.showLoader(TrailDetailActivity.this);
            final ApiInterface api = ApiClient.getApiService();
            Call<List<TrailChild>> call = api.getTrailChildren(trailId);
            call.enqueue(new Callback<List<TrailChild>>() {
                @Override
                public void onResponse(Call<List<TrailChild>> call, Response<List<TrailChild>> response) {
                    if (response.code() == 200) {
                        if (response.body() != null) {
                            Utils.hideLoader(TrailDetailActivity.this);
                            trailChildList = response.body();
                            childAdapter = new TrailChildAdapter(TrailDetailActivity.this, trailChildList);
                            trailChildRecycler.setAdapter(childAdapter);
                            for (int i = 0; i < trailChildList.size(); i++) {
                                Double lat = Double.parseDouble(trailChildList.get(i).getLat());
                                Double lang = Double.parseDouble(trailChildList.get(i).getLang());
                                String place = trailChildList.get(i).getTempleName();
                                LatLng latLng = new LatLng(lat, lang);
                                mMap.addMarker(new MarkerOptions()
                                        .position(latLng)
                                        .title(place));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
                            }
                        } else {
                            Utils.hideLoader(TrailDetailActivity.this);
                            System.out.println(response.body());
                            Toast.makeText(TrailDetailActivity.this, "Response body is null" + response.body(), Toast.LENGTH_LONG).show();
                        }
                    } else if (response.code() == 401) {
                        Utils.hideLoader(TrailDetailActivity.this);
                        // Handle unauthorized
                    } else if (response.code() == 404) {
                        Utils.hideLoader(TrailDetailActivity.this);
                        Toast.makeText(TrailDetailActivity.this, "Not Found (From ServerSide Error)", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 500) {
                        Utils.hideLoader(TrailDetailActivity.this);
                        Toast.makeText(TrailDetailActivity.this, "Server Broken", Toast.LENGTH_SHORT).show();
                    } else {
                        Utils.hideLoader(TrailDetailActivity.this);
                        Toast.makeText(TrailDetailActivity.this, "Unknown Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<TrailChild>> call, Throwable t) {
                    Utils.hideLoader(TrailDetailActivity.this);
                    Utils.hideLoader(TrailDetailActivity.this);
                    System.out.println("onFailure:  " + t.getMessage());
                    Toast.makeText(TrailDetailActivity.this, "onFailure:  " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Utils.showErrorSnackBar(findViewById(android.R.id.content), "Please Turn ON Internet first!!!");
        }
    }

    public void getTempleLocations() {
        temples = new ArrayList<>();

        for (int i = 0; i < trailChildList.size(); i++) {
            temples.add(new LatLng(Double.parseDouble(trailChildList.get(i).getLat()), Double.parseDouble(trailChildList.get(i).getLang())));
        }
    }
}