package com.thenneem.omnitrail.ui.saintreview;

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
import android.widget.Toast;

import com.thenneem.omnitrail.R;
import com.thenneem.omnitrail.adapter.EventAdaptor;
import com.thenneem.omnitrail.model.Event;
import com.thenneem.omnitrail.rest.ApiClient;
import com.thenneem.omnitrail.rest.ApiInterface;
import com.thenneem.omnitrail.ui.templereview.TempleReviewFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaintReviewFragment extends Fragment {

    private SaintReviewViewModel mViewModel;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static final String TAG = SaintReviewFragment.class.getSimpleName();

    public static SaintReviewFragment newInstance() {
        return new SaintReviewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root =   inflater.inflate(R.layout.saint_review_fragment, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.rv_saintevent);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        // calling json retrofit
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<List<Event>> call = apiService.getEventList("saint", getArguments().getString("SaintId"));

        //
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                //religinoSingle = response.body();
                List<Event> rl = (List<Event>) response.body();
                recyclerView.setAdapter(new EventAdaptor(rl,R.layout.eventlist_layout, R.layout.empltyrv_layout,  getContext()));



            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
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
        mViewModel = ViewModelProviders.of(this).get(SaintReviewViewModel.class);
        // TODO: Use the ViewModel
    }

}
