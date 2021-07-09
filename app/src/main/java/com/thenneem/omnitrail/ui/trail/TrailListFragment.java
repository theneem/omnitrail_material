package com.thenneem.omnitrail.ui.trail;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.thenneem.omnitrail.R;
import com.thenneem.omnitrail.TrailActivity;
import com.thenneem.omnitrail.TrailDetailActivity;
import com.thenneem.omnitrail.adapter.RecyclerItemClickListner;
import com.thenneem.omnitrail.adapter.TrailAdapter;
import com.thenneem.omnitrail.model.TrailMaster;
import com.thenneem.omnitrail.retrofit.ApiClient;
import com.thenneem.omnitrail.retrofit.ApiInterface;
import com.thenneem.omnitrail.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrailListFragment extends Fragment {
    private RecyclerView trailRecycler;
    private List<TrailMaster> master;
    private TrailAdapter trailAdapter;
    View view;

    public TrailListFragment() {
        // Required empty public constructor
    }

    public static TrailListFragment newInstance()
    {
        return new TrailListFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_trail_list, container, false);
        trailRecycler = view.findViewById(R.id.trail_list);
        trailAdapter = new TrailAdapter(getActivity(), master);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        trailRecycler.setLayoutManager(layoutManager);
        trailRecycler.setItemAnimator(new DefaultItemAnimator());
        getTrails();

        trailRecycler.addOnItemTouchListener(new RecyclerItemClickListner(getActivity(), trailRecycler, new RecyclerItemClickListner.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), TrailDetailActivity.class);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
        return view;
    }

    public void getTrails() {
        if (Utils.isConnected(getActivity())) {
            Utils.showLoader(getActivity());
            final ApiInterface api = ApiClient.getApiService();
            Call<List<TrailMaster>> call = api.getTrails();
            call.enqueue(new Callback<List<TrailMaster>>() {
                @Override
                public void onResponse(Call<List<TrailMaster>> call, Response<List<TrailMaster>> response) {
                    if (response.code() == 200) {
                        if (response.body() != null) {
                            Utils.hideLoader(getActivity());
                            master = response.body();
                            trailAdapter = new TrailAdapter(getActivity(), master);
                            trailRecycler.setAdapter(trailAdapter);
                        } else {
                            Utils.hideLoader(getActivity());
                            Toast.makeText(getActivity(), "Response body is null", Toast.LENGTH_LONG).show();
                        }
                    } else if (response.code() == 401) {
                        Utils.hideLoader(getActivity());
                        // Handle unauthorized
                    } else if (response.code() == 404) {
                        Utils.hideLoader(getActivity());
                        Toast.makeText(getActivity(), "Not Found (From ServerSide Error)", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 500) {
                        Utils.hideLoader(getActivity());
                        Toast.makeText(getActivity(), "Server Broken", Toast.LENGTH_SHORT).show();
                    } else {
                        Utils.hideLoader(getActivity());
                        Toast.makeText(getActivity(), "Unknown Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<TrailMaster>> call, Throwable t) {
                    Utils.hideLoader(getActivity());
                    Utils.hideLoader(getActivity());
                    Toast.makeText(getActivity(), "onFailure:  " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Utils.showErrorSnackBar(view.findViewById(android.R.id.content), "Please Turn ON Internet first!!!");
        }
    }
}