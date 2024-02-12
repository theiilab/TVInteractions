package com.yuanren.tvinteractions.log;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.yuanren.tvinteractions.model.Movie;
import com.yuanren.tvinteractions.model.MovieList;

import java.util.ArrayList;
import java.util.List;

public class Block {
    private final static String TAG = "Block";
    public final static int SESSION_1_NUM_TASK = 8;
    public final static int SESSION_2_NUM_TASK = 7;
    public final static int SESSION_3_NUM_TASK = 10;
    private Context context;

    public int pid;
    public int sid;
    public String method;
    public int dataSet;

    public int id;
    public String targetMovie = "";
    public int movieLength = 0;
    public String selectedMovie = "";
    public Long startTime = 0L;
    public Long endTime = 0L;
    public Long blockCompletionTime = 0L;
    public int actionsPerBlock = 0;
    public int actionUpsPerBlock = 0;
    public int actionsNeeded = 0;
    public double errorRate = 0;
    public int index = 0; // zero-based
    public int incorrectTitleCount = 0;

    private int[] randoms;
    public List<Task> tasks = new ArrayList<>();

    private String[] session1_tasks = {
            TaskType.TYPE_TASK_FIND.name,
            TaskType.TYPE_TASK_PLAY_5_SEC.name,
            TaskType.TYPE_TASK_CHANGE_VOLUME.name,
            TaskType.TYPE_TASK_FORWARD.name,
            TaskType.TYPE_TASK_PAUSE.name,
            TaskType.TYPE_TASK_BACKWARD.name,
            TaskType.TYPE_TASK_GO_TO_END.name,
            TaskType.TYPE_TASK_GO_TO_START.name
    };
    private String[] session3_tasks1 = {
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

    private String[] session3_tasks2 = {
            "Red Notice",
            "Uncharted",
            "The Wolf of Wall Street",
            "Iron man",
            "Fantastic Beasts and Where to Find Them",
            "Fall",
            "Lala Land",
            "The Da Vinci Code",
            "Crazy Rich Asians",
            "The Adam Project"};

    public Block(Context context, int pid, int sid, String method, int dataSet, int bid, String targetMovie, int[] randoms) {
        this.context = context;
        this.pid = pid;
        this.sid = sid;
        this.method = method;
        this.dataSet = dataSet;
        this.id = bid;
        this.targetMovie = targetMovie;
        this.movieLength = MovieList.getMovie(context, targetMovie) != null ? MovieList.getMovie(context, targetMovie).getLength() : 0;
        this.randoms = randoms;

        switch (sid) {
            case 1:
                for (int i = 0; i < session1_tasks.length; ++i) {
                    Task task = new Task(pid, sid, method, dataSet, bid, i + 1, session1_tasks[i], targetMovie, movieLength);
                    if (i == 0) {
                        task.actionsNeeded = calculateS1T1ActionsNeeded();
                    }
                    tasks.add(task);
                }
                break;
            case 2:
                for (int i = 0; i < SESSION_2_NUM_TASK; ++i) {
                    Task task = new Task(pid, sid, method, dataSet, bid, i + 1, "Question " + (i + 1), targetMovie, movieLength);
                    task.actionsNeeded = 3;
                    tasks.add(task);
                }
                break;
            default:
                for (int i = 0; i < SESSION_3_NUM_TASK; ++i) {
                    this.targetMovie = dataSet == 0 ? session3_tasks1[i]: session3_tasks2[i];
                    this.movieLength = MovieList.getMovie(context, this.targetMovie) != null ? MovieList.getMovie(context, this.targetMovie).getLength() : 0;
                    Task task = new Task(pid, sid, method, dataSet, bid, i + 1, "Search " + (i + 1), this.targetMovie, movieLength);
                    tasks.add(task);
                }
                break;
        }
    }

    public Task nextTask() {
        switch (sid) {
            case 1:
                index = Math.min(index + 1, session1_tasks.length - 1);
                break;
            case 2:
                index = Math.min(index + 1, SESSION_2_NUM_TASK - 1);
                break;
            default:
                index = Math.min(index + 1, SESSION_3_NUM_TASK - 1);
                break;
        }
        return tasks.get(index);
    }

    public int getCurrentIndex() {
        return index;
    }

    public Task getCurrentTask() {
        return tasks.get(index);
    }

    @NonNull
    @Override
    public String toString() {
        if (sid == 3) {
            for (Task task: tasks) {
                incorrectTitleCount += task.incorrectTitleCount;
            }
            errorRate = incorrectTitleCount != 0 ? 10.0 / incorrectTitleCount : 0;
            blockCompletionTime = endTime - startTime;
            return "" + pid + "," + method + "," + sid + "," + dataSet + "," + id + "," + blockCompletionTime + "," + startTime + "," + endTime + "," + actionsPerBlock + "," + errorRate + "\n";

        } else {
            for (Task task: tasks) {
                actionsNeeded += task.actionsNeeded;
            }
            blockCompletionTime = endTime - startTime;
            errorRate = actionsNeeded != 0 ? ((double) actionsPerBlock - (double) actionsNeeded) / actionsNeeded : 0;
            return "" + pid + "," + method + "," + sid + "," + dataSet + "," + id + "," + targetMovie + "," + movieLength + "," + selectedMovie + "," + blockCompletionTime + "," + startTime + "," + endTime + "," + actionsPerBlock + "," + actionsNeeded + "," + errorRate + "," + actionUpsPerBlock + "\n";
        }
    }

    private int calculateS1T1ActionsNeeded() {
        int count = 0;
        int i = id - 1;
        if (i == 0) { // 1st movie/block
            count = randoms[i] + 1; // vertical navigation (0) + horizontal navigation + click
        } else {
            int categoryDiff = 1;
            int positionDiff = Math.abs(randoms[i] - randoms[i - 1]);
            count = categoryDiff + positionDiff + 1; // vertical difference + horizontal difference + click
        }
        return count;
    }
}
