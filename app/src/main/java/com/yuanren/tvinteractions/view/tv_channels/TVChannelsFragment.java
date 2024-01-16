package com.yuanren.tvinteractions.view.tv_channels;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import com.yuanren.tvinteractions.R;
import com.yuanren.tvinteractions.base.NavigationMenuCallback;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TVChannelsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TVChannelsFragment extends Fragment {
    private static final String TAG = "TVChannelsFragment";
    private NavigationMenuCallback navigationMenuCallback;
    private TextView tvChannelsTV;
    private int channel = 1;

    public TVChannelsFragment() {
        // Required empty public constructor
    }

    public static TVChannelsFragment newInstance() {
        TVChannelsFragment fragment = new TVChannelsFragment();
        return fragment;
    }

    public void setNavigationMenuCallback(NavigationMenuCallback callback) {
        this.navigationMenuCallback = callback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_channels, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvChannelsTV = view.findViewById(R.id.tv_channels_TV);
        tvChannelsTV.setFocusable(true);
        tvChannelsTV.requestFocus();
        tvChannelsTV.setText(String.valueOf(channel));

        tvChannelsTV.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                // filter out the function call for KEY_DOWN event, only working for KEY_UP event to avoid two-times calling
                if (keyEvent.getAction() != KeyEvent.ACTION_DOWN) {
                    return true;
                }

                switch (i) {
                    case KeyEvent.KEYCODE_DPAD_UP:
                        channel = Math.min(100, channel + 1);
                        break;
                    case KeyEvent.KEYCODE_DPAD_DOWN:
                        channel = Math.max(1, channel - 1);
                        break;
                    case KeyEvent.KEYCODE_DPAD_LEFT:
                        navigationMenuCallback.navMenuToggle(true);
                        break;
                    default:
                        Log.d(TAG, "tvChannelsTV - onKey - default");
                }
                tvChannelsTV.setText(String.valueOf(channel));
                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        // reset channel
        channel = 1;
        tvChannelsTV.setText(String.valueOf(channel));
    }
}