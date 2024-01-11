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

public class SearchSocketService {
    public static final String TAG = "NetworkUtils";
    public static final int SERVER_PORT = 5050;

    private static SocketUpdateCallback socketUpdateCallback;
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static Thread serverThread = null; //here it sets the Thread initially to null
    private static Handler handler = new Handler();

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

    public static String loadActionData(String data) {
        return data;
    }

    public static void setSocketUpdateCallback(SocketUpdateCallback callback) {
        socketUpdateCallback = callback;
    }

    /** Activate the server network */
    static class ServerThread implements Runnable {

        public void run() {
            try {
                serverSocket = new ServerSocket(SERVER_PORT);
                Log.i(TAG,"Server Start");
            } catch (IOException e) {
                e.printStackTrace();
            }

            //communicates to client and displays error if communication fails
            if (null != serverSocket) {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        clientSocket = serverSocket.accept();

                        // start thread
                        CommunicationThread communicationThread = new CommunicationThread(clientSocket);
                        new Thread(communicationThread).start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /** Implements the runnable class to communicate with the client */
    static class CommunicationThread implements Runnable {

        private Socket commSocket;
        private BufferedReader input;

        public CommunicationThread(Socket commSocket) {
            this.commSocket = commSocket;
            try {
                this.input = new BufferedReader(new InputStreamReader(this.commSocket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i(TAG,"1 Connected to Client!");
        }

        public void run() {
            // get search input from smartwatch side
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    String read = input.readLine();

                    //checks to see if the client is still connected and displays disconnected if disconnected
                    if (null == read || "Disconnect".contentEquals(read)) {
                        Thread.interrupted();
                        read = "Offline....";
                        Log.i(TAG,"Client : " + read);
                        break;
                    }
                    Log.i(TAG,"Client : " + read);

                    // update message on UI Thread
                    String text = loadActionData(read);
                    socketUpdateCallback.update(handler, text);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
