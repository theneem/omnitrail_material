package com.thenneem.omnitrail;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.thenneem.omnitrail.model.Religion;
import com.thenneem.omnitrail.model.Temple;
import com.thenneem.omnitrail.ui.temple.TempleFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class OmniHomeActivity extends AppCompatActivity {

    private Temple temple;
    private Religion religion;

    private MaterialToolbar topToolBar;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_omni_home);

//// try loading


        getIncomingIntent();

        openFragment(TempleFragment.newInstance());


        ///// end try loading

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        //getSupportActionBar().hide();
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
     //   NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
       // NavigationUI.setupWithNavController(navView, navController);
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


    public  void getIncomingIntent(){
        //mContentView.setText



        temple = (Temple)  getIntent().getSerializableExtra("Temple");

       // toolbar.setTitle(temple.getReligionName() + " -> " +  temple.getTempleName());

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