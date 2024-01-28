package com.yuanren.tvinteractions.view.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.yuanren.tvinteractions.R;
import com.yuanren.tvinteractions.base.NavigationMenuCallback;
import com.yuanren.tvinteractions.base.SocketUpdateCallback;
import com.yuanren.tvinteractions.model.Movie;
import com.yuanren.tvinteractions.model.MovieList;
import com.yuanren.tvinteractions.network.SearchSocketService;
import com.yuanren.tvinteractions.view.base.SpaceItemDecoration;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment implements SocketUpdateCallback {
    private static final String TAG = "SearchFragment";

    private NavigationMenuCallback navigationMenuCallback;

    private ConstraintLayout keyboard;
    private RecyclerView recyclerView;
    private EditText inputField;

    private SearchListAdapter adapter;
    private List<Movie> movies;
    private StringBuilder userInput = new StringBuilder();

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    public void setNavigationMenuCallback(NavigationMenuCallback callback) {
        this.navigationMenuCallback = callback;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        movies = MovieList.setUpSearchDummyMovies();
        movies.addAll(MovieList.getRealList());

        // grid of movies
        recyclerView = view.findViewById(R.id.search_movies);
        GridLayoutManager gl = new GridLayoutManager(getContext(), 5);
        recyclerView.setLayoutManager(gl);
        recyclerView.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.search_margin_between_sm),0, getResources().getDimensionPixelSize(R.dimen.search_margin_between_sm),0));
        adapter = new SearchListAdapter(movies);
        recyclerView.setAdapter(adapter);

        // set up listeners for keyboard
        keyboard = view.findViewById(R.id.search_keyboard);
        for (int i = 0; i < keyboard.getChildCount(); i++) {
            View v = ((LinearLayout)keyboard.getChildAt(i)).getChildAt(0);
            v.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int i, KeyEvent keyEvent) {
                    if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_DPAD_LEFT) {
                        Log.d(TAG, "OnKeyRight - Keyboard the most left keys pressed");
                        navigationMenuCallback.navMenuToggle(true);
                    }
                    return false;
                }
            });
        }

        // manage input watcher
        inputField = view.findViewById(R.id.search_input);
        inputField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d(TAG, "Input text changed");

                if (editable.toString().equals("")) {
                    adapter.update(movies);
                } else {
                    adapter.update(getSearchResult(editable.toString()));
                }
            }
        });

        SearchSocketService.setSocketUpdateCallback(this);
        SearchSocketService.start();
    }

    public void onKeyClick(View v) {
        if (v.getTag().toString().equals("SPACE")) {
            userInput.append(" ");
        } else if (v.getTag().toString().equals("DEL")) {
            if (userInput.length() > 0 ) {
                userInput.deleteCharAt(userInput.length() - 1);
            }
        } else {
            userInput.append(v.getTag().toString().toLowerCase());
        }
        inputField.setText(userInput.toString());
    }

    private List<Movie> getSearchResult(String searchName) {
        List<Movie> result = new ArrayList<>();
        Map<Movie, Integer> map = new HashMap<>();

        for (Movie movie: movies) {
//            int score = minDistance(movie.getTitle().toLowerCase(), searchName);
            int score = prefixMatch(movie.getTitle().toLowerCase(), searchName);

            if (score != 0) {
                map.put(movie, score);
            }
        }

        // Create a list from elements of HashMap
        List<Map.Entry<Movie, Integer> > list =
                new LinkedList<Map.Entry<Movie, Integer>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Movie, Integer> >() {
            @Override
            public int compare(Map.Entry<Movie, Integer> o1, Map.Entry<Movie, Integer> o2) {
                if (o1.getValue() < o2.getValue()) {
                    return -1;
                } else if (Objects.equals(o1.getValue(), o2.getValue())) {
                    return o1.getKey().getTitle().length() - o2.getKey().getTitle().length();
                } else {
                    return 1;
                }
//                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        for (int i = 0; i < list.size(); ++i) {
            result.add(list.get(i).getKey());
        }

        return result;
    }

    private int prefixMatch(String movieName, String searchName) {
        if (searchName.length() > movieName.length()) {
            return 0;
        }

        if (searchName.equals(movieName.substring(0, searchName.length()))) {
            return searchName.length();
        }
        return 0;
    }

//    private int minDistance(String s1, String s2) {
//        int[][] dp = new int[s1.length() + 1][s2.length() + 1];
//
//        for (int i = 0; i < s1.length() + 1; ++i) {
//            dp[i][0] = i;
//        }
//
//        for (int j = 0; j < s2.length() + 1; ++j) {
//            dp[0][j] = j;
//        }
//
//        for (int i = 1; i < s1.length() + 1; ++i) {
//            for (int j = 1; j < s2.length() + 1; ++j) {
//                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
//                    dp[i][j] = dp[i - 1][j - 1];
//                } else {
//                    dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + 1;
//                }
//            }
//        }
//        return dp[s1.length()][s2.length()];
//    }

    // for smartwatch inu[put
    @Override
    public void update(Handler handler, String text) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                inputField.setText(text);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // clear the search result in text field and grid because setText will call afterTextChange and reset the movie grid
        inputField.setText("");

        SearchSocketService.setSocketUpdateCallback(this);
        SearchSocketService.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SearchSocketService.stop();
    }
}