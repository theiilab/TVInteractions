package com.yuanren.tvinteractions.view.search;

import android.content.Context;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuanren.tvinteractions.R;
import com.yuanren.tvinteractions.base.NavigationMenuCallback;
import com.yuanren.tvinteractions.base.OnKeyListener;
import com.yuanren.tvinteractions.base.SocketUpdateCallback;
import com.yuanren.tvinteractions.log.Action;
import com.yuanren.tvinteractions.log.ActionType;
import com.yuanren.tvinteractions.log.Block;
import com.yuanren.tvinteractions.log.Session;
import com.yuanren.tvinteractions.log.Task;
import com.yuanren.tvinteractions.model.Movie;
import com.yuanren.tvinteractions.model.MovieList;
import com.yuanren.tvinteractions.network.SearchSocketService;
import com.yuanren.tvinteractions.utils.FileUtils;
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
    private Context context;
    private TextView taskReminder;
    private Session session;
    private Block block;
    private Task task;
    private boolean startFlag = false;
    private Long actionStartTime = 0L;

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
        context = view.getContext().getApplicationContext();
        session = (Session) view.getContext().getApplicationContext();
        block = session.getCurrentBlock();
        task = session.getCurrentBlock().getCurrentTask();
        /** --------------- */
        movies = setUpSearchDummyMovies();
//        movies.addAll(MovieList.getRealList());

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
        taskReminder.setText("Block 1: Search movie " + task.id + " on the sheet");
        /** --------------- */

        /** ----- log ----- */
        View.OnKeyListener onKeyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (!startFlag) {
                        startFlag = true;
                        block.startTime = System.currentTimeMillis();
                        task.startTime = block.startTime;
                    }
                    actionStartTime = System.currentTimeMillis();
                    task.actionsPerTask++;
                } else if (event.getAction() == KeyEvent.ACTION_UP) {
                    Action action = null;
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_LEFT:
                            action = new Action(session, "", ActionType.TYPE_ACTION_LEFT.name, TAG, actionStartTime, System.currentTimeMillis());
                            FileUtils.writeRaw(getContext(), action);
                            break;
                        case KeyEvent.KEYCODE_DPAD_RIGHT:
                            action = new Action(session, "", ActionType.TYPE_ACTION_RIGHT.name, TAG, actionStartTime, System.currentTimeMillis());
                            FileUtils.writeRaw(getContext(), action);
                            break;
                        case KeyEvent.KEYCODE_DPAD_UP:
                            action = new Action(session, "", ActionType.TYPE_ACTION_UP.name, TAG, actionStartTime, System.currentTimeMillis());
                            FileUtils.writeRaw(getContext(), action);
                            break;
                        case KeyEvent.KEYCODE_DPAD_DOWN:
                            action = new Action(session, "", ActionType.TYPE_ACTION_DOWN.name, TAG, actionStartTime, System.currentTimeMillis());
                            FileUtils.writeRaw(getContext(), action);
                            break;
                    }
                }
                return false;
            }
        };
        /** --------------- */

        View.OnKeyListener leftColumnOnKeyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    if (!startFlag) {
                        startFlag = true;
                        block.startTime = System.currentTimeMillis();
                        task.startTime = block.startTime;
                    }
                    actionStartTime = System.currentTimeMillis();

                    if (i == KeyEvent.KEYCODE_DPAD_LEFT) {
                        Log.d(TAG, "OnKeyRight - Keyboard the most left keys pressed");
                        navigationMenuCallback.navMenuToggle(true);
                    }
                } else if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    task.actionsPerTask++;

                    Action action = null;
                    switch (i) {
                        case KeyEvent.KEYCODE_DPAD_LEFT:
                            action = new Action(session, "", ActionType.TYPE_ACTION_LEFT.name, TAG, actionStartTime, System.currentTimeMillis());
                            FileUtils.writeRaw(getContext(), action);
                            break;
                        case KeyEvent.KEYCODE_DPAD_RIGHT:
                            action = new Action(session, "", ActionType.TYPE_ACTION_RIGHT.name, TAG, actionStartTime, System.currentTimeMillis());
                            FileUtils.writeRaw(getContext(), action);
                            break;
                        case KeyEvent.KEYCODE_DPAD_UP:
                            action = new Action(session, "", ActionType.TYPE_ACTION_UP.name, TAG, actionStartTime, System.currentTimeMillis());
                            FileUtils.writeRaw(getContext(), action);
                            break;
                        case KeyEvent.KEYCODE_DPAD_DOWN:
                            action = new Action(session, "", ActionType.TYPE_ACTION_DOWN.name, TAG, actionStartTime, System.currentTimeMillis());
                            FileUtils.writeRaw(getContext(), action);
                            break;
//                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        // ENTER is handled in onKeyClick()
                    }
                }
                return false;
            }
        };

        // set up listeners for keyboard
        for (int i = 0; i < keyboard.getChildCount(); i++) {
            LinearLayout child = (LinearLayout) keyboard.getChildAt(i);
            for (int j = 0; j < child.getChildCount(); j++) {
                View grandChild = child.getChildAt(j);
                if (grandChild instanceof Button) {
                    ((Button) grandChild).setOnKeyListener(onKeyListener);
                } else {
                    ((ImageButton) grandChild).setOnKeyListener(onKeyListener);
                }
            }

            // for left column, control when to open nav
            View v = ((LinearLayout)keyboard.getChildAt(i)).getChildAt(0);
            v.setOnKeyListener(leftColumnOnKeyListener);
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
//                Log.d(TAG, "Input text changed");

                if (editable.toString().equals("")) {
                    adapter.update(movies);
                    userInput.setLength(0);
                } else {
                    if (editable.toString().trim().length() != 0) {
                        adapter.update(getSearchResult(editable.toString()));
                    }
                }
//                recyclerView.invalidate();
                adapter.notifyDataSetChanged();
            }
        });

        SearchSocketService.setSocketUpdateCallback(this);
        SearchSocketService.start();
    }

    public void onKeyClick(View v) {
        /** ----- log ----- */
        if (!startFlag) {
            startFlag = true;
            block.startTime = System.currentTimeMillis();
            task.startTime = block.startTime;
        }//        metrics.actionsPerTask++;  // already add 1 in the onKeyListener above
        /** --------------- */

        if (v.getTag().toString().equals("SPACE")) {
            userInput.append(" ");

            /** ----- log ----- */
            Action action = new Action(session, "", v.getTag().toString(), TAG, actionStartTime, System.currentTimeMillis());
            FileUtils.writeRaw(getContext(), action);
            /** ----- log ----- */
        } else if (v.getTag().toString().equals("DEL")) {
            if (userInput.length() > 0 ) {
                userInput.deleteCharAt(userInput.length() - 1);

                /** ----- log ----- */
                task.backspaceCount++;

                Action action = new Action(session, "", v.getTag().toString(), TAG, actionStartTime, System.currentTimeMillis());
                FileUtils.writeRaw(getContext(), action);
            } else {
                /** ----- log ----- */
                task.actionsPerTask--;  // account for correction if user keep deleting on empty string
                /** --------------- */
            }
        } else {
            userInput.append(v.getTag().toString().toLowerCase());

            /** ----- log ----- */
            Action action = new Action(session, "", v.getTag().toString(), TAG, actionStartTime, System.currentTimeMillis());
            FileUtils.writeRaw(getContext(), action);
            /** --------------- */
        }
        inputField.setText(userInput.toString());
    }

    /** ----- log ----- */
    private List<Movie> setUpSearchDummyMovies() {
//        if (session.id == 3) {
//            int length;
//            if (block.id == 1) {
//                length = 50;
//            } else if (block.id == 2) {
//                length = 100;
//            } else {
//                length = 250;
//            }
//            return MovieList.setUpSearchDummyMovies(length);
//        }
        return MovieList.setUpSearchDummyMovies(-1); // set up movies as much as had
    }

    @Override
    public boolean onItemClick(View v, int keyCode, KeyEvent event, int position) {
        return false;
    }

    /** ----- log ----- */
    @Override
    public boolean onItemClick(View v, int keyCode, KeyEvent event, int position, Movie movie) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            actionStartTime = System.currentTimeMillis();
            task.actionsPerTask++;

            switch (keyCode) {
                case KeyEvent.KEYCODE_ENTER:
                case KeyEvent.KEYCODE_DPAD_CENTER:
                    task.totalCharacterEntered = userInput.length();
                    Log.d(TAG, "totalCharacterEntered: " + task.totalCharacterEntered);
                    Log.d(TAG, "Movie targeted: " + task.targetMovie);
                    Log.d(TAG, "Movie selected: " + movie.getTitle());
                    if (movie.getTitle().equals(task.targetMovie)) {
                        setLogData(movie, position);
                        Log.d(TAG, "Log data set");
                    } else {
                        task.incorrectTitleCount++;
                    }
                    break;
            }
        } else if (event.getAction() == KeyEvent.ACTION_UP) {
            Action action = null;
            switch (keyCode) {
                case KeyEvent.KEYCODE_ENTER:
                case KeyEvent.KEYCODE_DPAD_CENTER:
                    action = new Action(session, movie.getTitle(), ActionType.TYPE_ACTION_ENTER.name, TAG, actionStartTime, System.currentTimeMillis());
                    break;
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    action = new Action(session, movie.getTitle(), ActionType.TYPE_ACTION_LEFT.name, TAG, actionStartTime, System.currentTimeMillis());
                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    action = new Action(session, movie.getTitle(), ActionType.TYPE_ACTION_RIGHT.name, TAG, actionStartTime, System.currentTimeMillis());
                    break;
                case KeyEvent.KEYCODE_DPAD_UP:
                    action = new Action(session, movie.getTitle(), ActionType.TYPE_ACTION_UP.name, TAG, actionStartTime, System.currentTimeMillis());
                    break;
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    action = new Action(session, movie.getTitle(), ActionType.TYPE_ACTION_DOWN.name, TAG, actionStartTime, System.currentTimeMillis());
                    break;
            }
            FileUtils.writeRaw(getContext(), action);
        }
        return false;
    }

    private void setLogData(Movie movie, int position) {
        task.selectedMovie = movie.getTitle();
        task.endTime = System.currentTimeMillis();
        task.positionOnSelect = position;
        block.actionsPerBlock += task.actionsPerTask;
        FileUtils.write(context, task);

        // advance to the next
        if (block.id == session.SESSION_3_NUM_BLOCK && task.id == block.SESSION_3_NUM_TASK) {
            block.endTime = task.endTime;
            FileUtils.write(context, block);

            // update reminder
            taskReminder.setText("Session 3 Accomplished");
        } else if (block.id < session.SESSION_3_NUM_BLOCK && task.id == block.SESSION_3_NUM_TASK) {
            block.endTime = task.endTime;
            FileUtils.write(context, block);

            // next block
            block = session.nextBlock();
            task = block.getCurrentTask();
            movies = setUpSearchDummyMovies();
//            movies.addAll(MovieList.getRealList());
            inputField.setText("");

            // update reminder
            taskReminder.setText("Block " + block.id + ": Search movie " + task.id + " on the sheet");
            block.startTime = System.currentTimeMillis();
            task.startTime = block.startTime;;
        } else {
            // next task
            inputField.setText("");
            task = block.nextTask();
            task.startTime = System.currentTimeMillis();

            // update reminder
            taskReminder.setText("Block " + block.id + ": Search movie " + task.id + " on the sheet");
        }
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
                    return 1;
                } else if (Objects.equals(o1.getValue(), o2.getValue())) {
                    return o2.getKey().getCategoryIndex() - o1.getKey().getCategoryIndex();
                } else {
                    return -1;
                }
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
                /** ----- log ----- */
//                metrics.totalCharacterEntered = text.length();
                Action action = new Action(session, "", text, TAG, System.currentTimeMillis(), System.currentTimeMillis());
                FileUtils.writeRaw(getContext(), action);
                /** --------------- */

                userInput.setLength(0);
                userInput.append(text);
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