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
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuanren.tvinteractions.R;
import com.yuanren.tvinteractions.base.NavigationMenuCallback;
import com.yuanren.tvinteractions.base.OnKeyListener;
import com.yuanren.tvinteractions.base.SocketUpdateCallback;
import com.yuanren.tvinteractions.log.Action;
import com.yuanren.tvinteractions.log.ActionType;
import com.yuanren.tvinteractions.log.Metrics;
import com.yuanren.tvinteractions.log.TaskType;
import com.yuanren.tvinteractions.model.Movie;
import com.yuanren.tvinteractions.model.MovieList;
import com.yuanren.tvinteractions.network.SearchSocketService;
import com.yuanren.tvinteractions.utils.FileUtils;
import com.yuanren.tvinteractions.view.base.SpaceItemDecoration;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EventListener;
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
public class SearchFragment extends Fragment implements SocketUpdateCallback, OnKeyListener {
    private static final String TAG = "SearchFragment";

    private NavigationMenuCallback navigationMenuCallback;

    private ConstraintLayout keyboard;
    private RecyclerView recyclerView;
    private EditText inputField;

    private SearchListAdapter adapter;
    private List<Movie> movies;
    private StringBuilder userInput = new StringBuilder();

    /** -------- log -------- */
    private TextView taskReminder;
    private Metrics metrics;
    private int task = 1;

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
        /** ----- log ----- */
        metrics = (Metrics) getActivity().getApplicationContext();
        metrics.task = "1";
        metrics.startTime = System.currentTimeMillis();
        metrics.targetMovie = metrics.getFirstTargetMovie(); // must set!
        /** --------------- */
        movies = setUpSearchDummyMovies();
        movies.addAll(MovieList.getRealList());

        // grid of movies
        recyclerView = view.findViewById(R.id.search_movies);
        GridLayoutManager gl = new GridLayoutManager(getContext(), 5);
        recyclerView.setLayoutManager(gl);
        recyclerView.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.search_margin_between_sm),0, getResources().getDimensionPixelSize(R.dimen.search_margin_between_sm),0));
        adapter = new SearchListAdapter(movies);
        adapter.setOnKeyListener(this);
        recyclerView.setAdapter(adapter);

        keyboard = view.findViewById(R.id.search_keyboard);
        inputField = view.findViewById(R.id.search_input);
        /** ----- log ----- */
        taskReminder = view.findViewById(R.id.task_reminder);
        taskReminder.setText("Block 1: Search movie " + task + " on the sheet");
        /** --------------- */

        // set up listeners for keyboard
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
                    userInput.setLength(0);
                } else {
                    adapter.update(getSearchResult(editable.toString()));
                }
                recyclerView.invalidate();
                adapter.notifyDataSetChanged();
            }
        });

        SearchSocketService.setSocketUpdateCallback(this);
        SearchSocketService.start();
    }

    public void onKeyClick(View v) {
        /** ----- log ----- */
        metrics.actionsPerTask++;

        Action action = new Action(metrics, "", v.getTag().toString(), TAG, System.currentTimeMillis(), System.currentTimeMillis());
        FileUtils.writeRaw(getContext(), action);
        /** --------------- */

        if (v.getTag().toString().equals("SPACE")) {
            userInput.append(" ");
        } else if (v.getTag().toString().equals("DEL")) {
            if (userInput.length() > 0 ) {
                userInput.deleteCharAt(userInput.length() - 1);
            }

            /** ----- log ----- */
            metrics.backspaceCount++;
            /** --------------- */
        } else {
            userInput.append(v.getTag().toString().toLowerCase());

            /** ----- log ----- */
            metrics.totalCharacterEntered++;
            /** --------------- */
        }
        inputField.setText(userInput.toString());
    }

    /** ----- log ----- */
    private List<Movie> setUpSearchDummyMovies() {
        if (metrics.session == 3) {
            int length;
            if (metrics.block == 1) {
                length = 50;
            } else if (metrics.block == 2) {
                length = 100;
            } else {
                length = 250;
            }
            return MovieList.setUpSearchDummyMovies(length);
        }
        return MovieList.setUpSearchDummyMovies(-1); // set up movies as much as had
    }

    @Override
    public boolean onItemClick(View v, int keyCode, KeyEvent event, int position) {
        return false;
    }

    /** ----- log ----- */
    @Override
    public boolean onItemClick(View v, int keyCode, KeyEvent event, Movie movie) {
        if (event.getAction() == KeyEvent.ACTION_UP) {
            Action action = null;
            switch (keyCode) {
                case KeyEvent.KEYCODE_ENTER:
                case KeyEvent.KEYCODE_DPAD_CENTER:
                    if (movie.getTitle().equals(metrics.targetMovie)){
                        metrics.task = String.valueOf(task);
                        metrics.selectedMovie = movie.getTitle();
                        metrics.endTime = System.currentTimeMillis();
                        metrics.taskCompletionTime = metrics.endTime - metrics.startTime;
                        FileUtils.write(v.getContext(), metrics);
                        action = new Action(metrics, movie.getTitle(), ActionType.TYPE_ACTION_ENTER.name, TAG, event.getDownTime(), event.getEventTime());

                        // clear data and advance to the next task
                        if (metrics.block == metrics.SESSION_3_NUM_BLOCK && task == metrics.SESSION_3_NUM_TASK) {
                            clearLogData();
                            taskReminder.setText("Session 3 Accomplished");
                        } else if (metrics.block < metrics.SESSION_3_NUM_BLOCK && task == metrics.SESSION_3_NUM_TASK) {
                            movies = setUpSearchDummyMovies();
                            movies.addAll(MovieList.getRealList());
                            inputField.setText("");
                            metrics.nextBlock();
                            task = 1;

                            taskReminder.setText("Block " + metrics.block + ": Search movie " + task + " on the sheet");
                        } else {
                            inputField.setText("");
                            metrics.nextTask();
                            task++;

                            taskReminder.setText("Block " + metrics.block + ": Search movie " + task + " on the sheet");
                        }
                        metrics.startTime = System.currentTimeMillis();
                    } else {
                        metrics.incorrectTitleCount++;
                    }
                    break;
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    action = new Action(metrics, movie.getTitle(), ActionType.TYPE_ACTION_LEFT.name, TAG, event.getDownTime(), event.getEventTime());
                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    action = new Action(metrics, movie.getTitle(), ActionType.TYPE_ACTION_RIGHT.name, TAG, event.getDownTime(), event.getEventTime());
                    break;
                case KeyEvent.KEYCODE_DPAD_UP:
                    action = new Action(metrics, movie.getTitle(), ActionType.TYPE_ACTION_UP.name, TAG, event.getDownTime(), event.getEventTime());
                    break;
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    action = new Action(metrics, movie.getTitle(), ActionType.TYPE_ACTION_DOWN.name, TAG, event.getDownTime(), event.getEventTime());
                    break;
                default:
                    action = new Action(metrics, movie.getTitle(), ActionType.TYPE_ACTION_DIRECTION.name, TAG, event.getDownTime(), event.getEventTime());
                    break;
            }
            FileUtils.writeRaw(getContext(), action);
        }
        return false;
    }

    private void clearLogData() {
        task = 0;
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

    // for smartwatch inu[put
    @Override
    public void update(Handler handler, String text) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                /** ----- raw log ----- */
                Action action = new Action(metrics, "", text, TAG, System.currentTimeMillis(), System.currentTimeMillis());
                FileUtils.writeRaw(getContext(), action);
                /** ------------------- */

                inputField.setText(text);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // clear the search result in text field and grid because setText will call afterTextChange and reset the movie grid
        inputField.setText("");
        userInput.setLength(0);

        SearchSocketService.setSocketUpdateCallback(this);
        SearchSocketService.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SearchSocketService.stop();
    }
}