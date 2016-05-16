package ro.pub.cs.systems.eim.practicaltest02;

import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by colez on 16/05/2016.
 */
public class ServerThread extends Thread {
    private ServerSocket serverSocket;
    private Integer port;
    private boolean isRunning;

    public void startServer(Integer port) {
        this.port = port;
        isRunning = true;
        start();
        Log.v(Constants.TAG, "startServer() method invoked " + serverSocket);
    }

    public void stopServer() {
        isRunning = false;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (serverSocket != null) {
                        serverSocket.close();
                    }
                    Log.v(Constants.TAG, "stopServer() method invoked "+serverSocket);
                } catch(IOException ioException) {
                    Log.e(Constants.TAG, "An exception has occurred: "+ioException.getMessage());
                    if (Constants.DEBUG) {
                        ioException.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            while (isRunning) {
                Socket socket = serverSocket.accept();
                Log.v(Constants.TAG, "Connection opened with "+socket.getInetAddress()+":"+socket.getLocalPort());
                CommunicationThread communicationThread = new CommunicationThread(socket);
            }
        } catch (IOException ioException) {
            Log.e(Constants.TAG, "An exception has occurred: "+ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        }
    }
}
