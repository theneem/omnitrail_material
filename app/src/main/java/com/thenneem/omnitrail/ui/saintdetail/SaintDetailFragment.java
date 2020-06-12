package com.thenneem.omnitrail.ui.saintdetail;

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
import com.thenneem.omnitrail.R;
import com.thenneem.omnitrail.model.Saint;
import com.thenneem.omnitrail.model.Temple;
import com.thenneem.omnitrail.rest.ApiClient;
import com.thenneem.omnitrail.rest.ApiInterface;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaintDetailFragment extends Fragment  implements View.OnClickListener  {

    private SaintDetailViewModel mViewModel;

    TextView txtSect;
    TextView txtSamudai;
    TextView txtParentSaint;
    TextView txtSaintDate;
    TextView txtFullAddress;
    TextView txtSaintStory;
    TextView txtContactPerson;
    MaterialButton btnWikiLink;

    String strWiki;



    public static SaintDetailFragment newInstance() {
        return new SaintDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View root  =  inflater.inflate(R.layout.saint_detail_fragment, container, false);

        String mySID = "";

        txtSect = root.findViewById(R.id.txtSect);

        txtSamudai = root.findViewById(R.id.txtSamudai);
        txtParentSaint = root.findViewById(R.id.txtParentSaint);
        txtSaintDate = root.findViewById(R.id.txtSaintDate);
        txtFullAddress = root.findViewById(R.id.txtFullAddress);
        txtSaintStory = root.findViewById(R.id.txtSaintStory);
        btnWikiLink = root.findViewById(R.id.btnWikiLink);

        txtContactPerson= root.findViewById(R.id.txtContactPerson);


        btnWikiLink.setOnClickListener(this);

        Bundle bundle = this.getArguments();
        if (bundle != null) {

            mySID = bundle.getString("SaintId");
            //Toast.makeText(this.getContext(), "TempleID : " + myTID, Toast.LENGTH_SHORT).show();
        }




        // calling json retrofit
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<List<Saint>> call = apiService.getSaint(mySID);
        //Call<List<Temple>> call = apiService.getTempleList("1");

        //
        call.enqueue(new Callback<List<Saint>>() {
            @Override
            public void onResponse(Call<List<Saint>> call, Response<List<Saint>> response) {


                List<Saint> sl = (List<Saint>) response.body();
                ///recyclerView.setAdapter(new TempleAdaptor(rl,R.layout.templelist_layout,getContext()));
                BindSaintDetail(sl);


            }

            @Override
            public void onFailure(Call<List<Saint>> call, Throwable t) {
                //Log.d(TAG , t.toString());
                Toast.makeText(getContext(), "Error" +  t.toString(),Toast.LENGTH_LONG).show();
            }
        });

        //

        return root;

    }


    public void BindSaintDetail( List<Saint> sl)
    {
        if(sl.size() >0 )
        {

            txtSect.setText(sl.get(0).getSectName());
            txtSamudai.setText(sl.get(0).getSamudai());
            txtParentSaint.setText(sl.get(0).getParentSaintName());
            txtSaintDate.setText(sl.get(0).getSaintDate());
            txtSaintStory.setText(sl.get(0).getSaintStory());
            txtFullAddress.setText(sl.get(0).getCurrentAddress());
            strWiki = sl.get(0).getWiki_link();
            txtContactPerson.setText(sl.get(0).getChiefFollower() + " : " + sl.get(0).getChiefFollowerContact());



        }

    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SaintDetailViewModel.class);
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
