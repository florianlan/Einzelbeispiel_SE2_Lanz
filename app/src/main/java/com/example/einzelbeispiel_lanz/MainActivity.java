package com.example.einzelbeispiel_lanz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button send;
    TextView enterMat;
    EditText matNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        send = findViewById(R.id.btn);
        enterMat = findViewById(R.id.enterMat);
        matNumber = findViewById(R.id.entryMatNumber);


        send.setOnClickListener(v -> {
            String input = matNumber.getText().toString();
            for (int i = 0; i < input.length(); i++) {
                if (!Character.isDigit(input.charAt(i))) {
                    matNumber.setError("Es dürfen nur Zahlen verwendet werden!");
                    return;
                }
            }
            if (input.length() != 8) {
                matNumber.setError("Die Matrikelnummer muss 8 Zahlen beinhalten!");
                return;
            }




        });

    }


}