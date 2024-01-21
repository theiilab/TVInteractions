package com.yuanren.tvinteractions.log;

import android.app.Application;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Metrics extends Application {
    public int pid = 0;
    public String method = "";
    public int session = 0;
    public int block = 1;
    public String targetMovie = "";
    public String selectedMovie = "";
    public String task = "";
    public Long taskCompletionTime = 0L;
    public Long startTime = 0L;
    public Long endTime = 0L;
    public int actionsPerTask = 0;
    public int actionsNeeded = 0;
    public double errorRate = 0;

    public List<Action> actions = new ArrayList<>();

    private String[] targetMovies = {
            "The King's Man",
            "Jumanji",
            "The Devil Wears Prada",
            "Venom",
            "Harry Potter and the Prisoner of Azkaban",
            "Insomnia",
            "Mama Mia",
            "Sherlock Holmes",
            "Flipped",
            "Inception",
            "Space Jam",
            "Death on the Nile"};

    private String[] targetMovies2 = {
            "Red Notice",
            "Uncharted",
            "The Wolf of Wall Street",
            "Iron man",
            "Fantastic Beasts and Where to Find Them",
            "Fall",
            "Lala Land",
            "The Da Vinci Code",
            "Crazy Rich Asians",
            "The Adam Project",
            "Million Dollar Baby",
            "Pain Hustler"};


    @NonNull
    @Override
    public String toString() {
        targetMovie = targetMovies[block - 1];
        return "" + pid + "," + method + "," + session + "," + block + "," + targetMovie + "," + selectedMovie + "," + task + "," + taskCompletionTime + "," + startTime + "," + endTime + "," + actionsPerTask + "," + actionsNeeded + "," + errorRate + "\n";
    }

    public void next() {
        selectedMovie = "";
        task = "";
        actionsPerTask = 0;
        taskCompletionTime = 0L;
        actionsNeeded = 0;
        startTime = 0L;
        endTime = 0L;
        block++;
    }
}
