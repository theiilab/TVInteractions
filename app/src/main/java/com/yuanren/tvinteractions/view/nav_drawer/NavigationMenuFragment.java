package com.yuanren.tvinteractions.view.nav_drawer;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yuanren.tvinteractions.R;
import com.yuanren.tvinteractions.base.FragmentChangeListener;
import com.yuanren.tvinteractions.base.NavigationStateListener;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NavigationMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavigationMenuFragment extends Fragment {
    private static final String TAG = "NavigationMenuFragment";
    public static final int TYPE_VIEW_SEARCH = 1;
    public static final int TYPE_VIEW_HOME = 2;
    public static final int TYPE_VIEW_MOVIES = 3;
    public static final int TYPE_VIEW_TV_SHOWS = 4;
    public static final int TYPE_VIEW_SETTINGS = 5;

    private ConstraintLayout constraintLayout;

    // nav menu items
    private ImageButton searchIB;
    private ImageButton homeIB;
    private ImageButton moviesIB;
    private ImageButton tvShowsIB;
    private ImageButton settingsIB;
    private TextView searchTV;
    private TextView homeTV;
    private TextView moviesTV;
    private TextView tvShowsTV;
    private TextView settingsTV;

    private int currentSelectedFragmentType;
    private FragmentChangeListener fragmentChangeListener;
    private NavigationStateListener navigationStateListener;
    private int menuTextAnimationDelay = 0; // 200
    private int lastSelectedMenuItem;

    public NavigationMenuFragment() {
        // Required empty public constructor
    }

    public static NavigationMenuFragment newInstance() {
        NavigationMenuFragment fragment = new NavigationMenuFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navigation_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        constraintLayout = getActivity().findViewById(R.id.open_nav_CL);
        searchIB = view.findViewById(R.id.search_IB);
        homeIB = view.findViewById(R.id.home_IB);
        moviesIB = view.findViewById(R.id.movies_IB);
        tvShowsIB = view.findViewById(R.id.shows_IB);
        settingsIB = view.findViewById(R.id.settings_IB);
        searchTV = view.findViewById(R.id.search_TV);
        homeTV = view.findViewById(R.id.home_TV);
        moviesTV = view.findViewById(R.id.movies_TV);
        tvShowsTV = view.findViewById(R.id.shows_TV);
        settingsTV = view.findViewById(R.id.settings_TV);

        // by default selection
        homeIB.setImageResource(R.drawable.ic_home_selected);
        lastSelectedMenuItem = TYPE_VIEW_HOME;
        currentSelectedFragmentType = TYPE_VIEW_HOME;

        // set listeners for each mav menu item
        searchListeners();
        homeListeners();
    }

    public void setFragmentChangeListener(FragmentChangeListener callback) {
        this.fragmentChangeListener = callback;
    }

    public void setNavigationStateListener(NavigationStateListener callback) {
        this.navigationStateListener = callback;
    }

    /** ------------------------------------------------------------------------------------ */
    /** -------------------------- set listeners for nav menu item ------------------------- */
    /** ------------------------------------------------------------------------------------ */

    private void searchListeners() {
        searchIB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                // check if the button is focused
                if (b) {
                    if (isNavigationOpen()) {
                        setMenuIconFocusView(searchIB, R.drawable.ic_search_selected);
                        setMenuNameFocusView(searchTV, true);
                        focusIn(searchIB, 0);
                    }
                } else {
                    setMenuIconFocusView(searchIB, R.drawable.ic_search_unselected);
                    setMenuNameFocusView(searchTV, false);
                    focusOut(searchIB, 0);
                }
            }
        });

        searchIB.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {//only when key is pressed down
                    switch (i) {
                        case KeyEvent.KEYCODE_ENTER:
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                            lastSelectedMenuItem = TYPE_VIEW_SEARCH;
                            navigationStateListener.onStateChanged(false, lastSelectedMenuItem);
                            fragmentChangeListener.switchFragment(TYPE_VIEW_SEARCH);
                            closeNav();
                            break;
                        case KeyEvent.KEYCODE_DPAD_RIGHT:
                            navigationStateListener.onStateChanged(false, lastSelectedMenuItem);
                            closeNav();
                    }
                }
                return false;
            }
        });
    }

    private void homeListeners() {
        homeIB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                // check if the button is focused
                if (b) {
                    if (isNavigationOpen()) {
                        setMenuIconFocusView(homeIB, R.drawable.ic_home_selected);
                        setMenuNameFocusView(homeTV, true);
                        focusIn(homeIB, 0);
                    }
                } else {
                    setMenuIconFocusView(homeIB, R.drawable.ic_home_unselected);
                    setMenuNameFocusView(homeTV, false);
                    focusOut(homeIB, 0);
                }
            }
        });

        homeIB.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {//only when key is pressed down
                    switch (i) {
                        case KeyEvent.KEYCODE_ENTER:
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                            lastSelectedMenuItem = TYPE_VIEW_HOME;
                            navigationStateListener.onStateChanged(false, lastSelectedMenuItem);
                            fragmentChangeListener.switchFragment(TYPE_VIEW_HOME);
                            closeNav();
                            break;
                        case KeyEvent.KEYCODE_DPAD_RIGHT:
                            navigationStateListener.onStateChanged(false, lastSelectedMenuItem);
                            closeNav();
                    }
                }
                return false;
            }
        });
    }

    /** ------------------------------------------------------------------------------------ */
    /** ----------------------------- nav menu items open/close ---------------------------- */
    /** ------------------------------------------------------------------------------------ */
    public void openNav() {
        Log.d(TAG, "openNav");
        enableNavMenuViews(View.VISIBLE);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT);
        constraintLayout.setLayoutParams(lp);
        navigationStateListener.onStateChanged(true, lastSelectedMenuItem);

        switch (lastSelectedMenuItem) {
            case TYPE_VIEW_SEARCH:
                searchIB.requestFocus();
                setMenuNameFocusView(searchTV, true);
                break;
            case TYPE_VIEW_HOME:
                homeIB.requestFocus();
                setMenuNameFocusView(homeTV, true);
                break;
            case TYPE_VIEW_MOVIES:
                moviesIB.requestFocus();
                setMenuNameFocusView(moviesTV, true);
                break;
            case TYPE_VIEW_TV_SHOWS:
                tvShowsIB.requestFocus();
                setMenuNameFocusView(tvShowsTV, true);
                break;
            case TYPE_VIEW_SETTINGS:
                settingsIB.requestFocus();
                setMenuNameFocusView(settingsTV, true);
                break;
        }
    }

    private void enableNavMenuViews(int visibility) {
        Log.d(TAG, "enableNavMenuViews");
        if (visibility == View.GONE) {
            menuTextAnimationDelay = 0;
            searchTV.setVisibility(visibility);
            homeTV.setVisibility(visibility);
            moviesTV.setVisibility(visibility);
            tvShowsTV.setVisibility(visibility);
            settingsTV.setVisibility(visibility);
        } else {
            animateMenuNamesEntry(searchTV, visibility, TYPE_VIEW_SEARCH);
//            animateMenuNamesEntry(homeTV, visibility, TYPE_VIEW_HOME);
//            animateMenuNamesEntry(moviesTV, visibility, TYPE_VIEW_MOVIES);
//            animateMenuNamesEntry(tvShowsTV, visibility, TYPE_VIEW_TV_SHOWS);
//            animateMenuNamesEntry(settingsTV, visibility, TYPE_VIEW_SETTING);
        }

    }

    private void animateMenuNamesEntry(View view, int visibility, int viewCode) {
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setVisibility(visibility);
                Animation animate = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_left_menu_items);
                view.startAnimation(animate);
                menuTextAnimationDelay = 100;
                switch (viewCode) {
                    case TYPE_VIEW_SEARCH:
                        animateMenuNamesEntry(searchTV, visibility, viewCode + 1);
                        break;
                    case TYPE_VIEW_HOME:
                        animateMenuNamesEntry(homeTV, visibility, viewCode + 1);
                        break;
                    case TYPE_VIEW_MOVIES:
                        animateMenuNamesEntry(moviesTV, visibility, viewCode + 1);
                        break;
                    case TYPE_VIEW_TV_SHOWS:
                        animateMenuNamesEntry(tvShowsTV, visibility, viewCode + 1);
                        break;
                    case TYPE_VIEW_SETTINGS:
                        animateMenuNamesEntry(settingsTV, visibility, viewCode + 1);
                        break;
                }
            }
        }, menuTextAnimationDelay);

    }

    public void closeNav() {
        Log.d(TAG, "closeNav");
        enableNavMenuViews(View.GONE);
        navOutAnimation();
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT);
        constraintLayout.setLayoutParams(lp);

        //highlighting last selected menu icon
        highlightMenuSelection(lastSelectedMenuItem);

        //Setting out of focus views for menu icons, names
        unHighlightMenuSelections(lastSelectedMenuItem);

    }

    private void navOutAnimation() {
        Animation animate = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_right);
        constraintLayout.startAnimation(animate);
    }

    private void unHighlightMenuSelections(int lastSelectedMenuItem) {
        if (lastSelectedMenuItem != TYPE_VIEW_SEARCH) {
            setMenuIconFocusView(searchIB, R.drawable.ic_search_unselected);
            setMenuNameFocusView(searchTV, false);
        }
        if (lastSelectedMenuItem !=  TYPE_VIEW_HOME) {
            setMenuIconFocusView(homeIB, R.drawable.ic_home_unselected);
            setMenuNameFocusView(homeTV, false);
        }
        if (lastSelectedMenuItem !=  TYPE_VIEW_MOVIES) {
            setMenuIconFocusView(moviesIB, R.drawable.ic_movie_unselected);
            setMenuNameFocusView(moviesTV, false);
        }
        if (lastSelectedMenuItem !=  TYPE_VIEW_TV_SHOWS) {
            setMenuIconFocusView(tvShowsIB, R.drawable.ic_shows_unselected);
            setMenuNameFocusView(tvShowsTV, false);
        }
        if (lastSelectedMenuItem !=  TYPE_VIEW_SETTINGS) {
            setMenuIconFocusView(settingsIB, R.drawable.ic_settings_unselected);
            setMenuNameFocusView(settingsTV, false);
        }
    }

    private void highlightMenuSelection(int lastSelectedMenuItem) {
        switch (lastSelectedMenuItem) {
            case TYPE_VIEW_SEARCH:
                setMenuIconFocusView(searchIB, R.drawable.ic_search_selected);
                break;
            case TYPE_VIEW_HOME:
                setMenuIconFocusView(homeIB, R.drawable.ic_home_selected);
                break;
            case TYPE_VIEW_MOVIES:
                setMenuIconFocusView(moviesIB, R.drawable.ic_movie_selected);
                break;
            case TYPE_VIEW_TV_SHOWS:
                setMenuIconFocusView(tvShowsIB, R.drawable.ic_shows_selected);
                break;
            case TYPE_VIEW_SETTINGS:
                setMenuIconFocusView(settingsIB, R.drawable.ic_settings_selected);
                break;
        }
    }

    /** ------------------------------------------------------------------------------------ */
    /** --------------------------- nav menu items focus/unfocus --------------------------- */
    /** ------------------------------------------------------------------------------------ */
    private boolean isNavigationOpen() {
        // check whether text view is displayed
        return homeTV.getVisibility() == View.VISIBLE;
    }

    private void setMenuIconFocusView(ImageButton view, int resource) {
        view.setImageResource(resource);
    }

    private void setMenuNameFocusView(TextView view, boolean inFocus) {
        if (inFocus) {
            view.setTextColor(getActivity().getColor(R.color.navigation_menu_focus_color));
        } else {
            view.setTextColor(getActivity().getColor(R.color.navigation_menu_focus_out_color));
        }
    }

    /** Set animation when focused */
    private void focusIn(View v, int position) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(v, "scaleX", 1f, 1.2f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(v, "scaleY", 1f, 1.2f);
        AnimatorSet set = new AnimatorSet();
        set.play(scaleX).with(scaleY);
        set.start();
    }

    /** Set animation when focus is lost */
    private void focusOut(View v, int position) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(v, "scaleX", 1.2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(v, "scaleY", 1.2f, 1f);
        AnimatorSet set = new AnimatorSet();
        set.play(scaleX).with(scaleY);
        set.start();
    }
}