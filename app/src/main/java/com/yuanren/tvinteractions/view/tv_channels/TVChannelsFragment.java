package com.yuanren.tvinteractions.view.tv_channels;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuanren.tvinteractions.R;
import com.yuanren.tvinteractions.view.movies.MoviesFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TVChannelsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TVChannelsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TVChannelsFragment() {
        // Required empty public constructor
    }

    public static TVChannelsFragment newInstance() {
        TVChannelsFragment fragment = new TVChannelsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_channels, container, false);
    }
}