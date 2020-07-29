package com.thenneem.omnitrail.ui.book;

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

import com.thenneem.omnitrail.R;
import com.thenneem.omnitrail.adapter.BookAdaptor;
import com.thenneem.omnitrail.adapter.SaintAdaptor;
import com.thenneem.omnitrail.model.Book;
import com.thenneem.omnitrail.model.Saint;
import com.thenneem.omnitrail.rest.ApiClient;
import com.thenneem.omnitrail.rest.ApiInterface;
import com.thenneem.omnitrail.ui.saint.SaintFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookFragment extends Fragment {


    //recycle view
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    private BookViewModel mViewModel;

    private static final String TAG = BookFragment.class.getSimpleName();

    public static BookFragment newInstance() {
        return new BookFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root =  inflater.inflate(R.layout.book_fragment, container, false);
        recyclerView = root.findViewById(R.id.rv_book);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);


        // calling json retrofit
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<List<Book>> call = apiService.getBookList(getArguments().getString("rid"));

        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                //religinoSingle = response.body();

                List<Book> bl = response.body();
                recyclerView.setAdapter(new BookAdaptor(bl,R.layout.booklist_layout,getContext()));


                //Log.d(TAG ,"No of religion revivd " + bl.size());
                //Log.d(TAG ,"No of religion revivd " + religinoSingle.getReligionName());

                //Toast.makeText(getApplication(), "No of Religion " + rl.size(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                //Log.d(TAG , t.toString());
                Toast.makeText(getContext(), "Error" +  t.toString(),Toast.LENGTH_LONG).show();
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(BookViewModel.class);
        // TODO: Use the ViewModel
    }

}
