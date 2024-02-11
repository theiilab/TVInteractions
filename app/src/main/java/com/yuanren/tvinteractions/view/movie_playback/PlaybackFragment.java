package com.yuanren.tvinteractions.view.movie_playback;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import androidx.constraintlayout.widget.ConstraintLayout;
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
import com.yuanren.tvinteractions.log.Action;
import com.yuanren.tvinteractions.log.ActionType;
import com.yuanren.tvinteractions.log.Metrics;
import com.yuanren.tvinteractions.log.TaskType;
import com.yuanren.tvinteractions.model.Movie;
import com.yuanren.tvinteractions.model.MovieList;
import com.yuanren.tvinteractions.utils.FileUtils;
import com.yuanren.tvinteractions.view.base.SpaceItemDecoration;
import com.yuanren.tvinteractions.view.x_ray.XRayCardListAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PlaybackFragment extends Fragment {
    private static final String TAG = "PlaybackFragment";
    private static final int VIDEO_ACTION_PLAY = 0;
    private static final int VIDEO_ACTION_PAUSE = 1;
    private static final int VIDEO_ACTION_FORWARD = 2;
    private static final int VIDEO_ACTION_REWIND = 3;
    private static final int VIDEO_TIME_DELTA = 5000; // 5s

    //Minimum Video you want to buffer while Playing (ms)
    private static final int MIN_BUFFER_DURATION = 6000;
    //Max Video you want to buffer during PlayBack
    private static final int MAX_BUFFER_DURATION = 9000;
    //Min Video you want to buffer before start Playing it
    private static final int MIN_PLAYBACK_START_BUFFER = 6000;
    //Min video You want to buffer when user resumes video
    private static final int MIN_PLAYBACK_RESUME_BUFFER = 0;

    private PlayerView playerView;
    private TextView title;
    private ImageButton backBtn;
    private ImageButton videoStatusIndicator;
    private ConstraintLayout xRayLayout;
    private ImageButton playBtn;
    private ImageButton pauseBtn;
    private ExoPlayer exoPlayer;
    private DefaultTimeBar timeBar;
    private RecyclerView recyclerView;
    private ProgressBar loadingBar;

    private XRayCardListAdapter adapter;
    private boolean playWhenReady = true;
    private Movie movie;

    /** ----- log ----- */
    private Context context;
    private Metrics metrics;
    private TextView taskReminder;
    private Handler timeHandler = new Handler(Looper.getMainLooper());
    private int actionCount = 0;
    private int actionUpCount = 0;
    private Long playStartTime = 0L;
    private Long playEndTime = 0L;
    private boolean playFlag = false;

    private Long changeVolumeStartTime = 0L;
    private Long changeVolumeEndTime = 0L;
    private int changeVolumeSemaphore = 0;
    private boolean changeVolumeFlag = false;

    private Long forwardStartTime = 0L;
    private Long forwardEndTime = 0L;
    private int forwardSemaphore = 0;
    private boolean forwardFlag = false;

    private Long pauseStartTime = 0L;
    private Long pauseEndTime = 0L;
    private int pauseSemaphore = 0;
    private boolean pauseFlag = false;

    private Long backwardStartTime = 0L;
    private Long backwardEndTime = 0L;
    private int backwardSemaphore = 0;
    private boolean backwardFlag= false;
    private boolean forwardDoneFlag = false;
    private boolean backwardDoneFlag = false;

    private Long goToEndStartTime = 0L;
    private Long goToEndEndTime = 0L;
    private Long goToEndCurTimeIndex = 0L;
    private int goToEndSemaphore = 0;
    private boolean goToEndFlag = false;

    private Long goToStartStartTime = 0L;
    private Long goToStartEndTime = 0L;
    private int goToStartSemaphore = 0;
    private boolean goToStartFlag = false;
    private boolean blockDoneFlag = false;

    private Long actionStartTime = 0L;

    private Map<TaskType, Integer> actionsNeeded = new HashMap<TaskType, Integer>() {{
        put(TaskType.TYPE_TASK_PLAY_5_SEC, 0);
        put(TaskType.TYPE_TASK_CHANGE_VOLUME, 2);
        put(TaskType.TYPE_TASK_FORWARD, 2);
        put(TaskType.TYPE_TASK_PAUSE, 2);
        put(TaskType.TYPE_TASK_BACKWARD, 2);
    }};
    /** --------------- */

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
        movie = MovieList.getMovie(view.getContext().getApplicationContext(), (int)getArguments().getLong(PlaybackActivity.SELECTED_MOVIE_ID));

        /** ----- log ----- */
        context = view.getContext().getApplicationContext();
        metrics = (Metrics) view.getContext().getApplicationContext();
        actionsNeeded.put(TaskType.TYPE_TASK_GO_TO_END, movie.getLength() / (VIDEO_TIME_DELTA / 1000) + 1);
        actionsNeeded.put(TaskType.TYPE_TASK_GO_TO_START, movie.getLength() / (VIDEO_TIME_DELTA / 1000) + 1);
        /** --------------- */


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
        xRayLayout = view.findViewById(R.id.x_ray);
        playBtn = view.findViewById(R.id.exo_play);
        pauseBtn = view.findViewById(R.id.exo_pause);
        timeBar = view.findViewById(R.id.exo_progress);
        loadingBar = view.findViewById(R.id.loading_bar);
        taskReminder = view.findViewById(R.id.task_reminder);

        title.setText(movie.getTitle());
        xRayLayout.setVisibility(View.GONE);
        initializePlayer(movie.getVideoUrl());

        /** ----- log ----- */
        taskReminder.setText("1. Play for 5 seconds");
        playStartTime = System.currentTimeMillis();  // play task includes the pre-loading time for the video clip
        /** --------------- */

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
                Log.d(TAG, "Key action: " + i);
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    /** ----- log ----- */
                    actionStartTime = System.currentTimeMillis();
                    /** --------------- */

                    switch (i) {
                        case KeyEvent.KEYCODE_DPAD_RIGHT:
                            Log.d(TAG, "forward");
                            animateVideoIndicator(VIDEO_ACTION_FORWARD);

                            /** ----- log ----- */
                            if (!forwardDoneFlag) {
                                if (changeVolumeFlag && metrics.taskNum == 3) {
                                    // completion of task 3
                                    changeVolumeEndTime = System.currentTimeMillis();
                                    setLogData(TaskType.TYPE_TASK_CHANGE_VOLUME, changeVolumeStartTime, changeVolumeEndTime);
                                    clearTaskData();

                                    forwardStartTime = System.currentTimeMillis();
                                }
                                actionCount++;
                                forwardSemaphore++;
                                if (forwardSemaphore == actionsNeeded.get(TaskType.TYPE_TASK_FORWARD) && metrics.taskNum == 4) {
                                    forwardFlag = true;
                                    forwardDoneFlag = true;
                                    taskReminder.setText("4. Pause/Play");
                                }
                            } else {
                                if (backwardFlag && metrics.taskNum == 6) {
                                    // completion of task 6
                                    backwardEndTime = System.currentTimeMillis();
                                    setLogData(TaskType.TYPE_TASK_BACKWARD, backwardStartTime, backwardEndTime);
                                    clearTaskData();

                                    // on task 7
                                    goToEndStartTime = System.currentTimeMillis();
                                    goToEndCurTimeIndex = exoPlayer.getCurrentPosition() / 1000;
                                }
                                actionCount++;
                                goToEndSemaphore++;

                                if (goToEndSemaphore == actionsNeeded.get(TaskType.TYPE_TASK_GO_TO_END) && metrics.taskNum == 7) {
                                    goToEndFlag = true;
                                    taskReminder.setText("7. Go to the start");
                                }
                            }
                            /** --------------- */
                            break;
                        case KeyEvent.KEYCODE_ENTER:
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                            Log.d(TAG, "pause");
                            animateVideoIndicator(playWhenReady ? VIDEO_ACTION_PAUSE : VIDEO_ACTION_PLAY);
                            /** ----- log ----- */
                            if (forwardFlag && metrics.taskNum == 4) {
                                // completion of task 4
                                forwardEndTime = System.currentTimeMillis();
                                setLogData(TaskType.TYPE_TASK_FORWARD, forwardStartTime, forwardEndTime);
                                clearTaskData();

                                // on task 5
                                pauseStartTime = System.currentTimeMillis();
                            }
                            actionCount++;
                            pauseSemaphore++;

                            if (pauseSemaphore == actionsNeeded.get(TaskType.TYPE_TASK_PAUSE) && metrics.taskNum == 5) {
                                pauseFlag = true;
                                taskReminder.setText("5. Backward by 10 seconds");
                            }
                            /** --------------- */
                            break;
                        case KeyEvent.KEYCODE_DPAD_LEFT:
                            Log.d(TAG, "rewind");
                            animateVideoIndicator(VIDEO_ACTION_REWIND);

                            /** ----- log ----- */
                            if (!backwardDoneFlag) {
                                if (pauseFlag && metrics.taskNum == 5) {
                                    // completion of task 5
                                    pauseEndTime = System.currentTimeMillis();
                                    setLogData(TaskType.TYPE_TASK_PAUSE, pauseStartTime, pauseEndTime);
                                    clearTaskData();

                                    // on task 5
                                    backwardStartTime = System.currentTimeMillis();
                                }
                                actionCount++;
                                backwardSemaphore++;

                                if (backwardSemaphore == actionsNeeded.get(TaskType.TYPE_TASK_BACKWARD) && metrics.taskNum == 6) {
                                    backwardFlag = true;
                                    backwardDoneFlag = true;
                                    taskReminder.setText("6. Go to the end");
                                }
                            } else {
                                if (goToEndFlag && metrics.taskNum == 7) {
                                    // completion of task 7
                                    goToEndEndTime = System.currentTimeMillis();
                                    actionsNeeded.put(TaskType.TYPE_TASK_GO_TO_END, (int) (movie.getLength() - goToEndCurTimeIndex) / (VIDEO_TIME_DELTA / 1000) + 1);
                                    setLogData(TaskType.TYPE_TASK_GO_TO_END, goToEndStartTime, goToEndEndTime);
                                    clearTaskData();

                                    // on task 8
                                    goToStartStartTime = System.currentTimeMillis();
                                }
                                actionCount++;
                                goToStartSemaphore++;
                                goToStartEndTime = System.currentTimeMillis();

                                if (goToStartSemaphore == actionsNeeded.get(TaskType.TYPE_TASK_GO_TO_START) && metrics.taskNum == 8) {
                                    blockDoneFlag = true;
                                    taskReminder.setText("8. Back");
                                }
                            }
                            break;
                        case KeyEvent.KEYCODE_DPAD_DOWN:
                            // always focus on the first x-ray item
//                            recyclerView.getChildAt(0).requestFocus();
                            actionCount++;
                            break;
                        case KeyEvent.KEYCODE_BACK:
                            /** ----- log ----- */// prevent user accidentally exit before completing all tasks
                            if (!blockDoneFlag) { // on last task #: 8, 7 actions in this page (1 navi in main page)
                                actionCount++;
                                return true;
                            }

                            // completion of task 8
                            setLogData(TaskType.TYPE_TASK_GO_TO_START, goToStartStartTime, goToStartEndTime);

                            clearLogData();
                            /** --------------- */

                            getActivity().finish();
                            break;
                        default:
                            Log.d(TAG, "videoStatusIndicator - onKey - default");
                    }
                } else if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    /** ----- raw log ----- */
                    actionUpCount++;

                    Action action = null;
                    switch (i) {
                        case KeyEvent.KEYCODE_DPAD_LEFT:
                            action = new Action(metrics, movie.getTitle(),
                                    ActionType.TYPE_ACTION_LEFT.name, TAG, actionStartTime, System.currentTimeMillis());
                            break;
                        case KeyEvent.KEYCODE_DPAD_RIGHT:
                            action = new Action(metrics, movie.getTitle(),
                                    ActionType.TYPE_ACTION_RIGHT.name, TAG, actionStartTime, System.currentTimeMillis());
                            break;
                        case KeyEvent.KEYCODE_DPAD_UP:
                            action = new Action(metrics, movie.getTitle(),
                                    ActionType.TYPE_ACTION_UP.name, TAG, actionStartTime, System.currentTimeMillis());
                            break;
                        case KeyEvent.KEYCODE_DPAD_DOWN:
                            action = new Action(metrics, movie.getTitle(),
                                    ActionType.TYPE_ACTION_DOWN.name, TAG, actionStartTime, System.currentTimeMillis());
                            break;
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            action = new Action(metrics, movie.getTitle(),
                                    ActionType.TYPE_ACTION_ENTER.name, TAG, actionStartTime, System.currentTimeMillis());
                            break;
                        case KeyEvent.KEYCODE_BACK:
                            action = new Action(metrics, movie.getTitle(),
                                    ActionType.TYPE_ACTION_BACK.name, TAG, actionStartTime, System.currentTimeMillis());
                            break;
                    }
                    FileUtils.writeRaw(getContext(), action);
                    return true;
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

        exoPlayer = new ExoPlayer.Builder(getActivity().getApplicationContext())
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

                        /** ----- log ----- */
                        if (!playFlag) {
                            timeHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    playFlag = true;
                                    actionUpCount = Math.max(0, actionUpCount - 1); // weird bug, always has 1 hit when enter this page

                                    taskReminder.setText("2. Change volume by 2 units");
                                }
                            }, 5000);
                        }
                        /** --------------- */
                        break;
                    case Player.STATE_ENDED:
                        backBtn.requestFocus();
                    default:
                        Log.d(TAG, "default");
                        break;
                }
            }

            /** ----- log ----- */
            @Override
            public void onDeviceVolumeChanged(int volume, boolean muted) {
                Player.Listener.super.onDeviceVolumeChanged(volume, muted);

                Log.d(TAG, "device volume changed: " + volume);
                if (playFlag && metrics.taskNum == 2) {
                    // completion of task 1
                    playEndTime = System.currentTimeMillis();
                    setLogData(TaskType.TYPE_TASK_PLAY_5_SEC, playStartTime, playEndTime);
                    clearTaskData();

                    // on task 2
                    changeVolumeStartTime = System.currentTimeMillis();
                }
                actionCount++;
                actionUpCount++;
                changeVolumeSemaphore++;
                if (changeVolumeSemaphore == actionsNeeded.get(TaskType.TYPE_TASK_CHANGE_VOLUME) && metrics.taskNum == 3) {
                    changeVolumeFlag = true;
                    taskReminder.setText("3. Forward by 10 seconds");
                }

                // raw
                Action action = new Action(metrics, movie.getTitle(),
                        ActionType.TYPE_ACTION_VOLUME.name, TAG, System.currentTimeMillis(), System.currentTimeMillis());
                FileUtils.writeRaw(getContext(), action);
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

    /** ----- log ----- */
    private void setLogData(TaskType task, Long startTime, Long endTime) {
        metrics.actionsPerTask = actionCount;
        metrics.actionsNeeded = actionsNeeded.get(task);
        metrics.startTime = startTime;
        metrics.endTime = endTime;
        metrics.actionUpPerTask = actionUpCount;

        FileUtils.write(context, metrics);
        metrics.nextTask();
    }

    private void clearTaskData() {
//        playFlag = false;
//        changeVolumeFlag = false;
//        forwardFlag = false;
//        pauseFlag = false;
//        backwardFlag = false;
//        goToEndFlag = false;
//        goToStartFlag = false;

        actionCount = 0;
        actionUpCount = 0;
        changeVolumeSemaphore = 0;
        forwardSemaphore = 0;
        pauseSemaphore = 0;
        backwardSemaphore = 0;
        goToEndSemaphore = 0;
        goToStartSemaphore = 0;
    }

    /** ----- log ----- */
    private void clearLogData() {
        playFlag = false;
        changeVolumeFlag = false;
        forwardFlag = false;
        pauseFlag = false;
        backwardFlag = false;
        goToEndFlag = false;
        goToStartFlag = false;
        forwardDoneFlag = false;
        backwardDoneFlag = false;
        blockDoneFlag = false;

        actionCount = 0;
        actionUpCount = 0;

        playStartTime = 0L;
        playEndTime = 0L;

        changeVolumeStartTime = 0L;
        changeVolumeEndTime = 0L;
        changeVolumeSemaphore = 0;

        forwardStartTime = 0L;
        forwardEndTime = 0L;
        forwardSemaphore = 0;

        pauseStartTime = 0L;
        pauseEndTime = 0L;
        pauseSemaphore = 0;

        backwardStartTime = 0L;
        backwardEndTime = 0L;
        backwardSemaphore = 0;

        goToEndStartTime = 0L;
        goToEndEndTime = 0L;
        goToEndCurTimeIndex = 0L;
        goToEndSemaphore = 0;

        goToStartStartTime = 0L;
        goToStartEndTime = 0L;
        goToStartSemaphore = 0;
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

        /** ----- log ----- */
        clearLogData();
    }
}