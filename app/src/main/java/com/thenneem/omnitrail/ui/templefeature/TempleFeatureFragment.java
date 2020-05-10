package com.thenneem.omnitrail.ui.templefeature;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thenneem.omnitrail.R;

public class TempleFeatureFragment extends Fragment {

    private TempleFeatureViewModel mViewModel;

    public static TempleFeatureFragment newInstance() {
        return new TempleFeatureFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.temple_feature_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TempleFeatureViewModel.class);
        // TODO: Use the ViewModel
    }

}
