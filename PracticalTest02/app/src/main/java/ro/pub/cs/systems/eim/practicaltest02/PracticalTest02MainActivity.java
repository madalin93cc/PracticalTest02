package ro.pub.cs.systems.eim.practicaltest02;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest02MainActivity extends AppCompatActivity {
//    global variables
    EditText serverPortText, clientAddressText, clientPortText;
    Button startServerBtn, startClientBtn;

    ButtonClickListener buttonClickListener = new ButtonClickListener();
//    client and server
    ServerThread serverThread;
    ClientThread clientThread;

    private class ButtonClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.startServerBtn: {
//                    TODO checks
                    if (serverPortText.getText() != null) {
                        serverThread = new ServerThread();
                        serverThread.startServer(Integer.parseInt(serverPortText.getText().toString()));
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
                case R.id.startClientBtn: {
                    if (clientPortText.getText() != null && clientAddressText.getText() != null) {
                        clientThread = new ClientThread(clientAddressText.getText().toString(), Integer.parseInt(clientPortText.getText().toString()));
                        clientThread.start();
                        Log.v(Constants.TAG, "Starting client...");
                    }
                    break;
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);

//        TODO set views and handlers
//        server
        serverPortText = (EditText) findViewById(R.id.serverPortText);
        startServerBtn = (Button) findViewById(R.id.startServerBtn);
        startServerBtn.setOnClickListener(buttonClickListener);
//        client
        clientAddressText = (EditText) findViewById(R.id.clientAddressText);
        clientPortText = (EditText) findViewById(R.id.clientPortText);
        startClientBtn = (Button) findViewById(R.id.startClientBtn);
        startServerBtn.setOnClickListener(buttonClickListener);
    }

    @Override
    public void onDestroy() {
        if (serverThread != null) {
            serverThread.stopServer();
        }
        super.onDestroy();
    }
}
