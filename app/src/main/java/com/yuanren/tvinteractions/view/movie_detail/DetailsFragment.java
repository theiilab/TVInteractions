package com.yuanren.tvinteractions.view.movie_detail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.leanback.app.RowsSupportFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ListRow;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yuanren.tvinteractions.R;
import com.yuanren.tvinteractions.model.Movie;
import com.yuanren.tvinteractions.model.MovieList;
import com.yuanren.tvinteractions.view.base.CardPresenter;
import com.yuanren.tvinteractions.view.base.RowPresenterSelector;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {
    private static final String TAG = "DetailsFragment";
    private static final String SELECTED_MOVIE_ID = "selectedMovieId";

    // num of related movies/recommadations
    private static final int NUM_COLS = 15;
    private ArrayObjectAdapter mRowsAdapter = new ArrayObjectAdapter();

    private ImageView backgroundImage;
    private TextView title;
    private TextView description;
    private TextView studio;
    private TextView category;
    private ImageButton playIB;
    private TextView playTV;
    private ImageView restartIB;
    private TextView restartTV;
    private ImageButton trailerIB;
    private TextView trailerTV;
    private ImageButton myListIB;
    private TextView myListTV;

    private Movie movie;
    private List<Movie> list;

    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance(long id) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putLong(SELECTED_MOVIE_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        addRowView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // get movies list and selected movie
        list = MovieList.getList();
        movie = list.get((int)getArguments().getLong(SELECTED_MOVIE_ID));

        // init views
        backgroundImage = view.findViewById(R.id.background_image);
        title = view.findViewById(R.id.title);
        description = view.findViewById(R.id.description);
        studio = view.findViewById(R.id.studio);
        category = view.findViewById(R.id.category);
        playIB = view.findViewById(R.id.play_IB);
        playTV = view.findViewById(R.id.play_TV);
        restartIB = view.findViewById(R.id.restart_IB);
        restartTV = view.findViewById(R.id.restart_TV);
        trailerIB = view.findViewById(R.id.trailer_IB);
        trailerTV = view.findViewById(R.id.trailer_TV);
        myListIB = view.findViewById(R.id.watch_list_IB);
        myListTV = view.findViewById(R.id.watch_list_TV);

        title.setText(movie.getTitle());
        description.setText(movie.getDescription());
        studio.setText(movie.getStudio());
        category.setText(" • Romance");
        Glide.with(getContext())
                .load(movie.getBackgroundImageUrl())
                .centerCrop()
                .into(backgroundImage);

        // initially focused by default
        playIB.setBackground(getActivity().getDrawable(R.drawable.shape_round_corner_focused));

        playListener();
        restartListener();
        trailerListener();
        playListListener();
    }

    private void playListener() {
        playIB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                // is focused
                if (b) {
                    playIB.setBackground(getActivity().getDrawable(R.drawable.shape_round_corner_focused));
                } else {
                    playIB.setBackground(getActivity().getDrawable(R.drawable.shape_round_corner_unfocused));
                }
            }
        });
    }

    private void restartListener() {
        restartIB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                // is focused
                if (b) {
                    restartIB.setBackground(getActivity().getDrawable(R.drawable.shape_round_corner_focused));
                    restartTV.setVisibility(View.VISIBLE);

                } else {
                    restartIB.setBackground(getActivity().getDrawable(R.drawable.shape_round_corner_unfocused));
                    restartTV.setVisibility(View.GONE);
                }
            }
        });
    }

    private void trailerListener() {
        trailerIB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                // is focused
                if (b) {
                    trailerIB.setBackground(getActivity().getDrawable(R.drawable.shape_round_corner_focused));
                    trailerTV.setVisibility(View.VISIBLE);

                } else {
                    trailerIB.setBackground(getActivity().getDrawable(R.drawable.shape_round_corner_unfocused));
                    trailerTV.setVisibility(View.GONE);
                }
            }
        });
    }

    private void playListListener() {
        myListIB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                // is focused
                if (b) {
                    myListIB.setBackground(getActivity().getDrawable(R.drawable.shape_round_corner_focused));
                    myListTV.setVisibility(View.VISIBLE);

                } else {
                    myListIB.setBackground(getActivity().getDrawable(R.drawable.shape_round_corner_unfocused));
                    myListTV.setVisibility(View.GONE);
                }
            }
        });
    }

//    private void addRowView() {
//        CardPresenter cardPresenter = new CardPresenter();
//            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
//            for (int j = 0; j < NUM_COLS; j++) {
//                listRowAdapter.add(list.get(j));
//            }
//            mRowsAdapter.add(new ListRow(listRowAdapter));
//        setAdapter(mRowsAdapter);
//    }
}