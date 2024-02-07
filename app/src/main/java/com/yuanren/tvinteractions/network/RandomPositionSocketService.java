package com.yuanren.tvinteractions.network;

import android.os.Handler;
import android.util.Log;

import com.yuanren.tvinteractions.base.SocketUpdateCallback;
import com.yuanren.tvinteractions.model.MovieList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class RandomPositionSocketService {
    public static final String TAG = "RandomPositionSocketService";
    public static final int SERVER_PORT = 5051;
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static Thread serverThread = null; //here it sets the Thread initially to null
    private static Handler handler = new Handler();

    private static String participant = "";
    private static String session = "";
    private static String method = "";
    private static String dataSet = "";
    private static String blockNum = "";


    public static void setLogBasic(String pid, String sid, String mid, String ds, String b) {
        participant = pid;
        session = sid;
        method = mid;
        dataSet = ds;
        blockNum = b;
    }

    public static void start() {
        if (serverThread != null) {
            stop();
        }
        serverThread = new Thread(new ServerThread());
        serverThread.start();
    }

    public static void stop() {
        if (null != serverThread) {
            serverThread.interrupt();
            serverThread = null;
        }
    }

    /** Activate the server network */
    static class ServerThread implements Runnable {

        public void run() {
            try {
                serverSocket = new ServerSocket(SERVER_PORT);
                Log.i(TAG,"Random position service Start");

                //communicates to client and displays error if communication fails
                while (null != serverSocket && !Thread.currentThread().isInterrupted()) {
                    clientSocket = serverSocket.accept();

                    // start thread
                    CommunicationThread communicationThread = new CommunicationThread(clientSocket);
                    new Thread(communicationThread).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /** Implements the runnable class to communicate with the client */
    static class CommunicationThread implements Runnable {

        private Socket commSocket;
        private PrintWriter output;

        private BufferedReader input;

        public CommunicationThread(Socket commSocket) {
            try {
                this.commSocket = commSocket;
                this.output = new PrintWriter(this.commSocket.getOutputStream(), true);
                this.input = new BufferedReader(new InputStreamReader(this.commSocket.getInputStream()));
            } catch (SocketException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i(TAG,"Random position service: connected to client!");
        }

        @Override
        public void run() {
//            while (!Thread.currentThread().isInterrupted()) {
            boolean connected = true;
            try {
                while (connected) {
                    // write random positions of movies to smartwatch side
                    String basic = participant + "," + session + "," + method + "," + dataSet + "," + blockNum + ";";
                    output.println(basic + MovieList.getRandomPosString(MovieList.randomPositions));
                    Log.d(TAG, "Random position server sent: " + basic + MovieList.getRandomPosString(MovieList.randomPositions));

                    String inputLine = input.readLine();
                    if (inputLine == null) {
                        Log.d(TAG,"Random position client disconnected!");
                        connected = false;
                    }
                }
                output.close();
                input.close();
                commSocket.close();
                serverSocket.close();
            } catch(IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
