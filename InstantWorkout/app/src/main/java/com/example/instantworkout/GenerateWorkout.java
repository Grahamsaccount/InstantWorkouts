package com.example.instantworkout;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GenerateWorkout extends AppCompatActivity {

    private Exercise[] exercises = null;
    private TrainingStyle ts = null;
    private Exercise[] workout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Toast.makeText(this, "GEN", Toast.LENGTH_SHORT).show();

        setContentView(R.layout.activity_generate_workout);
        //Toast.makeText(this, "CONTENT SET", Toast.LENGTH_SHORT).show();

        TextView[] textViews = {findViewById(R.id.textView),findViewById(R.id.textView2),findViewById(R.id.textView4),findViewById(R.id.textView3)};

        exercises = (Exercise[]) getIntent().getSerializableExtra("Exercises");
        ts = (TrainingStyle) getIntent().getSerializableExtra("Training");
        workout = new Exercise[4];

        //Toast.makeText(this, ts.get_TrainingStyle(), Toast.LENGTH_SHORT).show();

        String[] exerName = getResources().getStringArray(R.array.exerciseNames);

        SeekBar repSB = (SeekBar) findViewById(R.id.repSeekBar);
        repSB.setProgress(0);
        repSB.setMax(2);
        repSB.incrementProgressBy(1);

        Button upperButton = (Button) findViewById(R.id.btnArms);
        Button lowerButton = (Button) findViewById(R.id.btnLegs);
        Button subButton = (Button) findViewById(R.id.btnPreform);

        upperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> arr = new ArrayList<>();
                for (int i = 0; i < 16; i++){
                    if(exercises[i].getsScore() > -1)
                        arr.add(i);
                    if(i == 3)
                        i += 8;
                }
                Collections.shuffle(arr);
                List<Integer> arr2;
                arr2 = arr.subList(0,4);
                for (int i = 0; i < arr2.size(); i++){
                    textViews[i].setText(exerName[arr2.get(i)]);
                    workout[i] = new Exercise(repSB.getProgress(),true,exercises[arr2.get(i)].getsScore(),false,false,arr2.get(i),true);
                }
                subButton.setEnabled(true);
            }
        });

        lowerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> arr = new ArrayList<>();
                for (int i = 4; i < 12; i++){
                    if(exercises[i].getsScore() > -1)
                        arr.add(i);
                }
                Collections.shuffle(arr);
                List<Integer> arr2;
                arr2 = arr.subList(0,4);
                for (int i = 0; i < arr2.size(); i++){
                    textViews[i].setText(exerName[arr2.get(i)]);
                    if(arr2.get(i) == 10 || arr2.get(i) == 11)
                        workout[i] = new Exercise(repSB.getProgress(),false,exercises[arr2.get(i)].getsScore(),false,false,arr2.get(i),false);
                    else
                        workout[i] = new Exercise(repSB.getProgress(),false,exercises[arr2.get(i)].getsScore(),false,false,arr2.get(i),true);
                }
                subButton.setEnabled(true);
            }
        });

        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), PreformExercise.class);
                startIntent.putExtra("Training", ts);
                startIntent.putExtra("Exercises", exercises);
                startIntent.putExtra("Workout", workout);
                startActivity(startIntent);
            }
        });

        subButton.setEnabled(false);

        if(ts.get_TrainingStyleID() == 0) {
            textViews[0].setText(R.string.splittext);
        }
        else
        {
            repSB.setProgress(ts.get_Cycle()/2);
            repSB.setClickable(false);
            if(ts.get_Cycle()%2 == 0)
            {
                upperButton.callOnClick();
                lowerButton.setClickable(false);
            }
            else
            {
                lowerButton.callOnClick();
                upperButton.setClickable(false);
            }
            //flip flop auto select
        }
    }
}