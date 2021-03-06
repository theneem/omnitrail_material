package com.thenneem.omnitrail.ui.templefeature;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.thenneem.omnitrail.R;
import com.thenneem.omnitrail.adapter.EventAdaptor;
import com.thenneem.omnitrail.adapter.FeatureAdaptor;
import com.thenneem.omnitrail.model.Event;
import com.thenneem.omnitrail.model.Feature;
import com.thenneem.omnitrail.rest.ApiClient;
import com.thenneem.omnitrail.rest.ApiInterface;
import com.thenneem.omnitrail.ui.templereview.TempleReviewFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TempleFeatureFragment extends Fragment  implements View.OnClickListener {

    //recycle view

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private static final String TAG = TempleFeatureFragment.class.getSimpleName();

    private TempleFeatureViewModel mViewModel;

    View root;


    public static TempleFeatureFragment newInstance() {
        return new TempleFeatureFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

         root =  inflater.inflate(R.layout.temple_feature_fragment, container, false);


         recyclerView = root.findViewById(R.id.rv_templefeature);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        // calling json retrofit
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<List<Feature>> call = apiService.getFeatureList("temple", getArguments().getString("TempleId"));


        TextView txtTempleName;
        txtTempleName = root.findViewById(R.id.txtTempleName);
        Bundle bundle = this.getArguments();
        txtTempleName.setText(bundle.getString("TempleName"));

        call.enqueue(new Callback<List<Feature>>() {
            @Override
            public void onResponse(Call<List<Feature>> call, Response<List<Feature>> response) {

                List<Feature> rl = response.body();

                if(rl.size() <= 0 ) {

                    TextView tv = root.findViewById(R.id.empty_view);
                    //recyclerView = (RecyclerView) root.findViewById(R.id.rv_templeevent);
                    tv.setVisibility(View.VISIBLE);
                }
                else
                {

                    recyclerView.setAdapter(new FeatureAdaptor(rl,R.layout.featurelist_layout,getContext()));


                }


            }

            @Override
            public void onFailure(Call<List<Feature>> call, Throwable t) {
                //Log.d(TAG , t.toString());
                Toast.makeText(getContext(), "Error" +  t.toString(),Toast.LENGTH_LONG).show();
            }
        });

        //

        return root;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TempleFeatureViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onClick(View view) {




        switch (view.getId()) {
            //case R.id.mbtnShow:
                // Do something


        }
    }
}
