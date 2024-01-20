package com.yuanren.tvinteractions.utils;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {
    private static final String TAG = "FileUtils";
    private static final String extension = ".csv";
    public static final String logHeader = "Participant,Action Id,Method,Action,Task Completion Time (ms),Action Completion Time (ms),Error Rate,Distance Swiped (px),Angle Tilted (degree),Force Changed\n";
    public static final String logRawHeader = "Participant,Action Id,Method,Action,Task Start Time,Task End Time,Action Start Time,Action End Time,Error Rate,From X,From Y,To X,To Y,Start Angle,End Angle,Min Force, Max Force\n";


    public static void write(Context context, int pid, String method, String data) {
        String filename = "P" + pid + "-" + method + extension;

        File file = new File(context.getFilesDir(), filename);
        if (!file.exists()) {
            data = logHeader + data;
        }
        try {
            FileOutputStream stream = new FileOutputStream(file, true);
            stream.write(data.getBytes());
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Data written");
    }

    public static void writeRaw(Context context, int pid, String method, String data) {
        String filename = "P" + pid + "-" + method + extension;

        File file = new File(context.getFilesDir(), filename);
        if (!file.exists()) {
            data = logRawHeader + data;
        }
        try {
            FileOutputStream stream = new FileOutputStream(file, true);
            stream.write(data.getBytes());
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
