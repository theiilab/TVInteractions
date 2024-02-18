package com.yuanren.tvinteractions.log;

import android.util.Log;

import androidx.annotation.NonNull;

import com.yuanren.tvinteractions.model.Movie;
import com.yuanren.tvinteractions.model.MovieList;

public class Task {
    private final static String TAG = "Task";
    // common
    public int pid;
    public int sid;
    public String method;
    public int dataSet;
    public int bid;

    public int id;
    public String name;
    public String targetMovie;
    public int movieLength;
    public String selectedMovie;
    public Long startTime = 0L;
    public Long endTime = 0L;
    public Long taskCompletionTime = 0L;
    public int actionsPerTask = 0;
    public int actionUpsPerTask = 0;
    public int actionsNeeded = 0;
    public double errorRate = 0;

    // session 3
    public int positionOnSelect = 0;
    public double characterPerSecond = 0;
    public int backspaceCount = 0;
    public Long timePerCharacter = 0L;
    public int totalCharacterEntered = 0;
    public int incorrectTitleCount = 0;
    public String textEntered = "";
    public String inputStream = "";

    public Task(int pid, int sid, String method, int dataSet, int bid, int tid, String name, String targetMovie, int movieLength) {
        this.pid = pid;
        this.sid = sid;
        this.method = method;
        this.dataSet = dataSet;
        this.bid = bid;
        this.id = tid;
        this.name = name;
        this.targetMovie = targetMovie;
        this.movieLength = movieLength;
    }

    @NonNull
    @Override
    public String toString() {
        String res;
        if (sid == 1) {
            taskCompletionTime = endTime - startTime;
            /* leave action needed in the activity to handle (run-time data needed) */
            errorRate = actionsNeeded != 0 ? ((double) actionsPerTask - (double) actionsNeeded) / actionsNeeded : 0;
            res = "" + pid + "," + method + "," + sid + "," + dataSet + "," + bid + "," + targetMovie + "," + movieLength + "," + selectedMovie + "," + id + "," + name + "," + taskCompletionTime + "," + startTime + "," + endTime + "," + actionsPerTask + "," + actionsNeeded + "," + errorRate + "," + actionUpsPerTask + "\n";
        } else if (sid == 2) {
            taskCompletionTime = endTime - startTime;
            errorRate = actionsNeeded != 0 ? ((double) actionsPerTask - (double) actionsNeeded) / actionsNeeded : 0;
            res = "" + pid + "," + method + "," + sid + "," + dataSet + "," + bid + "," + targetMovie + "," + movieLength + "," + selectedMovie + "," + id + "," + name + "," + taskCompletionTime + "," + startTime + "," + endTime + "," + actionsPerTask + "," + actionsNeeded + "," + errorRate + "," + 0 + "\n";
        } else { // session 3
            taskCompletionTime = endTime - startTime;
            characterPerSecond = (double) totalCharacterEntered / (taskCompletionTime / 1000);
            timePerCharacter = totalCharacterEntered != 0 ? taskCompletionTime / totalCharacterEntered : 0;
            errorRate = incorrectTitleCount != 0 ? 1.0 / incorrectTitleCount : 0;
            res = "" + pid + "," + method + "," + sid + "," + dataSet + "," + bid + "," + targetMovie + "," + movieLength + "," + selectedMovie + "," + id + "," + name + "," + taskCompletionTime + "," + startTime + "," + endTime + "," + actionsPerTask + "," + errorRate + "," + positionOnSelect + "," + characterPerSecond + "," + backspaceCount + "," + timePerCharacter + "," + totalCharacterEntered + "," + textEntered + "," + inputStream + "\n";
        }
        return res;
    }
}
