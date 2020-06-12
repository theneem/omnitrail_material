package com.thenneem.omnitrail.ui.templedetail;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;
import com.thenneem.omnitrail.R;
import com.thenneem.omnitrail.adapter.TempleAdaptor;
import com.thenneem.omnitrail.model.Temple;
import com.thenneem.omnitrail.rest.ApiClient;
import com.thenneem.omnitrail.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TempleDetailFragment extends Fragment implements View.OnClickListener {

    private TempleDetailViewModel mViewModel;


    ImageView templeThumb;
   // TextView txtTempleName;
   // TextView txtLocation;
    TextView txtFullAddress;
    TextView txtTempleStory;
    TextView txtPrimaryDeity;
    TextView txtGoverningBody;
    TextView txtContactPerson;
    TextView txtCreator;
    TextView txtCompletionPeriod;

    String strWiki;



    public static TempleDetailFragment newInstance() {
        return new TempleDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        String myTID = "";

        final View root = inflater.inflate(R.layout.temple_detail_fragment, container, false);




//        txtTempleName = root.findViewById(R.id.txtTempleName);
 //       txtLocation = root.findViewById(R.id.txtMyLocation);
        txtFullAddress = root.findViewById(R.id.txtFullAddress);
        txtTempleStory = root.findViewById(R.id.txtTempleStory);
        txtPrimaryDeity = root.findViewById(R.id.txtPrimaryDeity);
        txtGoverningBody = root.findViewById(R.id.txtGoverningBody);
        txtContactPerson = root.findViewById(R.id.txtContactPerson);
        txtCreator = root.findViewById(R.id.txtCreator);
        txtContactPerson = root.findViewById(R.id.txtContactPerson);
        txtCompletionPeriod = root.findViewById(R.id.txtCompletionPeriod);

        templeThumb = root.findViewById(R.id.templeThumb);


        MaterialButton btnWiki = (MaterialButton) root.findViewById(R.id.btnWikiLink);
                btnWiki.setOnClickListener(this);

        Bundle bundle = this.getArguments();
        if (bundle != null) {

             myTID = bundle.getString("TempleId");
            //Toast.makeText(this.getContext(), "TempleID : " + myTID, Toast.LENGTH_SHORT).show();
        }



        // calling json retrofit
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<List<Temple>> call = apiService.getTemple(myTID);
        //Call<List<Temple>> call = apiService.getTempleList("1");


        //
        call.enqueue(new Callback<List<Temple>>() {
            @Override
            public void onResponse(Call<List<Temple>> call, Response<List<Temple>> response) {


                List<Temple> tl = (List<Temple>) response.body();
                ///recyclerView.setAdapter(new TempleAdaptor(rl,R.layout.templelist_layout,getContext()));
                BindTempleDetail(tl);


            }

            @Override
            public void onFailure(Call<List<Temple>> call, Throwable t) {
                //Log.d(TAG , t.toString());
                Toast.makeText(getContext(), "Error" +  t.toString(),Toast.LENGTH_LONG).show();
            }
        });

        //


        return root; }


        public void BindTempleDetail( List<Temple> tl)
        {
            if(tl.size() >0 )
            {

                txtFullAddress.setText(tl.get(0).getAddress() + " " + tl.get(0).getCity_name() + ", " + tl.get(0).getState_name()+ ", " +  tl.get(0).getCountry_name() );
                txtTempleStory.setText(tl.get(0).getTempleStory());

                txtPrimaryDeity.setText(tl.get(0).getPrimaryDeity());
                txtGoverningBody.setText(tl.get(0).getGoverningBody());
                txtContactPerson.setText(tl.get(0).getContactPerson() + " : " + tl.get(0).getContactNumber() ) ;
                txtCreator.setText(tl.get(0).getCreator());
                txtCompletionPeriod.setText(tl.get(0).getCompletionPerios());

                strWiki = tl.get(0).getWiki_link();

            }

        }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TempleDetailViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnWikiLink:
                // Do something
                Toast.makeText(this.getContext(), "wiki link crossed" + strWiki, Toast.LENGTH_SHORT).show();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(strWiki));
                startActivity(browserIntent);

        }
    }
}
