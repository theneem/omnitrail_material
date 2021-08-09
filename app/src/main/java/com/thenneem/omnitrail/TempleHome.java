package com.thenneem.omnitrail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.thenneem.omnitrail.model.Temple;
import com.thenneem.omnitrail.ui.templedetail.TempleDetailFragment;
import com.thenneem.omnitrail.ui.templefeature.TempleFeatureFragment;
import com.thenneem.omnitrail.ui.templereview.TempleReviewFragment;

import java.util.Objects;

public class TempleHome extends AppCompatActivity {

    private Temple temple;
    private MaterialToolbar toolbar;
    BottomNavigationView bottomNavigation;
    ImageView imgHead;
    TextView txtSubTag ;

    AppBarLayout appBarLayout;
    boolean isImageFitToScreen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temple_home);

        toolbar = findViewById(R.id.toptoolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        imgHead = findViewById(R.id.backdrop);
        appBarLayout = findViewById(R.id.appbar);



        imgHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int height = displayMetrics.heightPixels;
                int width = displayMetrics.widthPixels;
                int  appbarHeight = (int)  getResources().getDimension(R.dimen.appbar_height);



                if(isImageFitToScreen) {
                    isImageFitToScreen=false;
                    //imgHead.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    appBarLayout.setLayoutParams(new CoordinatorLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,appbarHeight));

                    imgHead.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));

                    imgHead.setAdjustViewBounds(true);
                    imgHead.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imgHead.setTooltipText("Click to Maximize");

                }else{
                    isImageFitToScreen=true;

                    appBarLayout.setLayoutParams(new CoordinatorLayout.LayoutParams(width,height));
                    imgHead.setLayoutParams(new ConstraintLayout.LayoutParams(width,height));

                    imgHead.setAdjustViewBounds(true);
                    imgHead.setTooltipText("Click to Minimize");

                    imgHead.setScaleType(ImageView.ScaleType.FIT_START);

                }
            }
        });


        AppBarLayout apbar ;
        apbar = findViewById(R.id.app_bar_layout);

        //toolbar.setTitle(temple.getReligionName());
        apbar.addOnOffsetChangedListener(new AppBarLayout.BaseOnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (Math.abs(verticalOffset)-appBarLayout.getTotalScrollRange() == 0)
                {
                    //  Collapsed
                    toolbar.setTitleTextColor(getResources().getColor(android.R.color.black));
                    toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
                }
                else
                {
                    //Expanded
                    toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
                    toolbar.setNavigationIcon(R.drawable.ic_back);
                }

            }
        });



        getIncomingIntent();
        openFragment(TempleDetailFragment.newInstance());


        toolbar.animate();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });


    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        // NEED TO GET RELIGION ID TO PASS
        bundle.putString("TempleId", String.valueOf(temple.getTempleID()));
        bundle.putString("TempleName", String.valueOf(temple.getTempleName()));
        bundle.putString("templeImg", String.valueOf(temple.getTempleIMG()));
        bundle.putString("templeAdd",temple.getAddress());
        bundle.putString("templeCity",temple.getCity_name());
        bundle.putString("templeState",temple.getState_name());
        bundle.putString("templeCountry",temple.getCountry_name());
        bundle.putString("templeStory",temple.getTempleStory());
        bundle.putString("templeDeity",temple.getPrimaryDeity());
        bundle.putString("templeGoverning",temple.getGoverningBody());
        bundle.putString("templeContactPerson",temple.getContactPerson());
        bundle.putString("templeContactNumber",temple.getContactNumber());
        bundle.putString("templeCreator",temple.getCreator());
        bundle.putString("templeCompletionPeriod",temple.getCompletionPerios());
        bundle.putString("templeWiki",temple.getWiki_link());
        fragment.setArguments(bundle);
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            switch (item.getItemId()) {
                case R.id.navigation_temple_detail:
                    openFragment(TempleDetailFragment.newInstance());

                    item.setChecked(true);
                    return true;
                case R.id.navigain_temple_review:
                    openFragment(TempleReviewFragment.newInstance());
                    item.setChecked(true);
                    return true;
                case R.id.navigation_temple_feature:
                    openFragment(TempleFeatureFragment.newInstance());
                    item.setChecked(true);
                    return true;
            }
            return false;
        }
    };


    public  void getIncomingIntent(){

        temple = (Temple)  getIntent().getSerializableExtra("Temple");
        txtSubTag = findViewById(R.id.textViewSubTag);
        txtSubTag.setText(temple.getTempleName());
        toolbar.setTitle(temple.getReligionName());

        ImageView imgtopbar = findViewById(R.id.backdrop);

        Picasso.Builder builder = new Picasso.Builder(getApplicationContext());
        builder.build().load(temple.getTempleIMG())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_foreground)
                .into(imgtopbar, new Callback() {
                    @Override
                    public void onSuccess() {                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(getApplicationContext(), "Piccaso Error "  + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }


}