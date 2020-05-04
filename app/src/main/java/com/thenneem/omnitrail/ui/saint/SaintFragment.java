package com.thenneem.omnitrail.ui.saint;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import com.thenneem.omnitrail.FullscreenActivity;
import com.thenneem.omnitrail.R;
import com.thenneem.omnitrail.adapter.ReligionAdaptor;
import com.thenneem.omnitrail.adapter.SaintAdaptor;
import com.thenneem.omnitrail.model.Religion;
import com.thenneem.omnitrail.model.Saint;
import com.thenneem.omnitrail.rest.ApiClient;
import com.thenneem.omnitrail.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaintFragment extends Fragment {


    //recycle view
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    private SaintViewModel mViewModel;

    private static final String TAG = SaintFragment.class.getSimpleName();


    public static SaintFragment newInstance() {
        return new SaintFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.saint_fragment, container, false);

        //android.widget.Toolbar  topToolBar = (Toolbar)root.findViewById(R.id.toptoolbar);


        recyclerView = (RecyclerView) root.findViewById(R.id.rv_religions);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);


        // calling json retrofit
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<List<Saint>> call = apiService.getSaintList(getArguments().getString("rid"));




        //
        call.enqueue(new Callback<List<Saint>>() {
            @Override
            public void onResponse(Call<List<Saint>> call, Response<List<Saint>> response) {
                //religinoSingle = response.body();

                List<Saint> rl = (List<Saint>) response.body();
                recyclerView.setAdapter(new SaintAdaptor(rl,R.layout.saintlist_layout,getContext()));


                Log.d(TAG ,"No of religion revivd " + rl.size());
                //Log.d(TAG ,"No of religion revivd " + religinoSingle.getReligionName());

                //Toast.makeText(getApplication(), "No of Religion " + rl.size(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<Saint>> call, Throwable t) {
                Log.d(TAG , t.toString());
                Toast.makeText(getContext(), "Error" +  t.toString(),Toast.LENGTH_LONG).show();
            }
        });

        //

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SaintViewModel.class);
        // TODO: Use the ViewModel
    }

}
