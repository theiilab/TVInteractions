package com.yuanren.tvinteractions.log;

import android.app.Application;

import androidx.annotation.NonNull;

import com.yuanren.tvinteractions.model.Movie;
import com.yuanren.tvinteractions.model.MovieList;
import com.yuanren.tvinteractions.view.movies.RowsOfMoviesFragment;

import java.util.ArrayList;
import java.util.List;

public class Metrics extends Application {
    public int pid = 0;
    public String method = "";
    public int session = 0;
    public int dataSet = 0;
    public int searchDataSet = 0;
    public int block = 1;
    public String targetMovie = "";
    public int movieLength = 0;
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
        targetMovie = dataSet == 0 ? targetMovies[block - 1] : targetMovies2[block - 1];
        movieLength = MovieList.getMovie(targetMovie).getLength();

        String res;
        if (session == 1) {
            res = "" + pid + "," + method + "," + session + "," + dataSet + "," + block + "," + targetMovie + "," + movieLength + "," + selectedMovie + "," + task + "," + taskCompletionTime + "," + startTime + "," + endTime + "," + actionsPerTask + "," + actionsNeeded + "," + errorRate + "\n";
        } else {
            res = "" + pid + "," + method + "," + session + "," + searchDataSet + "," + targetMovie + "," + movieLength + "," + selectedMovie + "," + task + "," + taskCompletionTime + "," + startTime + "," + endTime + "," + actionsPerTask + "," + actionsNeeded + "," + errorRate + "\n";
        }
        return res;
    }

    public String getFirstTargetMovie() {
        return dataSet == 0 ? targetMovies[0] : targetMovies2[0];
    }

    public int calculateActionsNeeded() {
        targetMovie = dataSet == 0 ? targetMovies[block - 1] : targetMovies2[block - 1];
        Movie movie = MovieList.getMovie(targetMovie);
        if (task == TaskType.TYPE_TASK_FIND.name()) {
            if (block <= 1) {
                actionsNeeded = movie.getCategoryIndex() + movie.getPosition() + 1; // vertical navigation + horizontal navigation + click
            } else {
                Movie prevMovie = MovieList.getMovie(targetMovies[block - 2]);
                int prevActionsNeeded = prevMovie.getCategoryIndex() + prevMovie.getPosition();
                int curActionsNeeded = movie.getCategoryIndex() + movie.getPosition();
                actionsNeeded = Math.abs(curActionsNeeded - prevActionsNeeded) + 1;
            }
        }
        return actionsNeeded;
    }

    public void next() {
        block = block > targetMovies.length ? block : block + 1;
        targetMovie = dataSet == 0 ? targetMovies[block - 1] : targetMovies2[block - 1];
        selectedMovie = "";
        task = "";
        taskCompletionTime = 0L;
        startTime = 0L;
        endTime = 0L;
        actionsPerTask = 0;
        actionsNeeded = 0;
        errorRate = 0;
    }
}
