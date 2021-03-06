package com.example.einzelbeispiel_lanz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button send;
    Button calculate;
    EditText matNumber;
    TextView showResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        send = findViewById(R.id.btn);
        calculate = findViewById(R.id.btn2);
        matNumber = findViewById(R.id.entryMatNumber);
        showResult = findViewById(R.id.answerServer);


        send.setOnClickListener(v -> {
            String input = matNumber.getText().toString();

            if (isValidInput(input)) {
                MyThread thread = new MyThread();
                thread.execute(matNumber.getText().toString());

            }


        });

        calculate.setOnClickListener(v -> {
            String input = matNumber.getText().toString();
            if (isValidInput(input)) {
                showResult.setText(calculate(input));
            } else {
                showResult.setText("Antwort vom Server");

            }

        });

    }

    private boolean isValidInput(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                matNumber.setError("Es dürfen nur Zahlen verwendet werden!");
                return false;
            }
        }
        if (s.length() != 8) {
            matNumber.setError("Die Matrikelnummer muss 8 Zahlen beinhalten!");
            return false;
        }

        return true;

    }

    private String calculate(String s) {
        StringBuilder result = new StringBuilder();
        List<Integer> notPrime = new ArrayList<>();
        notPrime.add(0);
        notPrime.add(1);
        notPrime.add(4);
        notPrime.add(6);
        notPrime.add(8);
        notPrime.add(9);

        for (Integer i : notPrime) {
            for (int j = 0; j < s.length(); j++) {
                int temp = s.charAt(j) - '0';
                if (temp == i) {
                    result.append(i);
                }
            }
        }

        return result.toString();
    }

    private void setText(String s) {
        runOnUiThread(() -> showResult.setText(s));
    }


    /**
     * Thread for the tcp call
     */
    public class MyThread extends AsyncTask<String, Void, String> {
        Socket clientSocket;
        DataOutputStream outToServer;
        String ip, matNumber;
        int port;

        @Override
        protected String doInBackground(String... strings) {
            ip = "se2-isys.aau.at";
            port = 53212;
            matNumber = strings[0];
            try {
                clientSocket = new Socket(ip, port);

                outToServer = new DataOutputStream(clientSocket.getOutputStream());

                BufferedReader inFromServer = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));

                outToServer.writeBytes(matNumber + '\n');

                setText(inFromServer.readLine());

                outToServer.close();
                clientSocket.close();

            } catch (IOException ioException) {
                Log.e("ERROR", "doInBackground: Error while connecting", ioException);
            }

            return null;
        }
    }

}