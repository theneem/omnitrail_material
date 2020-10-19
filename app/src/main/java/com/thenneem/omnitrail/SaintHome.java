package com.thenneem.omnitrail;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.thenneem.omnitrail.model.Saint;
import com.thenneem.omnitrail.ui.book.BookFragment;
import com.thenneem.omnitrail.ui.saint.SaintFragment;
import com.thenneem.omnitrail.ui.saintdetail.SaintDetailFragment;
import com.thenneem.omnitrail.ui.saintquality.SaintQualityFragment;
import com.thenneem.omnitrail.ui.saintreview.SaintReviewFragment;
import com.thenneem.omnitrail.ui.temple.TempleFragment;
import com.thenneem.omnitrail.ui.templedetail.TempleDetailFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;




import java.security.cert.CertificateNotYetValidException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SaintHome extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;


    private MaterialToolbar topToolBar;
    private Saint saint;
    ImageView imgSThumb;
    Toolbar toolbar;
    BottomNavigationView bottomNavigation;

    ImageView imgHead;

    AppBarLayout appBarLayout;
    boolean isImageFitToScreen;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;

    private View mControlsView;

    private boolean mVisible;

    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saint_home);


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





        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        getIncomingIntent();




    }


    public  void getIncomingIntent(){
        //mContentView.setText




        saint = (Saint)  getIntent().getSerializableExtra("Saint");
        TextView txtName = findViewById(R.id.fullscreen_content);

        imgSThumb = findViewById(R.id.backdrop);

        Picasso.Builder builder = new Picasso.Builder(getApplicationContext());
        builder.build().load(saint.getSaintIMG())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_foreground)
                .into(imgSThumb, new Callback() {
                    @Override
                    public void onSuccess() {                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(getApplicationContext(), "Piccaso Error "  + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


        //topToolBar.setTitle(  "-> " +   saint.getSaintName());
        toolbar.setTitle( saint.getReligionName() +  "-> " +   saint.getSaintName());






        openFragment(SaintDetailFragment.newInstance());


        //Toast.makeText(this, "R Name " + religion.getReligionName(), Toast.LENGTH_SHORT).show();
    }



    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        // NEED TO GET RELIGION ID TO PASS
        bundle.putString("SaintId", String.valueOf(  saint.getSaintID()));
        fragment.setArguments(bundle);
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    BottomNavigationView.OnNavigationItemSelectedListener   navigationItemSelectedListener    = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            switch (item.getItemId()) {
                case R.id.navigation_saint_detail:
                    openFragment(SaintDetailFragment.newInstance());
                    return true;
                case R.id.navigain_saint_review:
                    openFragment(SaintReviewFragment.newInstance());
                    return true;
                case R.id.navigation_saint_quality:
                    openFragment(SaintQualityFragment.newInstance());
                    return true;

            }


            return false;
        }
    };




    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            //hide();
        } else {
            //show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        //mHideHandler.removeCallbacks(mShowPart2Runnable);
        //mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
       // mHideHandler.removeCallbacks(mHidePart2Runnable);
       // mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        // mHideHandler.removeCallbacks(mHideRunnable);
        // mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
