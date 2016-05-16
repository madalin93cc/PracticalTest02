package ro.pub.cs.systems.eim.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    private TextView port, nr1, nr2;
    private Button startServerBtn, addBtn, mulBtn;
    private ButtonClickListener buttonClickListener = new ButtonClickListener();

    private ServerThread serverThread;
    private ClientThread clientThread;

    public class ClientThread extends Thread{
        private Integer port;
        private String text;

        public ClientThread(Integer port, String text) {
            this.port = port;
            this.text = text;
        }

        @Override
        public void run() {
            try {
                Socket socket = new Socket(Constants.ADDRESS, port);
                BufferedReader bufferedReader = Utilities.getReader(socket);
                PrintWriter printWriter = Utilities.getWriter(socket);
                if (bufferedReader != null && printWriter != null) {
                    printWriter.println(text);
                    printWriter.flush();
                    final String result = bufferedReader.readLine();
                    if (result != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(
                                        getApplicationContext(),
                                        result,
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        });
                    }
                } else {
                    Log.e(Constants.TAG, "[CLIENT THREAD] BufferedReader / PrintWriter are null!");
                }
            } catch (Exception e) {
                Log.e(Constants.TAG, "An exception has occurred: "+e.getMessage());
            }
        }
    }

    private class ButtonClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.startBtn: {
                    if (port.getText() != null) {
//                        start server
                        serverThread = new ServerThread();
                        serverThread.startServer(Integer.parseInt(port.getText().toString()));
                        Log.v(Constants.TAG, "Starting server...");
                    } else {
                        Toast.makeText(
                                getApplicationContext(),
                                "Server port should be filled!",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                    break;
                }
                case R.id.addBtn: {
                    if (port.getText() != null) {
//                        start client
                        String text = "add," + nr1.getText().toString() + "," + nr2.getText().toString();
                        clientThread = new ClientThread(Integer.parseInt(port.getText().toString()), text);
                        clientThread.start();
                    } else {
                        Toast.makeText(
                                getApplicationContext(),
                                "Server port should be filled!",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                    break;
                }
                case R.id.mulBtn: {
                    if (port.getText() != null) {
//                        start client
                        String text = "mul," + nr1.getText().toString() + "," + nr2.getText().toString();
                        clientThread = new ClientThread(Integer.parseInt(port.getText().toString()), text);
                        clientThread.start();
                    } else {
                        Toast.makeText(
                                getApplicationContext(),
                                "Server port should be filled!",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                    break;
                }
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        port = (TextView) findViewById(R.id.port);
        nr1 = (TextView) findViewById(R.id.nr1);
        nr2 = (TextView) findViewById(R.id.nr2);

        startServerBtn = (Button) findViewById(R.id.startBtn);
        addBtn = (Button) findViewById(R.id.addBtn);
        mulBtn = (Button) findViewById(R.id.mulBtn);

        startServerBtn.setOnClickListener(buttonClickListener);
        addBtn.setOnClickListener(buttonClickListener);
        mulBtn.setOnClickListener(buttonClickListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serverThread != null) {
            serverThread.stopServer();
        }
    }
}
