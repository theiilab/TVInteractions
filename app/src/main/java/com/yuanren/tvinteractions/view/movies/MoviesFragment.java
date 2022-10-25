package com.yuanren.tvinteractions.view.movies;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuanren.tvinteractions.R;
import com.yuanren.tvinteractions.base.NavigationMenuCallback;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MoviesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoviesFragment extends Fragment {
    private RowsOfMoviesFragment rowsOfMoviesFragment;
    private NavigationMenuCallback navigationMenuCallback;

    public MoviesFragment() {

    }

    public static MoviesFragment newInstance() {
        MoviesFragment fragment = new MoviesFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rowsOfMoviesFragment = RowsOfMoviesFragment.newInstance();
        rowsOfMoviesFragment.setNavigationMenuCallback(navigationMenuCallback);

        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.rows_movies, rowsOfMoviesFragment)
                .commit();
    }

    public void setNavigationMenuCallback(NavigationMenuCallback callback) {
        this.navigationMenuCallback = callback;
    }
}