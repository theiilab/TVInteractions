package com.yuanren.tvinteractions.view.movie_playback;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.DefaultTimeBar;
import com.google.android.exoplayer2.ui.PlayerView;
import com.yuanren.tvinteractions.R;
import com.yuanren.tvinteractions.model.Movie;
import com.yuanren.tvinteractions.model.MovieList;
import com.yuanren.tvinteractions.view.x_ray.SpaceItemDecoration;
import com.yuanren.tvinteractions.view.x_ray.XRayCardListAdapter;

import org.jetbrains.annotations.NotNull;

public class PlaybackFragment extends Fragment {
    private static final String TAG = "PlaybackFragment";

    private TextView title;
    private ImageButton backBtn;
    private ImageButton videoStatusIndicator;
    private PlayerView playerView;
    private ExoPlayer exoPlayer;
    private DefaultTimeBar progressBar;
    private RecyclerView recyclerView;
    private XRayCardListAdapter adapter;


    private boolean playWhenReady = true;
    private int currentItem = 0;
    private long playbackPosition = 0L;

    private Movie movie;

    public PlaybackFragment() {
        // Required empty public constructor
    }

    public static PlaybackFragment newInstance(long id) {
        Log.d(TAG, "Item: " + String.valueOf(id));

        PlaybackFragment fragment = new PlaybackFragment();
        Bundle args = new Bundle();
        args.putLong(PlaybackActivity.SELECTED_MOVIE_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_playback, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // get selected movie
        movie = MovieList.findBy((int)getArguments().getLong(PlaybackActivity.SELECTED_MOVIE_ID));

        // set x-ray row dynamically
        recyclerView = view.findViewById(R.id.x_ray_content);
        LinearLayoutManager ll = new LinearLayoutManager(getContext());
        ll.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(ll);
        recyclerView.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.dimens_10dp)));
        adapter = new XRayCardListAdapter(movie.getXRayItems());
        recyclerView.setAdapter(adapter);

        // set up UI components
        title = view.findViewById(R.id.title);
        backBtn = view.findViewById(R.id.back_btn);
        videoStatusIndicator = view.findViewById(R.id.video_status_indicator);
        playerView = view.findViewById(R.id.video_player);
        progressBar = view.findViewById(R.id.exo_progress);

        title.setText(movie.getTitle());
        initializePlayer(movie.getVideoUrl());

        backBtn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    backBtn.setBackgroundResource(R.drawable.ic_arrow_left_selected);
                } else {
                    backBtn.setBackgroundResource(R.drawable.ic_arrow_left_unselected);
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        videoStatusIndicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoStatusIndicator.setBackground(getResources().getDrawable(R.drawable.shape_x_ray_media_controller_round_corner));
                if (playWhenReady) {
                    playWhenReady = false;
                    videoStatusIndicator.setBackgroundResource(R.drawable.ic_playback_play_large);
                    exoPlayer.pause();
                } else {
                    playWhenReady = true;
                    videoStatusIndicator.setBackgroundResource(R.drawable.ic_playback_pause_large);
                    exoPlayer.play();
                }
                Log.d(TAG, "videoStatusIndicator - onClick");
                Animation animation = new AlphaAnimation(1.0f, 0.0f);
                animation.setDuration(1000);
                animation.setFillAfter(true);
                videoStatusIndicator.startAnimation(animation);
                Log.d(TAG, String.valueOf(videoStatusIndicator.getAlpha()));
            }
        });
    }

    private void initializePlayer(String url) {
        exoPlayer = new ExoPlayer.Builder(getContext()).build();
        // Bind the player to the view.
        playerView.setPlayer(exoPlayer);
        // resize and rescale the video to fit the screen
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        exoPlayer.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
        // Build the media item.
        MediaItem mediaItem = MediaItem.fromUri(url);
        // Set the media item to be played.
        exoPlayer.setMediaItem(mediaItem);
        // Prepare the player.
        exoPlayer.prepare();
        // Start the playback.
        exoPlayer.play();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (exoPlayer == null) {
            initializePlayer(movie.getVideoUrl());
        } else {
//            exoPlayer.prepare();
            exoPlayer.play();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        exoPlayer.pause();
    }

    @Override
    public void onStop() {
        super.onStop();
        exoPlayer.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        exoPlayer.release();
        exoPlayer = null;
    }
}