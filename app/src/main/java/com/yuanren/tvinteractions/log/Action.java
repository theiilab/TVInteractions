package com.yuanren.tvinteractions.log;

import android.app.Application;
import android.view.KeyEvent;

import androidx.annotation.NonNull;

public class Action {
    private String name;
    private String scope; // performed in which activity
    private double startTime;
    private double endTime;
    private double duration;
    private String other = ""; // tap (the movie name selected, x-ran item selected, etc), swipe (directions)

    public Action(String name, String scope, double startTime, double endTime) {
        this.name = name;
        this.scope = scope;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = endTime - startTime;
    }

    public Action(String name, String scope, double startTime, double endTime, String other) {
        this.name = name;
        this.scope = scope;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = endTime - startTime;
        this.other = other;
    }

    @NonNull
    @Override
    public String toString() {
        return name + "," + scope + "," + String.valueOf(startTime) + "," + String.valueOf(endTime) + "," + String.valueOf(duration) + "," + String.valueOf(other);
    }
}
