package com.yuanren.tvinteractions.log;

import android.app.Application;
import android.view.KeyEvent;

import androidx.annotation.NonNull;

public class Action {
    public int pid = 0;
    public String method = "";
    private int session = 0;
    private int block = 0;
    private String name;
    private String scope; // performed in which activity
    private double startTime;
    private double endTime;
    private double duration;
    private String other = ""; // tap (the movie name selected, x-ray item selected, etc), swipe (directions)

    public Action(int pid, String method, int session, int block, String name, String scope, double startTime, double endTime) {
        this.pid = pid;
        this.method = method;
        this.session = session;
        this.block = block;
        this.name = name;
        this.scope = scope;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = endTime - startTime;
    }

    public Action(int pid, String method, int session, int block, String name, String scope, double startTime, double endTime, String other) {
        this.pid = pid;
        this.method = method;
        this.session = session;
        this.block = block;
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
        return "" + pid + "," + method + "," + session + "," + block + name + "," + scope + "," + startTime + "," + endTime + "," + duration + "," + other;
    }
}
