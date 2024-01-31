package com.yuanren.tvinteractions.base;

import android.view.KeyEvent;
import android.view.View;

import com.yuanren.tvinteractions.model.Movie;

public interface OnKeyListener {
    boolean onItemClick(View v, int keyCode, KeyEvent event, int position);
    boolean onItemClick(View v, int keyCode, KeyEvent event, int position, Movie movie);
}
