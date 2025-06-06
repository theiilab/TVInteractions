package com.yuanren.tvinteractions.view.movie_playback;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.DefaultTimeBar;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.yuanren.tvinteractions.R;
import com.yuanren.tvinteractions.model.Movie;
import com.yuanren.tvinteractions.model.MovieList;
import com.yuanren.tvinteractions.view.base.SpaceItemDecoration;
import com.yuanren.tvinteractions.view.x_ray.XRayCardListAdapter;

import org.jetbrains.annotations.NotNull;

public class PlaybackFragment extends Fragment {
    private static final String TAG = "PlaybackFragment";
    private static final int VIDEO_ACTION_PLAY = 0;
    private static final int VIDEO_ACTION_PAUSE = 1;
    private static final int VIDEO_ACTION_FORWARD = 2;
    private static final int VIDEO_ACTION_REWIND = 3;
    private static final int VIDEO_TIME_DELTA = 5000; // 5s

    //Minimum Video you want to buffer while Playing (ms)
    private static int MIN_BUFFER_DURATION = 6000;
    //Max Video you want to buffer during PlayBack
    private static int MAX_BUFFER_DURATION = 9000;
    //Min Video you want to buffer before start Playing it
    private static int MIN_PLAYBACK_START_BUFFER = 6000;
    //Min video You want to buffer when user resumes video
    private static int MIN_PLAYBACK_RESUME_BUFFER = 0;

    private PlayerView playerView;
    private TextView title;
    private ImageButton backBtn;
    private ImageButton videoStatusIndicator;
    private ImageButton playBtn;
    private ImageButton pauseBtn;
    private ExoPlayer exoPlayer;
    private DefaultTimeBar timeBar;
    private RecyclerView recyclerView;
    private ProgressBar loadingBar;

    private XRayCardListAdapter adapter;
    private boolean playWhenReady = true;
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
        movie = MovieList.getMovie((int)getArguments().getLong(PlaybackActivity.SELECTED_MOVIE_ID));

        // set x-ray row dynamically
        recyclerView = view.findViewById(R.id.x_ray_container);
        LinearLayoutManager ll = new LinearLayoutManager(getContext());
        ll.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(ll);
        recyclerView.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.dimens_10dp)));
        adapter = new XRayCardListAdapter(movie.getXRayItems());
        recyclerView.setAdapter(adapter);

        // set up UI components
        playerView = view.findViewById(R.id.video_player);
        title = view.findViewById(R.id.title);
        backBtn = view.findViewById(R.id.back_btn);
        videoStatusIndicator = view.findViewById(R.id.video_status_indicator);
        playBtn = view.findViewById(R.id.exo_play);
        pauseBtn = view.findViewById(R.id.exo_pause);
        timeBar = view.findViewById(R.id.exo_progress);
        loadingBar = view.findViewById(R.id.loading_bar);

        title.setText(movie.getTitle());
        initializePlayer(movie.getVideoUrl());

        backBtn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    backBtn.setBackgroundResource(R.drawable.ic_playback_arrow_left_selected);
                    focusIn(backBtn, 0);
                } else {
                    backBtn.setBackgroundResource(R.drawable.ic_playback_arrow_left_unselected);
                    focusOut(backBtn, 0);
                }
            }
        });
        backBtn.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                switch (i) {
                    case KeyEvent.KEYCODE_ENTER:
                    case KeyEvent.KEYCODE_DPAD_CENTER:
                    case KeyEvent.KEYCODE_BACK:
                        getActivity().finish();
                        break;
                    default:
                        Log.d(TAG, "setOnKeyListener - onKey - default");
                }
                return false;
            }
        });

        videoStatusIndicator.requestFocus(); //focused by default
        videoStatusIndicator.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                // filter out the function call for KEY_DOWN event, only working for KEY_UP event to avoid two-times calling
                if (keyEvent.getAction()!=KeyEvent.ACTION_DOWN) {
                    return true;
                }

                switch (i) {
                    case KeyEvent.KEYCODE_ENTER:
                    case KeyEvent.KEYCODE_DPAD_CENTER:
                        animateVideoIndicator(playWhenReady ? VIDEO_ACTION_PAUSE : VIDEO_ACTION_PLAY);
                        break;
                    case KeyEvent.KEYCODE_DPAD_LEFT:
                        Log.d(TAG, "rewind");
                        animateVideoIndicator(VIDEO_ACTION_REWIND);
                        break;
                    case KeyEvent.KEYCODE_DPAD_RIGHT:
                        Log.d(TAG, "forward");
                        animateVideoIndicator(VIDEO_ACTION_FORWARD);
                        break;
                    case KeyEvent.KEYCODE_DPAD_DOWN:
                        // always focus on the first x-ray item
                        recyclerView.getChildAt(0).requestFocus();
                        break;
                    case KeyEvent.KEYCODE_BACK:
                        getActivity().finish();
                        break;
                    default:
                        Log.d(TAG, "videoStatusIndicator - onKey - default");
                }
                return false;
            }
        });

        // disable focus on play/pause & timeBar to avoid navigating over buttons, all video manipulation can be done by videoStatusIndicator
        pauseBtn.setFocusable(false);
        playBtn.setFocusable(false);
        timeBar.setFocusable(false);
    }

    private void initializePlayer(String url) {
        // customize load control
        LoadControl loadControl = new DefaultLoadControl.Builder()
                .setAllocator(new DefaultAllocator(true, 16))
                .setBufferDurationsMs(MIN_BUFFER_DURATION,
                        MAX_BUFFER_DURATION,
                        MIN_PLAYBACK_START_BUFFER,
                        MIN_PLAYBACK_RESUME_BUFFER)
                .setTargetBufferBytes(-1)
                .setPrioritizeTimeOverSizeThresholds(true).createDefaultLoadControl();

        TrackSelector trackSelector = new DefaultTrackSelector(getContext());

        RenderersFactory renderersFactory = new DefaultRenderersFactory(getContext()).setEnableAudioTrackPlaybackParams(true);

        exoPlayer = new ExoPlayer.Builder(getContext())
                .setLoadControl(loadControl)
                .setTrackSelector(trackSelector)
                .setRenderersFactory(renderersFactory)
                .build();
        // Bind the player to the view.
        playerView.setPlayer(exoPlayer);
        // resize and rescale the video to fit the screen
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        exoPlayer.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
        // Build the media item.
        MediaItem mediaItem = MediaItem.fromUri(url);
        // Set the media item to be played.
        exoPlayer.setMediaItem(mediaItem);
        // Set up listener for loading bar
        exoPlayer.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int playbackState) {
                switch (playbackState) {

                    case Player.STATE_BUFFERING:
                        loadingBar.setVisibility(View.VISIBLE);
                        break;
                    case Player.STATE_READY:
                        loadingBar.setVisibility(View.GONE);
                        break;
                    case Player.STATE_ENDED:
                        backBtn.requestFocus();
                    default:
                        Log.d(TAG, "default");
                        break;
                }
            }
        });
        // set focus every time controller is hidden and shown
        playerView.setControllerVisibilityListener(new PlayerControlView.VisibilityListener() {
            @Override
            public void onVisibilityChange(int visibility) {
                videoStatusIndicator.requestFocus();
            }
        });
        // Prepare the player.
        exoPlayer.prepare();
        // Whether playback should proceed when ready
        exoPlayer.setPlayWhenReady(true);
        // Start the playback.
        exoPlayer.play();
    }

    private void animateVideoIndicator(int action) {
        videoStatusIndicator.setAlpha(255);
        videoStatusIndicator.setBackground(getResources().getDrawable(R.drawable.shape_playback_video_indicator_bg));
        switch (action) {
            case VIDEO_ACTION_PAUSE:
                playWhenReady = false;
                videoStatusIndicator.setBackgroundResource(R.drawable.ic_playback_play_large);
                exoPlayer.pause();
                break;
            case VIDEO_ACTION_PLAY:
                playWhenReady = true;
                videoStatusIndicator.setBackgroundResource(R.drawable.ic_playback_pause_large);
                exoPlayer.play();
                break;
            case VIDEO_ACTION_FORWARD:
                videoStatusIndicator.setBackgroundResource(R.drawable.ic_playback_fast_forward_large);
                exoPlayer.seekTo(exoPlayer.getCurrentPosition() + VIDEO_TIME_DELTA);
                break;
            case VIDEO_ACTION_REWIND:
                videoStatusIndicator.setBackgroundResource(R.drawable.ic_playback_fast_rewind_large);
                exoPlayer.seekTo(exoPlayer.getCurrentPosition() - VIDEO_TIME_DELTA);
                break;
            default:
                Log.d(TAG, "animateVideoIndicator - default");
        }
        Animation animation = new AlphaAnimation(1.0f, 0.0f);
        animation.setDuration(1000);
        animation.setFillAfter(true);
        videoStatusIndicator.startAnimation(animation);
    }

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

    @Override
    public void onResume() {
        super.onResume();
        if (exoPlayer == null) {
            initializePlayer(movie.getVideoUrl());
        } else {
//            exoPlayer.prepare();
            exoPlayer.play();
        }

        // focused by default on the center of the screen
        videoStatusIndicator.requestFocus();

        // set selected x-ray item focused after back from x-ray content activity
        if (recyclerView != null & adapter != null) {
            int id = adapter.getSelectedXRayItemId();
            View xRayItem = recyclerView.getChildAt(id);
            if (xRayItem != null) {
                xRayItem.requestFocus();
            }
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