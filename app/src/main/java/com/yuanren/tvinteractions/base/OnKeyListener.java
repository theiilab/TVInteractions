package com.yuanren.tvinteractions.base;

import android.view.KeyEvent;
import android.view.View;

public interface OnKeyListener {
    boolean onItemClick(View v, int keyCode, KeyEvent event, int position);
}
