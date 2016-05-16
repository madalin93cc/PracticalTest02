package ro.pub.cs.systems.eim.test;

import android.util.Log;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by colez on 16/05/2016.
 */
public class CommunicationThread extends Thread {
    private Socket socket;

    public CommunicationThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        if (socket != null) {
            try {
                BufferedReader br = Utilities.getReader(socket);
                PrintWriter pw = Utilities.getWriter(socket);
//                read from socket
                if (br != null && pw != null) {
                    String line = br.readLine();
                    if (line != null) {
                        String[] words = line.split(",");
                        Integer result = null;
                        switch (words[0]) {
                            case "add": {
                                result = Integer.parseInt(words[1]) + Integer.parseInt(words[2]);
                                break;
                            }
                            case "mul": {
                                Thread.sleep(2000);
                                result = Integer.parseInt(words[1]) * Integer.parseInt(words[2]);
                            }
                        }
                        if (result != null) {
                            pw.println(result.toString());
                        }
                    }
                } else {
                    Log.e(Constants.TAG, "[CLIENT THREAD] BufferedReader / PrintWriter are null!");
                }
                socket.close();
            } catch (Exception e) {
                Log.e(Constants.TAG, "An exception has occurred: " + e.getMessage());
            }
        }
    }
}
