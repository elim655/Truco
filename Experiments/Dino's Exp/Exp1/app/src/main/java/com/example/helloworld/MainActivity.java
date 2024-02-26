package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.SeekBar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button rollButton = findViewById(R.id.rollButton);
        TextView resultsTextView = findViewById(R.id.resultsTextView);
        SeekBar seekBar = findViewById(R.id.seekBar);

        rollButton.setOnClickListener(v -> {
            int rand = new Random().nextInt(seekBar.getProgress()) + 1;
            resultsTextView.setText(String.valueOf(rand));
        });
    }
}
