package com.yuanren.tvinteractions.view.nav_drawer;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Typeface;
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
import com.yuanren.tvinteractions.log.Metrics;

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
    public static final int TYPE_VIEW_TV_CHANNELS = 4;
    public static final int TYPE_VIEW_SETTINGS = 5;

    private ConstraintLayout constraintLayout;

    // nav menu items
    private ImageButton searchIB;
    private TextView searchTV;
    private ImageButton searchUnderlineIB;
    private ImageButton homeIB;
    private TextView homeTV;
    private ImageButton homeUnderlineIB;
    private ImageButton moviesIB;
    private TextView moviesTV;
    private ImageButton moviesUnderlineIB;
    private ImageButton tvChannelsIB;
    private TextView tvChannelsTV;
    private ImageButton tvChannelsUnderlineIB;
    private ImageButton settingsIB;
    private TextView settingsTV;
    private ImageButton settingsUnderlineIB;

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
        constraintLayout = getActivity().findViewById(R.id.nav_drawer_details);
        searchIB = view.findViewById(R.id.search_IB);
        searchTV = view.findViewById(R.id.search_TV);
        searchUnderlineIB = view.findViewById(R.id.search_underline_IB);
        homeIB = view.findViewById(R.id.home_IB);
        homeTV = view.findViewById(R.id.home_TV);
        homeUnderlineIB = view.findViewById(R.id.home_underline_IB);
        moviesIB = view.findViewById(R.id.movies_IB);
        moviesTV = view.findViewById(R.id.movies_TV);
        moviesUnderlineIB = view.findViewById(R.id.movie_underline_IB);
        tvChannelsIB = view.findViewById(R.id.tv_channels_IB);
        tvChannelsTV = view.findViewById(R.id.tv_channels_TV);
        tvChannelsUnderlineIB = view.findViewById(R.id.tv_channels_underline_IB);
        settingsIB = view.findViewById(R.id.settings_IB);
        settingsTV = view.findViewById(R.id.settings_TV);
        settingsUnderlineIB = view.findViewById(R.id.settings_underline_IB);

        Metrics metrics = (Metrics) getActivity().getApplicationContext();
        if (metrics.session == 3) {
            searchIB.setImageResource(R.drawable.ic_nav_search_selected);
            searchUnderlineIB.setVisibility(View.VISIBLE);
            lastSelectedMenuItem = TYPE_VIEW_SEARCH;
            currentSelectedFragmentType = TYPE_VIEW_SEARCH;
        } else {
            // by default selection
            homeIB.setImageResource(R.drawable.ic_nav_home_selected);
            homeUnderlineIB.setVisibility(View.VISIBLE);
            lastSelectedMenuItem = TYPE_VIEW_HOME;
            currentSelectedFragmentType = TYPE_VIEW_HOME;
        }

        // set listeners for each mav menu item
        searchListeners();
        homeListeners();
        moviesListener();
        tvShowsListener();
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
                if (isNavigationOpen()) {
                    if (b) {
                        setMenuIconFocusView(searchIB, R.drawable.ic_nav_search_selected);
                        setMenuNameFocusView(searchTV, true);
                        focusIn(searchIB, 0);
                        searchUnderlineIB.setVisibility(View.VISIBLE);
                    } else {
                        setMenuIconFocusView(searchIB, R.drawable.ic_nav_search_unselected);
                        setMenuNameFocusView(searchTV, false);
                        focusOut(searchIB, 0);
                        searchUnderlineIB.setVisibility(View.GONE);
                    }
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
                            closeNav(); // closeNav() must be called at last to avoid focus change
                            break;
                        case KeyEvent.KEYCODE_DPAD_RIGHT:
                            navigationStateListener.onStateChanged(false, lastSelectedMenuItem);
                            closeNav();
                            break;
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
                if (isNavigationOpen()) {
                    if (b) {
                        setMenuIconFocusView(homeIB, R.drawable.ic_nav_home_selected);
                        setMenuNameFocusView(homeTV, true);
                        focusIn(homeIB, 0);
                        homeUnderlineIB.setVisibility(View.VISIBLE);
                    } else {
                        setMenuIconFocusView(homeIB, R.drawable.ic_nav_home_unselected);
                        setMenuNameFocusView(homeTV, false);
                        focusOut(homeIB, 0);
                        homeUnderlineIB.setVisibility(View.GONE);
                    }
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
                            break;
                    }
                }
                return false;
            }
        });
    }

    private void moviesListener() {
        moviesIB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                // check if the button is focused
                if (isNavigationOpen()) {
                    if (b) {
                        setMenuIconFocusView(moviesIB, R.drawable.ic_nav_movie_selected);
                        setMenuNameFocusView(moviesTV, true);
                        focusIn(moviesIB, 0);
                        moviesUnderlineIB.setVisibility(View.VISIBLE);
                    } else {
                        setMenuIconFocusView(moviesIB, R.drawable.ic_nav_movie_unselected);
                        setMenuNameFocusView(moviesTV, false);
                        focusOut(moviesIB, 0);
                        moviesUnderlineIB.setVisibility(View.GONE);
                    }
                }
            }
        });

        moviesIB.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {//only when key is pressed down
                    switch (i) {
                        case KeyEvent.KEYCODE_ENTER:
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                            lastSelectedMenuItem = TYPE_VIEW_MOVIES;
                            navigationStateListener.onStateChanged(false, lastSelectedMenuItem);
                            fragmentChangeListener.switchFragment(TYPE_VIEW_MOVIES);
                            closeNav();
                            break;
                        case KeyEvent.KEYCODE_DPAD_RIGHT:
                            navigationStateListener.onStateChanged(false, lastSelectedMenuItem);
                            closeNav();
                            break;
                    }
                }
                return false;
            }
        });
    }

    private void tvShowsListener() {
        tvChannelsIB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                // check if the button is focused
                if (isNavigationOpen()) {
                    if (b) {
                        setMenuIconFocusView(tvChannelsIB, R.drawable.ic_nav_tv_channels_selected);
                        setMenuNameFocusView(tvChannelsTV, true);
                        focusIn(tvChannelsIB, 0);
                        tvChannelsUnderlineIB.setVisibility(View.VISIBLE);
                    } else {
                        setMenuIconFocusView(tvChannelsIB, R.drawable.ic_nav_tv_channels_unselected);
                        setMenuNameFocusView(tvChannelsTV, false);
                        focusOut(tvChannelsIB, 0);
                        tvChannelsUnderlineIB.setVisibility(View.GONE);
                    }
                }
            }
        });

        tvChannelsIB.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {//only when key is pressed down
                    switch (i) {
                        case KeyEvent.KEYCODE_ENTER:
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                            lastSelectedMenuItem = TYPE_VIEW_TV_CHANNELS;
                            navigationStateListener.onStateChanged(false, lastSelectedMenuItem);
                            fragmentChangeListener.switchFragment(TYPE_VIEW_TV_CHANNELS);
                            closeNav();
                            break;
                        case KeyEvent.KEYCODE_DPAD_RIGHT:
                            navigationStateListener.onStateChanged(false, lastSelectedMenuItem);
                            closeNav();
                            break;
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
            case TYPE_VIEW_TV_CHANNELS:
                tvChannelsIB.requestFocus();
                setMenuNameFocusView(tvChannelsTV, true);
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
            tvChannelsTV.setVisibility(visibility);
            settingsTV.setVisibility(visibility);
        } else {
            animateMenuNamesEntry(searchTV, visibility, TYPE_VIEW_SEARCH);
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
                    case TYPE_VIEW_TV_CHANNELS:
                        animateMenuNamesEntry(tvChannelsTV, visibility, viewCode + 1);
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
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT);
        constraintLayout.setLayoutParams(lp);

        //highlighting last selected menu icon
        highlightMenuSelection(lastSelectedMenuItem);
        //Setting out of focus views for menu icons, names
        unHighlightMenuSelections(lastSelectedMenuItem);
    }

    private void unHighlightMenuSelections(int lastSelectedMenuItem) {
        if (lastSelectedMenuItem != TYPE_VIEW_SEARCH) {
            setMenuIconFocusView(searchIB, R.drawable.ic_nav_search_unselected);
            setMenuNameFocusView(searchTV, false);
            searchUnderlineIB.setVisibility(View.GONE);
        }
        if (lastSelectedMenuItem !=  TYPE_VIEW_HOME) {
            setMenuIconFocusView(homeIB, R.drawable.ic_nav_home_unselected);
            setMenuNameFocusView(homeTV, false);
            homeUnderlineIB.setVisibility(View.GONE);
        }
        if (lastSelectedMenuItem !=  TYPE_VIEW_MOVIES) {
            setMenuIconFocusView(moviesIB, R.drawable.ic_nav_movie_unselected);
            setMenuNameFocusView(moviesTV, false);
            moviesUnderlineIB.setVisibility(View.GONE);
        }
        if (lastSelectedMenuItem !=  TYPE_VIEW_TV_CHANNELS) {
            setMenuIconFocusView(tvChannelsIB, R.drawable.ic_nav_tv_channels_unselected);
            setMenuNameFocusView(tvChannelsTV, false);
            tvChannelsUnderlineIB.setVisibility(View.GONE);
        }
        if (lastSelectedMenuItem !=  TYPE_VIEW_SETTINGS) {
            setMenuIconFocusView(settingsIB, R.drawable.ic_nav_settings_unselected);
            setMenuNameFocusView(settingsTV, false);
            settingsUnderlineIB.setVisibility(View.GONE);
        }
    }

    private void highlightMenuSelection(int lastSelectedMenuItem) {
        switch (lastSelectedMenuItem) {
            case TYPE_VIEW_SEARCH:
                setMenuIconFocusView(searchIB, R.drawable.ic_nav_search_selected);
                searchUnderlineIB.setVisibility(View.VISIBLE);
                break;
            case TYPE_VIEW_HOME:
                setMenuIconFocusView(homeIB, R.drawable.ic_nav_home_selected);
                homeUnderlineIB.setVisibility(View.VISIBLE);
                break;
            case TYPE_VIEW_MOVIES:
                setMenuIconFocusView(moviesIB, R.drawable.ic_nav_movie_selected);
                moviesUnderlineIB.setVisibility(View.VISIBLE);
                break;
            case TYPE_VIEW_TV_CHANNELS:
                setMenuIconFocusView(tvChannelsIB, R.drawable.ic_nav_tv_channels_selected);
                tvChannelsUnderlineIB.setVisibility(View.VISIBLE);
                break;
            case TYPE_VIEW_SETTINGS:
                setMenuIconFocusView(settingsIB, R.drawable.ic_nav_settings_selected);
                tvChannelsUnderlineIB.setVisibility(View.VISIBLE);
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
            view.setTextColor(getActivity().getColor(R.color.nav_text_highlighted));
            view.setTypeface(view.getTypeface(), Typeface.NORMAL);
        } else {
            view.setTextColor(getActivity().getColor(R.color.nav_text_unhighlighted));
            view.setTypeface(view.getTypeface(), Typeface.NORMAL);
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