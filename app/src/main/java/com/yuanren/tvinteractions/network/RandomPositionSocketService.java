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
    public static final String TAG = "NetworkUtils";
    public static final int SERVER_PORT = 5051;
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
        private PrintWriter output;

        public CommunicationThread(Socket commSocket) {
            this.commSocket = commSocket;

            try {
                // no effect, need to work on more
                this.commSocket.setSoTimeout(30000);

                this.output = new PrintWriter(this.commSocket.getOutputStream(), true);
            } catch (SocketException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i(TAG,"2 Connected to Client!");
        }

        public void run() {
            // write random positions of movies to smartwatch side
            while (!Thread.currentThread().isInterrupted()) {
                // no effect, need to work on more
                if (commSocket.isClosed()) {
                    Thread.interrupted();
                    Log.i(TAG,"Interrupted, client close connection");
                    break;
                }
                output.println(MovieList.getRandomPosString(MovieList.randomPositions));
            }
        }
    }
}
