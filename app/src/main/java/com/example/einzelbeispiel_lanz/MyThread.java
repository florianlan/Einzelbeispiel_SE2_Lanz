package com.example.einzelbeispiel_lanz;

import android.os.AsyncTask;
import android.util.Log;

import java.io.*;
import java.net.Socket;

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
        String returnValue = "";
        try {
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(
                    new ByteArrayInputStream(matNumber.getBytes())));
            clientSocket = new Socket(ip, port);

            outToServer = new DataOutputStream(clientSocket.getOutputStream());
            Log.i("INFO", "doInBackground: 0");
            BufferedReader inFromServer = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            outToServer.writeBytes(inFromServer.readLine());
            Log.i("INFO", "doInBackground: 1");

            returnValue = inFromServer.readLine();
            Log.i("INFO", "doInBackground: Got DataOutputStream " + returnValue);
            Log.i("INFO", "doInBackground: 2");
            outToServer.close();
            clientSocket.close();
            Log.i("INFO", "doInBackground: 3");

        } catch (IOException ioException) {
            Log.e("ERROR", "doInBackground: Error while connecting", ioException);
        }

        if (returnValue == null || outToServer.toString().length() == 0) {
            return "";
        }
        return returnValue;
    }

}
