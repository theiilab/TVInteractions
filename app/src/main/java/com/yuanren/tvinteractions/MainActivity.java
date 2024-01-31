package com.yuanren.tvinteractions;

import android.content.Intent;
import android.database.MergeCursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.yuanren.tvinteractions.base.FragmentChangeListener;
import com.yuanren.tvinteractions.base.NavigationMenuCallback;
import com.yuanren.tvinteractions.base.NavigationStateListener;
import com.yuanren.tvinteractions.log.Metrics;
import com.yuanren.tvinteractions.model.MovieList;
import com.yuanren.tvinteractions.network.RandomPositionSocketService;
import com.yuanren.tvinteractions.view.movies.MoviesFragment;
import com.yuanren.tvinteractions.view.movies.RowsOfMoviesFragment;
import com.yuanren.tvinteractions.view.nav_drawer.NavigationMenuFragment;
import com.yuanren.tvinteractions.view.search.SearchFragment;
import com.yuanren.tvinteractions.view.tv_channels.TVChannelsFragment;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends FragmentActivity implements FragmentChangeListener, NavigationMenuCallback, NavigationStateListener {
    private static final String TAG = "MainActivity";

    private FrameLayout fragmentsLayout;
    private FrameLayout navDrawerLayout;
//    private ActivityMainBinding binding;

    private NavigationMenuFragment navMenuFragment;
    private SearchFragment searchFragment;
    private MoviesFragment moviesFragment;
    private TVChannelsFragment tvChannelsFragment;

    private int currentSelectedFragment = NavigationMenuFragment.TYPE_VIEW_HOME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navDrawerLayout = findViewById(R.id.nav_drawer);
        fragmentsLayout = findViewById(R.id.fragments);

        navMenuFragment = NavigationMenuFragment.newInstance();
        searchFragment = SearchFragment.newInstance();
        moviesFragment = MoviesFragment.newInstance();
        tvChannelsFragment = TVChannelsFragment.newInstance();


        // avoid duplicate fragment after screen rotation
        if (savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.nav_drawer, navMenuFragment)
                    .commit();

            /** -------- log -------- */
            Metrics metrics = (Metrics) getApplicationContext();
            Fragment fragment = metrics.session == 3 ? searchFragment : moviesFragment;
            /** --------------------- */
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragments, fragment)
                    .commit();
        }
        // start my socket channel to send random positions of movies to watch side
        Metrics metrics = (Metrics) getApplicationContext();
        RandomPositionSocketService.setLogBasic(String.valueOf(metrics.pid), String.valueOf(metrics.session), metrics.method, String.valueOf(metrics.dataSet));
        RandomPositionSocketService.start();
    }

    @Override
    public void navMenuToggle(Boolean toShow) {

        try {
            if (toShow) {
                navDrawerLayout.setBackgroundResource(R.drawable.ic_nav_bg_open);
                navDrawerLayout.requestFocus();
                fragmentsLayout.clearFocus();
                navEnterAnimation();
                navMenuFragment.openNav();
            } else {
                navDrawerLayout.setBackgroundResource(R.drawable.ic_nav_bg_closed);
                fragmentsLayout.requestFocus();
                navDrawerLayout.clearFocus();
                navMenuFragment.closeNav();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAttachFragment(@NonNull @NotNull Fragment fragment) {
        if (fragment instanceof NavigationMenuFragment) {
            ((NavigationMenuFragment)fragment).setFragmentChangeListener(this);
            ((NavigationMenuFragment)fragment).setNavigationStateListener(this);
        } else if (fragment instanceof SearchFragment) {
            ((SearchFragment)fragment).setNavigationMenuCallback(this);
        } else if (fragment instanceof MoviesFragment) {
            ((MoviesFragment)fragment).setNavigationMenuCallback(this);
        } else if (fragment instanceof TVChannelsFragment) {
            ((TVChannelsFragment)fragment).setNavigationMenuCallback(this);
        }
    }

    @Override
    public void onBackPressed() {
        return; // prevent user accidentally exit the app before completing all the tasks
    }

    private void navEnterAnimation() {
        Animation animate = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
        navDrawerLayout.startAnimation(animate);
    }

    @Override
    public void switchFragment(int fragmentName) {
        Fragment fragment = null;

        switch (fragmentName) {
            case NavigationMenuFragment.TYPE_VIEW_SEARCH:
                fragment = searchFragment;
                break;
            case NavigationMenuFragment.TYPE_VIEW_HOME:
                fragment = moviesFragment;
                //                    moviesFragment.selectFirstItem();
                break;
            case NavigationMenuFragment.TYPE_VIEW_MOVIES:
                fragment = moviesFragment;
                break;
            case NavigationMenuFragment.TYPE_VIEW_TV_CHANNELS:
                fragment = tvChannelsFragment;
                break;
            case NavigationMenuFragment.TYPE_VIEW_SETTINGS:
                fragment = moviesFragment;
                break;
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragments, fragment)
                .commit();
    }

    @Override
    public void onStateChanged(Boolean expanded, int lastSelected) {
        if (!expanded) {
            navMenuToggle(false);

            switch (currentSelectedFragment) {
                case NavigationMenuFragment.TYPE_VIEW_SEARCH:
                    currentSelectedFragment = NavigationMenuFragment.TYPE_VIEW_SEARCH;
                    break;
                case NavigationMenuFragment.TYPE_VIEW_HOME:
                    currentSelectedFragment = NavigationMenuFragment.TYPE_VIEW_HOME;
                    moviesFragment.selectFirstItem();
                    break;
                case NavigationMenuFragment.TYPE_VIEW_MOVIES:
                    currentSelectedFragment = NavigationMenuFragment.TYPE_VIEW_MOVIES;
                    moviesFragment.selectFirstItem();
                    break;
                case NavigationMenuFragment.TYPE_VIEW_TV_CHANNELS:
                    currentSelectedFragment = NavigationMenuFragment.TYPE_VIEW_TV_CHANNELS;
                    break;
                case NavigationMenuFragment.TYPE_VIEW_SETTINGS:
                    currentSelectedFragment = NavigationMenuFragment.TYPE_VIEW_SETTINGS;
                    break;
            }
        }
    }

    // For keyboard OnClick callback (in keyboard.xml android:onClick="onKeyClick")
    public void onKeyClick(View v) {
        searchFragment.onKeyClick(v);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** -------- log -------- */
        if (requestCode == RowsOfMoviesFragment.REQUEST_CODE_DETAILS) {
            moviesFragment.onResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        RandomPositionSocketService.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        RandomPositionSocketService.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RandomPositionSocketService.stop();
    }
}