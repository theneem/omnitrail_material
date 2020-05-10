package com.thenneem.omnitrail.ui.saintquality;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thenneem.omnitrail.R;

public class SaintQualityFragment extends Fragment {

    private SaintQualityViewModel mViewModel;

    public static SaintQualityFragment newInstance() {
        return new SaintQualityFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.saint_quality_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SaintQualityViewModel.class);
        // TODO: Use the ViewModel
    }

}
