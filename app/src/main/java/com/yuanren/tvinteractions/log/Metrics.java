package com.yuanren.tvinteractions.log;

import android.app.Application;

import androidx.annotation.NonNull;

import com.yuanren.tvinteractions.model.Movie;
import com.yuanren.tvinteractions.model.MovieList;
import com.yuanren.tvinteractions.view.movies.RowsOfMoviesFragment;

import java.util.ArrayList;
import java.util.List;

public class Metrics extends Application {
    public final static int SESSION_3_NUM_BLOCK = 3;
    public final static int SESSION_3_NUM_TASK = 10;
    public int pid = 0;
    public String method = "";
    public int session = 0;
    public int dataSet = 0;
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

    // session 3
    public double characterPerSecond = 0;
    public int backspaceCount = 0;
    public Long timePerCharacter = 0L;
    public int totalCharacterEntered = 0;

    public int incorrectTitleCount = 0;

    public List<Action> actions = new ArrayList<>();

    private String[] session1_targetMovies = {
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

    private String[] session1_targetMovies2 = {
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

    private String[] session2_targetMovies = {
            "The King's Man",
            "Jumanji",
            "The Devil Wears Prada",
            "Venom",
            "Harry Potter and the Prisoner of Azkaban",
            "Insomnia",
            "Mama Mia",
            "Sherlock Holmes"};

    private String[] session2_targetMovies2 = {
            "Red Notice",
            "Uncharted",
            "The Wolf of Wall Street",
            "Iron man",
            "Fantastic Beasts and Where to Find Them",
            "Fall",
            "Lala Land",
            "The Da Vinci Code"};

    private String[] session3_targetMovies = {
            "The King's Man",
            "Jumanji",
            "The Devil Wears Prada",
            "Venom",
            "Harry Potter and the Prisoner of Azkaban",
            "Insomnia",
            "Mama Mia",
            "Sherlock Holmes",
            "Flipped",
            "Inception"};

    @NonNull
    @Override
    public String toString() {
        String res = "";
        movieLength = MovieList.getMovie(targetMovie).getLength();

        if (session == 1 || session == 2) {
            res = "" + pid + "," + method + "," + session + "," + dataSet + "," + block + "," + targetMovie + "," + movieLength + "," + selectedMovie + "," + task + "," + taskCompletionTime + "," + startTime + "," + endTime + "," + actionsPerTask + "," + actionsNeeded + "," + errorRate + "\n";
        }  else {
            if (block == 1) {
                dataSet = 50;
            } else if (block == 2) {
                dataSet = 100;
            } else {
                dataSet = 250;
            }
            characterPerSecond = (double) totalCharacterEntered / (taskCompletionTime / 1000);
            timePerCharacter = taskCompletionTime / totalCharacterEntered;
            errorRate = incorrectTitleCount != 0 ? 1 / incorrectTitleCount : 0;
            res = "" + pid + "," + method + "," + session + "," + dataSet + "," + block + "," + targetMovie + "," + movieLength + "," + selectedMovie + "," + task + "," + taskCompletionTime + "," + startTime + "," + endTime + "," + characterPerSecond + "," + backspaceCount + "," + timePerCharacter + "," + totalCharacterEntered + "," + errorRate + "\n";
        }
        return res;
    }

    public String getFirstTargetMovie() {
        if (session == 1) {
            return dataSet == 0 ? session1_targetMovies[0] : session1_targetMovies2[0];
        } else if (session == 2) {
            return dataSet == 0 ? session2_targetMovies[0] : session2_targetMovies2[0];
        } else {
            return session3_targetMovies[0];
        }
    }

    public int calculateSession1ActionsNeeded() {
        targetMovie = dataSet == 0 ? session1_targetMovies[block - 1] : session1_targetMovies2[block - 1];
        Movie movie = MovieList.getMovie(targetMovie);
        if (task == TaskType.TYPE_TASK_FIND.name()) {
            if (block <= 1) {
                actionsNeeded = movie.getCategoryIndex() + movie.getPosition() + 1; // vertical navigation + horizontal navigation + click
            } else {
                Movie prevMovie = MovieList.getMovie(dataSet == 0 ? session1_targetMovies[block - 2] : session1_targetMovies2[block - 2]);
                int prevActionsNeeded = prevMovie.getCategoryIndex() + prevMovie.getPosition();
                int curActionsNeeded = movie.getCategoryIndex() + movie.getPosition();
                actionsNeeded = Math.abs(curActionsNeeded - prevActionsNeeded) + 1;
            }
        }
        return actionsNeeded;
    }

    public void nextBlock() {
        if (session == 1) { // 1
            block = block > session1_targetMovies.length ? block : block + 1;
            targetMovie = dataSet == 0 ? session1_targetMovies[block - 1] : session1_targetMovies2[block - 1];
        } else if (session == 2) { // 2
            block = block > session2_targetMovies.length ? block : block + 1;
            targetMovie = dataSet == 0 ? session2_targetMovies[block - 1] : session2_targetMovies2[block - 1];
        } else { // 3
            block = block + 1 > 3 ? block : block + 1;
            targetMovie = session3_targetMovies[0];
        }

        movieLength = MovieList.getMovie(targetMovie).getLength();
        selectedMovie = "";
        task = "";
        taskCompletionTime = 0L;
        startTime = 0L;
        endTime = 0L;
        actionsPerTask = 0;
        actionsNeeded = 0;
        errorRate = 0;
    }

    public void nextTask() {
        if (session == 3) {
            int i = Integer.parseInt(task);
            task = String.valueOf(i + 1 > 10 ? i : i + 1);
            targetMovie = session3_targetMovies[Integer.parseInt(task) - 1];
            selectedMovie = "";
            taskCompletionTime = 0L;
            startTime = 0L;
            endTime = 0L;
            characterPerSecond = 0;
            backspaceCount = 0;
            timePerCharacter = 0L;
            totalCharacterEntered = 0;
            incorrectTitleCount = 0;
        }
    }
}
