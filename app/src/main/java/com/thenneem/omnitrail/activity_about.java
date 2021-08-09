package com.thenneem.omnitrail;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class activity_about extends AppCompatActivity {

    private TextView mTextView;
    private MaterialToolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        toolbar = findViewById(R.id.toptoolbar);
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
        toolbar.animate();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //finish();
                activity_about.super.onBackPressed();

            }
        });


    }
}