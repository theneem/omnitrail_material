package com.thenneem.omnitrail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.thenneem.omnitrail.model.Temple;
import com.thenneem.omnitrail.ui.templedetail.TempleDetailFragment;
import com.thenneem.omnitrail.ui.templefeature.TempleFeatureFragment;
import com.thenneem.omnitrail.ui.templereview.TempleReviewFragment;

public class TempleHome extends AppCompatActivity {

    private Temple temple;
    Toolbar toolbar;
    BottomNavigationView bottomNavigation;
    ImageView imgHead;

    AppBarLayout appBarLayout;
    boolean isImageFitToScreen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temple_home);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);

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
                    //imgHead.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

                    //appBarLayout.setLayoutParams(new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT,CoordinatorLayout.LayoutParams.MATCH_PARENT));
                    //imgHead.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,ConstraintLayout.LayoutParams.MATCH_PARENT));

                    appBarLayout.setLayoutParams(new CoordinatorLayout.LayoutParams(width,height));
                    imgHead.setLayoutParams(new ConstraintLayout.LayoutParams(width,height));

                    imgHead.setAdjustViewBounds(true);
                    imgHead.setTooltipText("Click to Minimize");

                    //imgHead.setScaleType(ImageView.ScaleType.FIT_XY);
                    imgHead.setScaleType(ImageView.ScaleType.FIT_START);



                }
            }
        });




        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getIncomingIntent();
        openFragment(TempleDetailFragment.newInstance());


        // initiate and perform click event on button's
        ImageButton search = findViewById(R.id.imgbtnMax);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Toast.makeText(getApplicationContext(), "Show some text on the screen.", Toast.LENGTH_LONG).show();


                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int height = displayMetrics.heightPixels;
                int width = displayMetrics.widthPixels;

                imgHead.setMaxHeight(height);
                imgHead.setMaxWidth(width);


            }
        });



    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        // NEED TO GET RELIGION ID TO PASS
        bundle.putString("TempleId", String.valueOf(temple.getTempleID()));
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

        //toolbar.setTitle(temple.getReligionName() + " -> " +  temple.getTempleName());
        toolbar.setTitle(temple.getTempleName());

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