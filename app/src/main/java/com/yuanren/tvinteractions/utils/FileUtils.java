package com.yuanren.tvinteractions.utils;

import android.content.Context;
import android.util.Log;

import com.yuanren.tvinteractions.log.Action;
import com.yuanren.tvinteractions.log.Metrics;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {
    private static final String TAG = "FileUtils";
    private static final String extension = ".csv";
    public static final String logHeader1 = "Participant,Method,Session,Data Set,Block,Target Movie,Movie Length(s),Selected Movie,Task Number,Task,Task Completion Time (ms),Start Time(ms),End Time(ms),Actions Per Task,Actions Needed,Error Rate\n";
    public static final String logHeader2 = "Participant,Method,Session,Data Set,Block,Target Movie,Movie Length(s),Selected Movie,Task Number,Task,Task Completion Time (ms),Start Time(ms),End Time(ms),Actions Per Task,Actions Needed,Error Rate\n";
    public static final String logHeader3 = "Participant,Method,Session,Data Set,Block,Target Movie,Movie Length(s),Selected Movie,Task Number,Task,Task Completion Time (ms),Start Time(ms),End Time(ms),Error Rate,Character Per Second,Backspace Count,Time Per Character(ms),Total Character Entered\n";
    public static final String logRawHeader = "Participant,Method,Session,Block,Target Movie,Selected Movie,Action,Scope,Start Time,End Time,Duration,Other\n";


    public static void write(Context context, Metrics metrics) {
        String filename;
        if (metrics.session == 3) {
            filename = "P" + metrics.pid + "-" + metrics.method + "-Search" + extension;
        } else {
            filename = "P" + metrics.pid + "-" + metrics.method + extension;
        }

        File file = new File(context.getFilesDir(), filename);

        String data = "";
        if (!file.exists()) {
            if (metrics.session == 1) {
                data = logHeader1;
            } else if (metrics.session == 2) {
                data = logHeader2;
            } else {
                data = logHeader3;
            }
        }
        data += metrics.toString();

        try {
            FileOutputStream stream = new FileOutputStream(file, true);
            stream.write(data.getBytes());
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Data written");
    }

    public static void writeRaw(Context context, Action action) {
        String filename;
        if (action.session == 3) {
            filename = "P" + action.pid + "-" + action.method + "-Search-Raw" + extension;

        } else {
            filename = "P" + action.pid + "-" + action.method + "-Raw" + extension;
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
