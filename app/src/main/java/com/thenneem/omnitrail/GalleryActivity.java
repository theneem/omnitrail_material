package com.thenneem.omnitrail;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thenneem.omnitrail.adapter.ImageAdapter;
import com.thenneem.omnitrail.model.TempleImages;
import com.thenneem.omnitrail.retrofit.ApiClient;
import com.thenneem.omnitrail.retrofit.ApiInterface;
import com.thenneem.omnitrail.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class    GalleryActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerImages;
    private GridLayoutManager gridLayoutManager;
    private List<TempleImages> images;
    private ImageAdapter imageAdapter;
    String strName;
    Integer intId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        strName = bundle.getString("templename");
        intId = Integer.parseInt(bundle.getString("templeid"));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(strName + "'s Gallery");
        }

        recyclerImages = findViewById(R.id.imageRecycler);
        gridLayoutManager = new GridLayoutManager(GalleryActivity.this, 3);
        recyclerImages.setLayoutManager(gridLayoutManager);
        getImages();
    }

    public void getImages() {
        if (Utils.isConnected(GalleryActivity.this)) {
            images = new ArrayList<>();
            Utils.showLoader(GalleryActivity.this);
            final ApiInterface api = ApiClient.getApiService();
            Call<List<TempleImages>> call = api.getImages(intId);

            call.enqueue(new Callback<List<TempleImages>>() {
                @Override
                public void onResponse(Call<List<TempleImages>> call, Response<List<TempleImages>> response) {
                    if (response.code() == 200) {
                        if (response.body() != null) {
                            Utils.hideLoader(GalleryActivity.this);
                            images = response.body();
                            imageAdapter = new ImageAdapter(getApplicationContext(), images);
                            recyclerImages.setAdapter(imageAdapter);
                        } else {
                            Utils.hideLoader(GalleryActivity.this);
                            Toast.makeText(GalleryActivity.this, "Response body is null", Toast.LENGTH_LONG).show();
                        }
                    } else if (response.code() == 401) {
                        Utils.hideLoader(GalleryActivity.this);
                        // Handle unauthorized
                    } else if (response.code() == 404) {
                        Utils.hideLoader(GalleryActivity.this);
                        Toast.makeText(GalleryActivity.this, "Not Found (From Serverside Error)", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 500) {
                        Utils.hideLoader(GalleryActivity.this);
                        Toast.makeText(GalleryActivity.this, "Server Broken", Toast.LENGTH_SHORT).show();
                    } else {
                        Utils.hideLoader(GalleryActivity.this);
                        Toast.makeText(GalleryActivity.this, "Unknown Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<TempleImages>> call, Throwable t) {
                    Utils.hideLoader(GalleryActivity.this);
                    Toast.makeText(GalleryActivity.this, "onFailer:  " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Utils.showErrorSnackBar(findViewById(android.R.id.content), "Please Turn On Internet first!!!");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this PicassoClient and return to preview PicassoClient (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}