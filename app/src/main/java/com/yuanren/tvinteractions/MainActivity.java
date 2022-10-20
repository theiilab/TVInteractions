package com.yuanren.tvinteractions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.yuanren.tvinteractions.base.FragmentChangeListener;
import com.yuanren.tvinteractions.base.NavigationMenuCallback;
import com.yuanren.tvinteractions.base.NavigationStateListener;
import com.yuanren.tvinteractions.view.movies.MoviesFragment;
import com.yuanren.tvinteractions.view.nav_drawer.NavigationMenuFragment;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends FragmentActivity implements FragmentChangeListener, NavigationMenuCallback, NavigationStateListener {
    private static final String TAG = "MainActivity";

    private FrameLayout mainContentLayout;
    private FrameLayout navDrawerLayout;
//    private ActivityMainBinding binding;

    private NavigationMenuFragment navMenuFragment;
    private MoviesFragment moviesFragment;

    private int currentSelectedFragment = NavigationMenuFragment.TYPE_VIEW_HOME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mainContentLayout = findViewById(R.id.main_content);
        navDrawerLayout = findViewById(R.id.nav_drawer);

        navMenuFragment = NavigationMenuFragment.newInstance();
        moviesFragment = MoviesFragment.newInstance();


        // avoid duplicate fragment after screen rotation
        if (savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.nav_drawer, navMenuFragment)
                    .commit();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_content, moviesFragment)
                    .commit();
//            moviesFragment.selectFirstItem();
        }
    }

    @Override
    public void navMenuToggle(Boolean toShow) {

        try {
            if (toShow) {
                navDrawerLayout.setBackgroundResource(R.drawable.ic_nav_bg_open);
                mainContentLayout.clearFocus();
                navDrawerLayout.requestFocus();
                navEnterAnimation();
                navMenuFragment.openNav();
            } else {
                navDrawerLayout.setBackgroundResource(R.drawable.ic_nav_bg_closed);
                navDrawerLayout.clearFocus();
                mainContentLayout.requestFocus();
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
        } else if (fragment instanceof MoviesFragment) {
            ((MoviesFragment)fragment).setNavigationMenuCallback(this);
        }
    }

    private void navEnterAnimation() {
        Animation animate = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
        navDrawerLayout.startAnimation(animate);
    }

    @Override
    public void switchFragment(int fragmentName) {

    }

    @Override
    public void onStateChanged(Boolean expanded, int lastSelected) {
        if (!expanded) {
            navDrawerLayout.setBackgroundResource(R.drawable.ic_nav_bg_closed);
            navDrawerLayout.clearFocus();

            switch (currentSelectedFragment) {
                case NavigationMenuFragment.TYPE_VIEW_HOME:
                    currentSelectedFragment = NavigationMenuFragment.TYPE_VIEW_HOME;
//                    moviesFragment.selectFirstItem();
                    break;
            }
        }
    }
}