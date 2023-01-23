package com.yuanren.tvinteractions.view.movie_playback;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.DefaultTimeBar;
import com.google.android.exoplayer2.ui.PlayerView;
import com.yuanren.tvinteractions.R;
import com.yuanren.tvinteractions.model.Movie;
import com.yuanren.tvinteractions.model.MovieList;
import com.yuanren.tvinteractions.view.movie_playback.x_ray.SpaceItemDecoration;
import com.yuanren.tvinteractions.view.movie_playback.x_ray.XRayCardListAdapter;

import org.jetbrains.annotations.NotNull;
import org.xml.sax.helpers.XMLReaderAdapter;

//import com.google.android.exoplayer2.upstream.DataSource;

//public class PlaybackFragment extends VideoSupportFragment {
//    private static final String TAG = "PlaybackFragment";
//
//    private PlaybackTransportControlGlue<MediaPlayerAdapter> mTransportControlGlue;
//    private Movie movie;
//
//    public PlaybackFragment() {
//        // Required empty public constructor
//    }
//
//    public static PlaybackFragment newInstance(long id) {
//        Log.d(TAG, "Item: " + String.valueOf(id));
//
//        PlaybackFragment fragment = new PlaybackFragment();
//        Bundle args = new Bundle();
//        args.putLong(PlaybackActivity.SELECTED_MOVIE_ID, id);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        // get selected movie
//        movie = MovieList.findBy((int)getArguments().getLong(PlaybackActivity.SELECTED_MOVIE_ID));
//
//        // set up adapter
//        MediaPlayerAdapter playerAdapter = new MediaPlayerAdapter(getActivity());
//        playerAdapter.setRepeatAction(PlaybackControlsRow.RepeatAction.INDEX_NONE);
//
//        //set up glue and glue host
//        VideoSupportFragmentGlueHost glueHost =
//                new VideoSupportFragmentGlueHost(PlaybackFragment.this);
//        mTransportControlGlue = new PlaybackTransportControlGlue<>(getActivity(), playerAdapter);
//        mTransportControlGlue.setHost(glueHost);
////        mTransportControlGlue.setTitle(movie.getTitle());
////        mTransportControlGlue.setSubtitle(movie.getDescription());
//        mTransportControlGlue.playWhenPrepared();
//        playerAdapter.setDataSource(Uri.parse(movie.getVideoUrl()));
//
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        if (mTransportControlGlue != null) {
//            mTransportControlGlue.pause();
//        }
//    }
//}
public class PlaybackFragment extends Fragment {
    private static final String TAG = "PlaybackFragment";

    private TextView title;
    private PlayerView playerView;
    private ExoPlayer exoPlayer;
    private ImageButton playBtn;
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
        adapter = new XRayCardListAdapter(movie.getxRayItems());
        recyclerView.setAdapter(adapter);

        title = view.findViewById(R.id.title);
        playerView = view.findViewById(R.id.video_player);
        playBtn = view.findViewById(R.id.exo_play_btn);
        progressBar = view.findViewById(R.id.exo_progress);

        title.setText(movie.getTitle());
        initializePlayer(movie.getVideoUrl());

        playerView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                Log.d(TAG, "on key pressed");
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) { //only when key is pressed down
                    switch (i) {
                        case KeyEvent.KEYCODE_ENTER:
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                            Log.d(TAG, "playView on press");
                            break;
                    }
                }
                return false;
            }
        });
    }

    private void initializePlayer(String url) {
        exoPlayer = new ExoPlayer.Builder(getContext()).build();
        // Bind the player to the view.
        playerView.setPlayer(exoPlayer);

        // Build the media item.
        MediaItem mediaItem = MediaItem.fromUri(url);
        // Set the media item to be played.
        exoPlayer.setMediaItem(mediaItem);

//        exoPlayer.addListener();

        // Prepare the player.
        exoPlayer.prepare();
        // Start the playback.
        exoPlayer.play();
    }

//    private void hideSystemUi() {
//        WindowCompat.setDecorFitsSystemWindows(window, false);
//
//        WindowInsetsControllerCompat(window, viewBinding.videoView).let { controller ->
//                controller.hide(WindowInsetsCompat.Type.systemBars())
//            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//        }
//    }

    @Override
    public void onResume() {
        super.onResume();
        if (exoPlayer == null) {
            initializePlayer(movie.getVideoUrl());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        exoPlayer.release();
    }

    @Override
    public void onStop() {
        super.onStop();
        exoPlayer.release();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        exoPlayer.release();
        exoPlayer = null;
    }
}