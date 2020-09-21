package com.thenneem.omnitrail;

import android.Manifest;
import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.thenneem.omnitrail.model.Religion;
import com.thenneem.omnitrail.ui.book.BookFragment;
import com.thenneem.omnitrail.ui.saint.SaintFragment;
import com.thenneem.omnitrail.ui.temple.TempleFragment;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

import static androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
@RuntimePermissions
public class ReligionHome extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;

    @SuppressLint("MissingPermission")
    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    public void geoSearch(String radius){
        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if(location == null){
                new AlertDialog.Builder(ReligionHome.this)
                        .setMessage("Could not determine location")
                        .setPositiveButton("OK", null)
                        .show();
                return;
            }
            showLocationSearchResult(location, radius);
        });
    }

    public void showLocationSearchResult(Location location, String radius){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("rid", String.valueOf(religion.getReligionID()));
        bundle.putString("radius", radius);
        bundle.putString("lon", Double.toString(location.getLongitude()));
        bundle.putString("lat", Double.toString(location.getLatitude()));
        Fragment fragment = null;
        int itemId = bottomNavigation.getSelectedItemId();
        switch (itemId){
            case R.id.navigation_temple:
                fragment = TempleFragment.newInstance();
                break;
            case R.id.navigain_saint:
                fragment = SaintFragment.newInstance();
                break;
        }
        if(fragment != null){
            fragment.setArguments(bundle);
            transaction.replace(R.id.container, fragment);
            transaction.addToBackStack("searchGeo");
            transaction.commit();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ReligionHomePermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale(Manifest.permission.ACCESS_FINE_LOCATION)
    public void rationaleGEO(PermissionRequest request){
        new AlertDialog.Builder(this)
                .setPositiveButton("OK", (dialog, which) -> {
                    request.proceed();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    request.cancel();
                })
                .setCancelable(false)
                .setMessage("to use the search for objects closest to you, allow the application to get your geo position")
                .show();
    }


    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;
    private MaterialToolbar topToolBar;
    private Religion religion;
    private SearchView searchView;


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

        setContentView(R.layout.activity_religion_home);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        topToolBar = findViewById(R.id.toptoolbar);

        Menu menu = topToolBar.getMenu();
        MenuItem searchItem = menu.findItem(R.id.action_search);
        Menu submenu = menu.findItem(R.id.action_geo).getSubMenu();
        submenu.findItem(R.id.one).setOnMenuItemClickListener(item ->{
                    ReligionHomePermissionsDispatcher.geoSearchWithPermissionCheck(ReligionHome.this, "5");
                    return false;
                }
        );
        submenu.findItem(R.id.two).setOnMenuItemClickListener(item ->{
                    ReligionHomePermissionsDispatcher.geoSearchWithPermissionCheck(ReligionHome.this, "10");
                    return false;
                }
        );
        submenu.findItem(R.id.three).setOnMenuItemClickListener(item ->{
                    ReligionHomePermissionsDispatcher.geoSearchWithPermissionCheck(ReligionHome.this, "50");
                    return false;
                }
        );
        submenu.findItem(R.id.four).setOnMenuItemClickListener(item ->{
                    ReligionHomePermissionsDispatcher.geoSearchWithPermissionCheck(ReligionHome.this, "100");
                    return false;
                }
        );
        searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(hint);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);


        topToolBar.setNavigationIcon(R.drawable.ic_back);
        topToolBar.animate();


        topToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getApplicationContext(), FullscreenActivity.class);
                //startActivity(intent);

                finish();

            }
        });

        //searchView = findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                showSearchResult(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnCloseListener(() -> {
            getSupportFragmentManager().popBackStack("search", POP_BACK_STACK_INCLUSIVE);
            return false;
        });

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


        getIncomingIntent();

        openFragment(TempleFragment.newInstance());


    }


    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("rid", String.valueOf(religion.getReligionID()));
        fragment.setArguments(bundle);
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void showSearchResult(String query){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("rid", String.valueOf(religion.getReligionID()));
        bundle.putString("query", query);
        Fragment fragment = null;
        int itemId = bottomNavigation.getSelectedItemId();
        switch (itemId){
            case R.id.navigation_temple:
                fragment = TempleFragment.newInstance();
                break;
            case R.id.navigain_saint:
                fragment = SaintFragment.newInstance();
                break;
            case R.id.navigation_book:
                fragment = BookFragment.newInstance();
                break;
        }
        if(fragment != null){
            fragment.setArguments(bundle);
            transaction.replace(R.id.container, fragment);
            transaction.addToBackStack("search");
            transaction.commit();
        }
    }

    private String hint = "Search Temple";

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            boolean result = false;
            switch (item.getItemId()) {
                case R.id.navigation_temple:
                    openFragment(TempleFragment.newInstance());
                    item.setChecked(true);
                    //bottomNavigation.setItemTextColor(android.R.color.holo_red_dark);
                    hint = "Search Temple";
                    result = true;
                    break;
                case R.id.navigain_saint:
                    openFragment(SaintFragment.newInstance());
                    item.setChecked(true);
                    hint = "Search Saint";
                    result = true;
                    break;
                case R.id.navigation_book:
                    openFragment(BookFragment.newInstance());
                    item.setChecked(true);
                    hint = "Search Book";
                    result = true;
                    break;

            }
            searchView.setQueryHint(hint);
            return result;
        }
    };

    public void getIncomingIntent() {
        //mContentView.setText


        religion = (Religion) getIntent().getSerializableExtra("Religion");
        TextView txtName = findViewById(R.id.fullscreen_content);
        ImageView imgRThumb = findViewById(R.id.imgReligionThumb);

        txtName.setText(religion.getReligionName());
        topToolBar.setTitle(religion.getReligionName());

        Picasso.Builder builder = new Picasso.Builder(getApplicationContext());


        builder.build().load(religion.getHeaderimg())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_foreground)
                .into(imgRThumb, new Callback() {
                    @Override
                    public void onSuccess() {
                        //holder.mMediaEvidencePb.setVisibility(View.GONE);
                        //Log.d("test1","piccaso Success" );
                        //Toast.makeText(context, "Piccaso success ", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Exception e) {
                        //Log.d("test1","piccaso error" + e.getMessage() );
                        Toast.makeText(getApplicationContext(), "Piccaso Error " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


        //Toast.makeText(this, "R Name " + religion.getReligionName(), Toast.LENGTH_SHORT).show();
    }

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
