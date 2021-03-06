package com.thenneem.omnitrail;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import com.thenneem.omnitrail.adapter.ReligionAdaptor;
import com.thenneem.omnitrail.model.Religion;
import com.thenneem.omnitrail.rest.ApiClient;
import com.thenneem.omnitrail.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.thenneem.omnitrail.utils.PreferenceManager;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {


    //recycle view
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    private List<Religion> religionsArray;
    private Religion religinoSingle;

    PreferenceManager preferenceManager;

    Picasso picasso;



    private static final String TAG = FullscreenActivity.class.getSimpleName();


    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

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
           /*
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        */
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
            //mControlsView.setVisibility(View.VISIBLE);
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

        setContentView(R.layout.activity_fullscreen);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);


        // Set up the user interaction to manually show or hide the system UI.
       /*
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });




        */
        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        // new design // findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);


        recyclerView = findViewById(R.id.rv_religions);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        FloatingActionButton fab = findViewById(R.id.add_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FullscreenActivity.this, ProfileTab.class);
                startActivity(intent);
            }
        });

        //botttom nabigatio click events

        ImageView imgAboutus = findViewById(R.id.menuAbutUs);
        imgAboutus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FullscreenActivity.this, activity_about.class);
                startActivity(intent);
            }

        });


        ImageView imgHowitWorks = findViewById(R.id.menuHowitWorks);
        imgHowitWorks.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FullscreenActivity.this, activity_howitworks.class);
                startActivity(intent);
            }

        });





/*
        FloatingActionButton fab = findViewById(R.id.add_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(FullscreenActivity.this, "Button clicked", Toast.LENGTH_SHORT).show();
                onButtonShowPopupWindowClick(view);
            }
        });

*/


        // end of show navigation draswer

        // navigation clicck tracking


        // end of navigation click traking

        try {


            //Toast.makeText(this, "Calling Rest Api ", Toast.LENGTH_SHORT).show();
            // calling json retrofit
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<List<Religion>> call = apiService.getReligionList();

            call.enqueue(new Callback<List<Religion>>() {
                @Override
                public void onResponse(Call<List<Religion>> call, Response<List<Religion>> response) {
                    //religinoSingle = response.body();

                    try {

                        //Toast.makeText(FullscreenActivity.this, "Religion list recevied", Toast.LENGTH_SHORT).show();
                        List<Religion> rl = response.body();

                       // Toast.makeText(FullscreenActivity.this, String.valueOf(rl.size()), Toast.LENGTH_SHORT).show();

                        //Toast.makeText(FullscreenActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                        recyclerView.setAdapter(new ReligionAdaptor(rl, R.layout.religionlist_layout, getApplicationContext()));

                    } catch (Exception e) {
                        // This will catch any exception, because they are all descended from Exception
                        //Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(, "Error" +  t.toString(),Toast.LENGTH_LONG).show();

                        Toast.makeText(FullscreenActivity.this, "exception ", Toast.LENGTH_SHORT).show();
                        Toast.makeText(FullscreenActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(FullscreenActivity.this, e.getStackTrace().toString(), Toast.LENGTH_SHORT).show();
                    }


                    //Log.d(TAG ,"No of religion revivd " + rl.size());
                    //Log.d(TAG ,"No of religion revivd " + religinoSingle.getReligionName());

                    //Toast.makeText(getApplication(), "No of Religion " + rl.size(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<List<Religion>> call, Throwable t) {
                    //Log.d(TAG , t.toString());
                    Toast.makeText(getApplication(), "Error" + t.toString(), Toast.LENGTH_LONG).show();
                }
            });

        } catch (Exception e) {
            Log.e("Fail 2", e.toString());
            //At the level Exception Class handle the error in Exception Table
            // Exception Create That Error  Object and throw it
            //E.g: FileNotFoundException ,etc
            Toast.makeText(this, "Exception occured", Toast.LENGTH_LONG).show();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();

            Toast.makeText(this, "Error" + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
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
        //mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        /*
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;
*/

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

    // navigation menu item click



    //function for popup side bar
    public void onButtonShowPopupWindowClick(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.nav_header_main, null);

        // create the popup window
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        //int width = displayMetrics.widthPixels;

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        //int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.START, 0, 0);


        //handle menu buttons click events inside



        NavigationView navigationView = (NavigationView) popupView.findViewById(R.id.nav_mainmenu);



        preferenceManager = new PreferenceManager(FullscreenActivity.this);


        MaterialButton btnLogin = popupView.findViewById(R.id.btn_login);
        MaterialButton btnLogout = popupView.findViewById(R.id.btn_logout);

        TextView txtName = popupView.findViewById(R.id.txtName);
        TextView txtEmail = popupView.findViewById(R.id.txtEmail);
        ImageView imgProfile = popupView.findViewById(R.id.imageView);

        if (preferenceManager.getLoginSession()) {


            btnLogin.setVisibility(View.GONE);
            btnLogout.setVisibility(View.VISIBLE);

            txtName.setVisibility(View.VISIBLE);
            txtEmail.setVisibility(View.VISIBLE);

            txtName.setText(preferenceManager.getuserName());
            txtEmail.setText(preferenceManager.getemailAddress());

            // try to set the profile piture using id


            String image_url = "https://graph.facebook.com/" + preferenceManager.getUserId() + "/picture?type=large";

            ImageView imgRThumb = findViewById(R.id.imgReligionThumb);
            Picasso.Builder builder = new Picasso.Builder(getApplicationContext());
            picasso = builder.build();
            picasso.load(image_url)
                    .placeholder((R.drawable.ic_launcher_background))
                    .error(R.drawable.ic_launcher_foreground)
                    .into(imgProfile);




            btnLogout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                   // Toast.makeText(FullscreenActivity.this, "logout Button clicked", Toast.LENGTH_SHORT).show();
                    preferenceManager.setLoginSession(false);
                    preferenceManager.clearPreferences();

                    // Facebook Logout
                    LoginManager.getInstance().logOut();



                    Intent intent = new Intent(FullscreenActivity.this, FullscreenActivity.class);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   startActivity(intent);

                }
            });

        }
        else {
            btnLogin.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.GONE);
            txtName.setVisibility(View.GONE);
            txtEmail.setVisibility(View.GONE);

            btnLogin.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    // Toast.makeText(FullscreenActivity.this, "Button clicked", Toast.LENGTH_SHORT).show();

                    Intent loginIntent = new Intent(FullscreenActivity.this, LoginActivity.class);
                    FullscreenActivity.this.startActivity(loginIntent);
                    FullscreenActivity.this.finish();

                }
            });



        }



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id == R.id.nav_gallery) {
                    startActivity(new Intent(FullscreenActivity.this,TrailActivity.class));
                } else if(id == R.id.nav_mytrails) {
                    startActivity(new Intent(FullscreenActivity.this,TrailDetailActivity.class));

                } else if(id == R.id.how_work) {

                    Intent mainIntent = new Intent(FullscreenActivity.this, activity_howitworks.class);

                    FullscreenActivity.this.startActivity(mainIntent);
                   // FullscreenActivity.this.finish();



                } else if(id == R.id.about_omnitrail)
                {

                    Intent mainIntent = new Intent(FullscreenActivity.this, activity_about.class);

                    FullscreenActivity.this.startActivity(mainIntent);
                }
                //drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });



        // end of hendle menu button click events insdie




        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }


}
