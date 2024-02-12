package com.yuanren.tvinteractions.utils;

import android.content.Context;
import android.util.Log;

import com.yuanren.tvinteractions.log.Action;
import com.yuanren.tvinteractions.log.Block;
import com.yuanren.tvinteractions.log.Session;
import com.yuanren.tvinteractions.log.Task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {
    private static final String TAG = "FileUtils";
    private static final String extension = ".csv";
    public static final String blockHeader1 = "Participant,Method,Session,Data Set,Block,Target Movie,Movie Length(s),Selected Movie,Block Completion Time (ms),Start Time(ms),End Time(ms),Actions Per Block,Actions Needed,Error Rate,Action Up Per Block on TV\n";
    public static final String blockHeader2 = "Participant,Method,Session,Data Set,Block,Target Movie,Movie Length(s),Selected Movie,Block Completion Time (ms),Start Time(ms),End Time(ms),Actions Per Block,Actions Needed,Error Rate,Action Up Per Block on TV\n";
    public static final String blockHeader3 = "Participant,Method,Session,Data Set,Block,Target Movie,Movie Length(s),Selected Movie,Block Completion Time (ms),Start Time(ms),End Time(ms),Actions Per Block,Error Rate\n";

    public static final String taskHeader1 = "Participant,Method,Session,Data Set,Block,Target Movie,Movie Length(s),Selected Movie,Task,Task Name,Task Completion Time (ms),Start Time(ms),End Time(ms),Actions Per Task,Actions Needed,Error Rate,Action Up Per Task on TV\n";
    public static final String taskHeader2 = "Participant,Method,Session,Data Set,Block,Target Movie,Movie Length(s),Selected Movie,Task,Task Name,Task Completion Time (ms),Start Time(ms),End Time(ms),Actions Per Task,Actions Needed,Error Rate,Action Up Per Task on TV\n";
    public static final String taskHeader3 = "Participant,Method,Session,Data Set,Block,Target Movie,Movie Length(s),Selected Movie,Task,Task Name,Task Completion Time (ms),Start Time(ms),End Time(ms),Actions Per Task,Error Rate,Position On Select,Character Per Second,Backspace Count,Time Per Character(ms),Total Character Entered\n";
    public static final String logRawHeader = "Participant,Method,Session,Block,Target Movie,Selected Movie,Task,Action,Scope,Start Time,End Time,Duration,Other\n";

    public static void write(Context context, Task task) {
        String filename;
        if (task.sid == 3) {
            filename = "P" + task.pid + "-" + task.method + "-Search";
            if (task.method.equals("Smartwatch")) {
                filename += "-TV";
            }
            filename += "-Raw-Metrics" + extension;
        } else {
            filename = "P" + task.pid + "-" + task.method;
            if (task.method.equals("Smartwatch")) {
                filename += "-TV";
            }
            filename += "-Raw-Metrics" + extension;
        }

        File file = new File(context.getFilesDir(), filename);

        String data = "";
        if (!file.exists()) {
            if (task.sid == 1) {
                data = taskHeader1;
            } else if (task.sid == 2) {
                data = taskHeader2;
            } else {
                data = taskHeader3;
            }
        }
        data += task.toString();

        try {
            FileOutputStream stream = new FileOutputStream(file, true);
            stream.write(data.getBytes());
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Task data written");
    }

    public static void write(Context context, Block block) {
        String filename;
        if (block.sid == 3) {
            filename = "P" + block.pid + "-" + block.method + "-Search";
            if (block.method.equals("Smartwatch")) {
                filename += "-TV";
            }
            filename += "-Metrics" + extension;
        } else {
            filename = "P" + block.pid + "-" + block.method;
            if (block.method.equals("Smartwatch")) {
                filename += "-TV";
            }
            filename += "-Metrics" + extension;
        }

        File file = new File(context.getFilesDir(), filename);

        String data = "";
        if (!file.exists()) {
            if (block.sid == 1) {
                data = blockHeader1;
            } else if (block.sid == 2) {
                data = blockHeader2;
            } else {
                data = blockHeader3;
            }
        }
        data += block.toString();

        try {
            FileOutputStream stream = new FileOutputStream(file, true);
            stream.write(data.getBytes());
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Block data written");
    }

    public static void writeRaw(Context context, Action action) {
        if (action == null) {
            return;
        }
        String filename;
        if (action.sid == 3) {
            filename = "P" + action.pid + "-" + action.method + "-Search";
            if (action.method.equals("Smartwatch")) {
                filename += "-TV";
            }
            filename += "-Raw-Log" + extension;
        } else {
            filename = "P" + action.pid + "-" + action.method;
            if (action.method.equals("Smartwatch")) {
                filename += "-TV";
            }
            filename += "-Raw-Log" + extension;
        }

        File file = new File(context.getFilesDir(), filename);

        String data = "";
        if (!file.exists()) {
            data = logRawHeader;
        }
        data += action.toString();

        try {
            FileOutputStream stream = new FileOutputStream(file, true);
            stream.write(data.getBytes());
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Raw data written");
    }
}
