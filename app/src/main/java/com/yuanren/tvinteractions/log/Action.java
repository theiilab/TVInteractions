package com.yuanren.tvinteractions.log;

import android.app.Application;
import android.database.MergeCursor;
import android.view.KeyEvent;

import androidx.annotation.NonNull;

public class Action {
    public int pid = 0;
    public String method = "";
    public int session = 0;
    private int block = 0;
    public String targetMovie = "";
    public String selectedMovie = "";
    private int taskNum = 0;
    private String name;
    private String scope; // performed in which activity
    private double startTime;
    private double endTime;
    private double duration;
    private String other = ""; // tap (the movie name selected, x-ray item selected, etc), swipe (directions)

    public Action(Metrics metrics, String selectedMovie, String name, String scope, double startTime, double endTime) {
        this.pid = metrics.pid;
        this.method = metrics.method;
        this.session = metrics.session;
        this.block = metrics.block;
        this.targetMovie = metrics.targetMovie;
        this.selectedMovie = selectedMovie;
        this.taskNum = metrics.taskNum;
        this.name = name;
        this.scope = scope;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = endTime - startTime;
    }

    public Action(Metrics metrics, String selectedMovie, String name, String scope, double startTime, double endTime, String other) {
        this.pid = metrics.pid;
        this.method = metrics.method;
        this.session = metrics.session;
        this.block = metrics.block;
        this.targetMovie = metrics.targetMovie;
        this.selectedMovie = selectedMovie;
        this.taskNum = metrics.taskNum;
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
        return "" + pid + "," + method + "," + session + "," + block + "," + targetMovie + "," + selectedMovie + "," + taskNum + "," + name + "," + scope + "," + startTime + "," + endTime + "," + duration + "," + other + "\n";
    }
}
