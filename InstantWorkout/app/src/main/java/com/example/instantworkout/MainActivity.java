package com.example.instantworkout;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnExerciseSelect = findViewById(R.id.btnSelectExercises);
        Button btnGenWorkout = findViewById(R.id.btnWorkoutGen);

        Exercise[] exercises = GetExercises();

        btnExerciseSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), DesiredExercises.class);
                startIntent.putExtra("Exercises", exercises);
                startActivity(startIntent);
            }
        });

        btnGenWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), GenerateWorkout.class);
                startIntent.putExtra("Exercises", exercises);
                startActivity(startIntent);
            }
        });

    }

    protected Exercise[] GetExercises()
    {
        Exercise [] e = new Exercise[4];
        for (int i =0; i < e.length; i++){
            e[i] = new Exercise();
        }
        return e;
    }
}