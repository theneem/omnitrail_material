package com.thenneem.omnitrail.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.thenneem.omnitrail.R;
import com.thenneem.omnitrail.adapter.TempleAdaptor;
import com.thenneem.omnitrail.model.Temple;
import com.thenneem.omnitrail.rest.ApiClient;
import com.thenneem.omnitrail.rest.ApiInterface;
import com.thenneem.omnitrail.ui.temple.TempleFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TempleMapsFragment extends Fragment {
    List<Temple> rl;
    GoogleMap mGoogleMap;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {

            /*
            LatLng sydney = new LatLng(-34, 151);
            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
*/

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }

            googleMap.setMyLocationEnabled(true); // false to disable

            googleMap.getUiSettings().setZoomControlsEnabled(true);

            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            googleMap.getMinZoomLevel();



        googleMap.getUiSettings().setAllGesturesEnabled(true);
    googleMap.getUiSettings().setRotateGesturesEnabled(true);

                    mGoogleMap = googleMap;


        }
    };

    public static TempleMapsFragment newInstance() {
        return new TempleMapsFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_temple_maps, container, false);


// moving api call logic to sperate function

        getTempleData();




        return root;
    }

    public void getTempleData()
    {


        // calling json retrofit
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        String rid = getArguments().getString("rid");
        Call<List<Temple>> call;
        if(getArguments().containsKey("query")){
            String query = getArguments().getString("query");
            call = apiService.templateSearch(rid, query);
        }else if(getArguments().containsKey("radius")){
            String radius = getArguments().getString("radius");
            String lat = getArguments().getString("lat");
            String lon = getArguments().getString("lon");
            call = apiService.templateGeoSearch(rid, lon, lat, radius);
        }else{
            call = apiService.getTempleList(rid);
        }

        //Call<List<Temple>> call = apiService.getTempleList("1");


        //
        //if(call == null) return null;

        call.enqueue(new Callback<List<Temple>>() {
            @Override
            public void onResponse(Call<List<Temple>> call, Response<List<Temple>> response) {


                rl = response.body();


                for (int i=0; i<rl.size(); i++) {

                    Toast.makeText(getContext(), rl.get(i).getTempleName(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(), Double.toString ( rl.get(i).getLang()), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(), Double.toString ( rl.get(i).getLat()), Toast.LENGTH_SHORT).show();


                    LatLng tmpld = new LatLng(rl.get(i).getLat(), rl.get(i).getLang());


                    Marker locationMarker = mGoogleMap.addMarker(new MarkerOptions().position(tmpld).title(rl.get(i).getTempleName()));
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(tmpld));

                    mGoogleMap.animateCamera(CameraUpdateFactory.zoomIn());
                    // Zoom out to zoom level 10, animating with a duration of 2 seconds.
                    mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);




                    locationMarker.showInfoWindow();

                }

                //recyclerView.setAdapter(new TempleAdaptor(rl,R.layout.templelist_layout,getContext()));


            }

            @Override
            public void onFailure(Call<List<Temple>> call, Throwable t) {
                //Log.d(TAG , t.toString());
                Toast.makeText(getContext(), "Error" +  t.toString(),Toast.LENGTH_LONG).show();
            }
        });

        //


    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}