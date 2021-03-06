package com.example.jour;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Read in journal prompts
        final List<String> prompts;
        prompts = getPrompts("prompts.txt");
        Collections.shuffle(prompts);
        int num_prompts = prompts.size();
        Log.e("Number of prompts", String.valueOf(num_prompts));

        // Set initial prompt
        TextView prompt_view = findViewById(R.id.prompt);
        final int randomNum = ThreadLocalRandom.current().nextInt(0, num_prompts);
        String random_prompt = prompts.get(randomNum);
        prompt_view.setText(random_prompt);

        // Serve new prompt
        FloatingActionButton refresh = findViewById(R.id.refresh);
        refresh.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int randomNum = ThreadLocalRandom.current().nextInt(0, num_prompts);
                String random_prompt = prompts.get(randomNum);
                prompt_view.setText(random_prompt);
            }
        });
    }

    private List<String> getPrompts(String fileName) {
        List<String> lines = new ArrayList<String>();
        try {
            InputStream stream;
            stream = getAssets().open(fileName);
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(stream));
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
                return lines;
            } finally {
                if (reader != null)
                    reader.close();
            }
        } catch (IOException e) {
            return lines;
        }
    }
}