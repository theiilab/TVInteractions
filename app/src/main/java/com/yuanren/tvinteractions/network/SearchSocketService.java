package com.yuanren.tvinteractions.network;

import android.os.Handler;
import android.util.Log;

import com.yuanren.tvinteractions.base.SocketUpdateCallback;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class SearchSocketService {
    public static final String TAG = "SearchSocketService";
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
                Log.i(TAG,"Search service start");

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
        private BufferedReader input;

        public CommunicationThread(Socket commSocket) {
            try {
                this.commSocket = commSocket;
                this.input = new BufferedReader(new InputStreamReader(this.commSocket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i(TAG,"Search service: connected to Client!");
        }

        public void run() {
            // get search input from smartwatch side
//            while (!Thread.currentThread().isInterrupted()) {
            try {
                while (true) {
                    String inputLine = input.readLine();
                    if (inputLine == null) {
                        Log.d(TAG,"Search client disconnected!");
                        break;
                    }

                    // update message on UI Thread
                    String text = loadActionData(inputLine);
                    socketUpdateCallback.update(handler, text);
                    Log.i(TAG, "Search client : " + inputLine);
                }
                input.close();
                commSocket.close();
                serverSocket.close();
            } catch(IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}