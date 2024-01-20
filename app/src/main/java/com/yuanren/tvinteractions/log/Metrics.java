package com.yuanren.tvinteractions.log;

import android.app.Application;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Metrics extends Application {
    public int pid = 0;
    public String method = "";
    public int session = 0;
    public int block = 0;
    public String movie = "";
    public String task = "";
    public double actionsPerTask = 0;
    public double taskCompletionTime = 0;
    public double actionsNeeded = 0;
    public double startTime = 0;
    public double endTime = 0;

    public List<Action> actions = new ArrayList<>();;

    @NonNull
    @Override
    public String toString() {
        return "" + pid + "," + method + "," + session + "," + block + "," + movie + "," + task + "," + actionsPerTask + "," + taskCompletionTime + "," + actionsNeeded + "," + startTime + "," + endTime + "\n";
    }
}
