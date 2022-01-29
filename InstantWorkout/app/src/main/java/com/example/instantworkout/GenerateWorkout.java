package com.example.instantworkout;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

public class GenerateWorkout extends AppCompatActivity {

    private Exercise[] exercises = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_workout);
        TextView[] textViews = {findViewById(R.id.textView),findViewById(R.id.textView2),findViewById(R.id.textView4),findViewById(R.id.textView3)};

        exercises = (Exercise[]) getIntent().getSerializableExtra("Exercises");

        String[] exerName = getResources().getStringArray(R.array.exerciseNames);

        for (int i = 0; i< textViews.length; i++) {
            textViews[i].setText(exerName[i]);
        }
    }
}