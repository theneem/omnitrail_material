package com.thenneem.omnitrail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.thenneem.omnitrail.adapter.RecyclerItemClickListner;
import com.thenneem.omnitrail.adapter.TrailAdapter;
import com.thenneem.omnitrail.model.TrailMaster;
import com.thenneem.omnitrail.retrofit.ApiClient;
import com.thenneem.omnitrail.retrofit.ApiInterface;
import com.thenneem.omnitrail.utils.Utils;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrailActivity extends AppCompatActivity {
    RecyclerView trailRecycler;
    private List<TrailMaster> master;
    private TrailAdapter trailAdapter;
    private MaterialToolbar topToolBar;
    private SearchView searchView;
    private final String hint = "Search Trail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail);

        topToolBar = findViewById(R.id.trailToolbar);
        trailRecycler = findViewById(R.id.trailRecycler);
        topToolBar.setNavigationIcon(R.drawable.ic_back);
        topToolBar.animate();
        Menu menu = topToolBar.getMenu();
        MenuItem searchItem = menu.findItem(R.id.trail_search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(hint);;
        trailAdapter = new TrailAdapter(this, master);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TrailActivity.this);
        trailRecycler.setLayoutManager(layoutManager);
        trailRecycler.setItemAnimator(new DefaultItemAnimator());
        getTrails();

        topToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                showSearchResult(query);
                return false;
            }
        });

        trailRecycler.addOnItemTouchListener(new RecyclerItemClickListner(this, trailRecycler, new RecyclerItemClickListner.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(TrailActivity.this,TrailDetailActivity.class);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
    }

    void showSearchResult(String query){
        List<TrailMaster> trails = new ArrayList<>();
        for (TrailMaster t: master){
            if (t.getTrailName().toLowerCase().contains(query.toLowerCase())){
                trails.add(t);
            }
        }
        trailAdapter.updateList(trails);
    }
    public void getTrails() {
        if (Utils.isConnected(TrailActivity.this)) {
            Utils.showLoader(TrailActivity.this);
            final ApiInterface api = ApiClient.getApiService();
            Call<List<TrailMaster>> call = api.getTrails();
            call.enqueue(new Callback<List<TrailMaster>>() {
                @Override
                public void onResponse(Call<List<TrailMaster>> call, Response<List<TrailMaster>> response) {
                    if (response.code() == 200) {
                        if (response.body() != null) {
                            Utils.hideLoader(TrailActivity.this);
                            master = response.body();
                            trailAdapter = new TrailAdapter(TrailActivity.this, master);
                            trailRecycler.setAdapter(trailAdapter);
                        } else {
                            Utils.hideLoader(TrailActivity.this);
                            Toast.makeText(TrailActivity.this, "Response body is null", Toast.LENGTH_LONG).show();
                        }
                    } else if (response.code() == 401) {
                        Utils.hideLoader(TrailActivity.this);
                        // Handle unauthorized
                    } else if (response.code() == 404) {
                        Utils.hideLoader(TrailActivity.this);
                        Toast.makeText(TrailActivity.this, "Not Found (From ServerSide Error)", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 500) {
                        Utils.hideLoader(TrailActivity.this);
                        Toast.makeText(TrailActivity.this, "Server Broken", Toast.LENGTH_SHORT).show();
                    } else {
                        Utils.hideLoader(TrailActivity.this);
                        Toast.makeText(TrailActivity.this, "Unknown Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<TrailMaster>> call, Throwable t) {
                    Utils.hideLoader(TrailActivity.this);
                    Utils.hideLoader(TrailActivity.this);
                    Toast.makeText(TrailActivity.this, "onFailure:  " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Utils.showErrorSnackBar(findViewById(android.R.id.content), "Please Turn ON Internet first!!!");
        }
    }
}