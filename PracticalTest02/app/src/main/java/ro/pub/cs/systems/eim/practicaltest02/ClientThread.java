package ro.pub.cs.systems.eim.practicaltest02;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by colez on 16/05/2016.
 */
public class ClientThread extends Thread {
    private String address;
    private Integer port;
    private Socket socket;

    public ClientThread(String address, Integer port) {
        this.address = address;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(address, port);
            if (socket == null) {
                Log.e(Constants.TAG, "[CLIENT THREAD] Could not create socket!");
                return;
            }
            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);
            if (bufferedReader != null && printWriter != null) {
//                printWriter.println(city);
//                printWriter.flush();
//                String weatherInformation;
//                while ((weatherInformation = bufferedReader.readLine()) != null) {
//                    final String finalizedWeatherInformation = weatherInformation;
//                    weatherForecastTextView.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            weatherForecastTextView.append(finalizedWeatherInformation + "\n");
//                        }
//                    });
//                }
            } else {
                Log.e(Constants.TAG, "[CLIENT THREAD] BufferedReader / PrintWriter are null!");
            }
        } catch (Exception e) {
            Log.e(Constants.TAG, "An exception has occurred: " + e.getMessage());
            if (Constants.DEBUG) {
                e.printStackTrace();
            }
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                Log.e(Constants.TAG, "An exception has occurred: " + e.getMessage());
                if (Constants.DEBUG) {
                    e.printStackTrace();
                }
            }
        }
    }
}
