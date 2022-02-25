package com.example.instantworkout;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.webkit.TracingConfig;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    private static final String URL_GET_DATA = "https://instantworkoutsapi.herokuapp.com/users/collectData";
    private static final String URL_GET_TS = "https://instantworkoutsapi.herokuapp.com/users/collectTraining";
    private String username = "graham";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnExerciseSelect = findViewById(R.id.btnSelectExercises);
        Button btnGenWorkout = findViewById(R.id.btnWorkoutGen);

        Exercise[] exercises = GetExercises();
        TrainingStyle ts = GetTrainingStyle();

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
                startIntent.putExtra("Training", ts);
                startIntent.putExtra("Exercises", exercises);
                startActivity(startIntent);
            }
        });

    }

    private TrainingStyle GetTrainingStyle() {
        TrainingStyle ret = (TrainingStyle) getIntent().getSerializableExtra("Training");
        TrainingStyle e = new TrainingStyle();
        if(ret == null)
        {
            APIDataService service = new APIDataService(MainActivity.this);
            HashMap<String, String> obj = new HashMap<String, String>();
            obj.put("username", username);

            service.callAPIURL(URL_GET_TS, obj, Request.Method.GET, response ->
            {
                try {
                    e.set_Cycle(Integer.parseInt(response.getString("trainingCycle")));
                    e.set_TrainingStyleID(Integer.parseInt(response.getString("trainingStyle")));
                    e.set_TrainingStyle(getResources().getStringArray(R.array.trainingStyles)[e.get_TrainingStyleID()]);
                } catch (JSONException err) {
                    err.printStackTrace();
                    Toast.makeText(MainActivity.this, "Error Connecting To database Try Again Later.", Toast.LENGTH_SHORT).show();
                }
            });
        }
        ret = e;

        return ret;
    }

    protected Exercise[] GetExercises()
    {
        Exercise [] r = (Exercise[]) getIntent().getSerializableExtra("Exercises");
        Exercise [] e = new Exercise[16];
        if(r== null) {

            APIDataService service = new APIDataService(MainActivity.this);
            HashMap<String, String> obj = new HashMap<String, String>();
            obj.put("username", username);

            for (int i = 0; i < e.length; i++) {
                e[i] = new Exercise();
            }
            String[] exerName = getResources().getStringArray(R.array.DBexerNames);
            service.callAPIURL(URL_GET_DATA, obj, Request.Method.GET, response ->
            {
                try {
                    for (int i = 0; i < e.length; i++) {
                        e[i].setsScore(Integer.parseInt(response.getString(exerName[i])));
                        e[i].setsOffset(i);
                    }
                } catch (JSONException err) {
                    err.printStackTrace();
                    Toast.makeText(MainActivity.this, "Error Connecting To database Try Again Later.", Toast.LENGTH_SHORT).show();
                }
            });

        }
        r = e;
        return r;
    }
}