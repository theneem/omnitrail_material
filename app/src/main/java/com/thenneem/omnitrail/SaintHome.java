package com.thenneem.omnitrail;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    BottomNavigationView bottomNavigation;


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
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_saint_home);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);


        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);


        topToolBar = (com.google.android.material.appbar.MaterialToolbar ) findViewById(R.id.toptoolbar);



        topToolBar.setNavigationIcon(R.drawable.ic_back);


        topToolBar.setNavigationOnClickListener(new View.OnClickListener() {
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
        TextView txtName = (TextView) findViewById(R.id.fullscreen_content);

        imgSThumb = (ImageView) findViewById(R.id.imgSaint);

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


        topToolBar.setTitle( saint.getReligionName() +  "-> " +   saint.getSaintName());



        TextView txtSaintName = (TextView) findViewById(R.id.txtSaintName);
        txtSaintName.setText(saint.getSaintName());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date BirthDate = null;
        Date DeathDate = null;
        String strBirthDate = "";
        String strDeathDate = "";


        try {


            if(saint.getBirthDate() != null  )
                BirthDate = format.parse(saint.getBirthDate());
            if(saint.getDeathDate() != null )
                DeathDate = format.parse(saint.getDeathDate());

            format = new SimpleDateFormat("MMM dd, yyyy");

            if(saint.getBirthDate() != null  )
                strBirthDate = format.format(BirthDate);

            if(saint.getDeathDate() != null )
                strDeathDate = format.format(DeathDate);




        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }


        TextView txtLifeSpan = (TextView) findViewById(R.id.txtLifeSpan);

        if(strDeathDate != "")
             txtLifeSpan.setText(strBirthDate + " to " +  strDeathDate );
        else
            txtLifeSpan.setText(strBirthDate);





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
            hide();
        } else {
            show();
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
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
