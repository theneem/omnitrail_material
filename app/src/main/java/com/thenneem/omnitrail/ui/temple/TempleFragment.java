package com.thenneem.omnitrail.ui.temple;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
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

import com.thenneem.omnitrail.R;
import com.thenneem.omnitrail.TempleHome;
import com.thenneem.omnitrail.adapter.RecyclerItemClickListner;
import com.thenneem.omnitrail.adapter.SaintAdaptor;
import com.thenneem.omnitrail.adapter.TempleAdaptor;
import com.thenneem.omnitrail.model.Saint;
import com.thenneem.omnitrail.model.Temple;
import com.thenneem.omnitrail.rest.ApiClient;
import com.thenneem.omnitrail.rest.ApiInterface;
import com.thenneem.omnitrail.ui.saint.SaintFragment;
import com.thenneem.omnitrail.ui.saint.SaintViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TempleFragment extends Fragment {

    private TempleViewModel mViewModel;


    //recycle view
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    List<Temple> rl;



    private static final String TAG = TempleFragment.class.getSimpleName();


    public static TempleFragment newInstance() {
        return new TempleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.temple_fragment, container, false);
        //Toast.makeText(this.getContext(), "rid : "  + getArguments().getString("rid"), Toast.LENGTH_SHORT).show();


        recyclerView = root.findViewById(R.id.rv_temples);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);



        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);






        // calling json retrofit
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

       Call<List<Temple>> call = apiService.getTempleList(getArguments().getString("rid"));
        //Call<List<Temple>> call = apiService.getTempleList("1");


        //
        call.enqueue(new Callback<List<Temple>>() {
            @Override
            public void onResponse(Call<List<Temple>> call, Response<List<Temple>> response) {
                //religinoSingle = response.body();

               rl = response.body();
                recyclerView.setAdapter(new TempleAdaptor(rl,R.layout.templelist_layout,getContext()));


            }

            @Override
            public void onFailure(Call<List<Temple>> call, Throwable t) {
                //Log.d(TAG , t.toString());
                Toast.makeText(getContext(), "Error" +  t.toString(),Toast.LENGTH_LONG).show();
            }
        });

        //



        recyclerView.addOnItemTouchListener(new RecyclerItemClickListner(this.getContext(), recyclerView, new RecyclerItemClickListner
                .OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //handle click events here

                Intent intent = new Intent(getContext(), TempleHome.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Temple",rl.get(position));


                getContext().startActivity(intent);

            }

            @Override
            public void onItemLongClick(View view, int position) {
                //handle longClick if any
                //Toast.makeText(getContext(), "Item long click " + String.valueOf(position), Toast.LENGTH_SHORT).show();

            }
        }));





        return root;

    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TempleViewModel.class);
        // TODO: Use the ViewModel
    }

}
