package com.example.instantworkout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;

import org.json.JSONException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class PreformExercise extends AppCompatActivity {

    private Exercise[] exercises = null;
    private TrainingStyle ts = null;
    private Exercise[] workout = null;
    private int g_offset = 0;
    private String username = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preform_exercise);

        exercises = (Exercise[]) getIntent().getSerializableExtra("Exercises");
        ts = (TrainingStyle) getIntent().getSerializableExtra("Training");
        workout = (Exercise[]) getIntent().getSerializableExtra("Workout");

        Button compButton = (Button) findViewById(R.id.completebtn);
        Button skipButton = (Button) findViewById(R.id.skipbtn);
        Button failButton = (Button) findViewById(R.id.failbtn);

        //Toast.makeText(this, "PRECALL", Toast.LENGTH_SHORT).show();
        showExercise();

        compButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckExercise();
                g_offset++;
                if(g_offset < workout.length) {
                    workout[g_offset-1].setpass(true);
                    showExercise();
                }
                else
                    dbPushWorkout();
            }
        });

        failButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //workouts by default are set to fail so no need to change pass value
                g_offset++;
                if(g_offset < workout.length)
                    showExercise();
                else
                    dbPushWorkout();
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workout[g_offset].setskip(true);
                //add new exercise
                Exercise [] temp = new Exercise[workout.length+1];
                for(int i = 0; i < workout.length; i++) {
                    temp[i] = workout[i];
                }
                temp[workout.length] = genSkip(workout[g_offset].getsOffset(), workout[g_offset].getIntensity().get_Reps());
                Toast.makeText(PreformExercise.this, "Added New Exercise.", Toast.LENGTH_SHORT).show();
                workout = temp;
                g_offset++;
                showExercise();
            }
        });

    }

    protected void showExercise()
    {
        TextView workoutName = (TextView) findViewById(R.id.exname);
        TextView workoutWeight = (TextView) findViewById(R.id.exweight);
        TextView workoutSet = (TextView) findViewById(R.id.exset);
        TextView workoutRep = (TextView) findViewById(R.id.exrep);

        String[] exerName = getResources().getStringArray(R.array.exerciseNames);

        workoutName.setText(exerName[workout[g_offset].getsOffset()]);
        //Toast.makeText(this, "NAME", Toast.LENGTH_SHORT).show();

        if(workout[g_offset].getIntensity().get_Style()) {
            workoutWeight.setText(String.format("%d %s", workout[g_offset].getIntensity().get_Time(), getString(R.string.poundage)));
        } else {
            workoutWeight.setText(String.format("%d %s", workout[g_offset].getIntensity().get_Time(), getString(R.string.time)));
        }
        //Toast.makeText(this, "WEIGHT", Toast.LENGTH_SHORT).show();

        workoutSet.setText(String.format("%s Sets", Integer.toString(workout[g_offset].getIntensity().get_Sets())));
        //Toast.makeText(this, "INTENSE", Toast.LENGTH_SHORT).show();

        workoutRep.setText(String.format("%s Reps", Integer.toString(workout[g_offset].getIntensity().get_Reps())));
        //Toast.makeText(this, "REP", Toast.LENGTH_SHORT).show();
    }

    private void dbPushWorkout()
    {
        boolean suc = false;
        APIDataService service = new APIDataService(PreformExercise.this);
        String push_url = "https://instantworkoutsapi.herokuapp.com/users/updateScores";
        String[] exerName = getResources().getStringArray(R.array.DBexerNames);
        username = "graham";
        HashMap<String, String> uploadParams = new HashMap<>();
        for(int i = 0; i < workout.length; i++)
        {
            if(!workout[i].isskip()) {
                if (workout[i].ispass()) {
                    exercises[workout[i].getsOffset()].setsScore(exercises[workout[i].getsOffset()].getsScore() + 2);
                }
                else
                {
                    if(exercises[workout[i].getsOffset()].getsScore() > 1)
                        exercises[workout[i].getsOffset()].setsScore(exercises[workout[i].getsOffset()].getsScore() - 1);
                }
            }
        }

        uploadParams.put("username", username);
        for(int i = 0; i < exercises.length; i++)
        {
            uploadParams.put(exerName[i], String.valueOf(exercises[i].getsScore()));
        }

        service.callAPIJSON(push_url, uploadParams, Request.Method.PUT, responseUpload -> {
            try {
                String success = responseUpload.getString("success");
                if (success.equals("true")) {
                    Toast.makeText(PreformExercise.this, "Exercise Completed and Recorded!", Toast.LENGTH_SHORT).show();
                    Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(startIntent);
                } else {
                    Toast.makeText(PreformExercise.this, "Error connecting to database... Could not save exercise...", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }
    private Exercise genSkip(int exSkip, int reps)
    {
        int hOrL = 0;
        if(reps == 10)
            hOrL = 1;
        else if(reps == 4)
            hOrL = 2;

        Exercise ret;
        if(exercises[exSkip].geteType())
        {
            ArrayList<Integer> arr = new ArrayList<>();
            for (int i = 0; i < 16; i++){
                if(exercises[i].getsScore() > -1 && i != exSkip)
                    arr.add(i);
                if(i == 3)
                    i += 8;
            }
            Collections.shuffle(arr);
            ret = new Exercise(hOrL,true,exercises[arr.get(0)].getsScore(),false,false,arr.get(0),true);
        }
        else
        {
            ArrayList<Integer> arr = new ArrayList<>();
            for (int i = 4; i < 12; i++){
                if(exercises[i].getsScore() > -1 && i != exSkip)
                    arr.add(i);
            }
            Collections.shuffle(arr);
            if(arr.get(0) == 10 || arr.get(0) == 11)
                ret = new Exercise(hOrL,false,exercises[arr.get(0)].getsScore(),false,false,arr.get(0),false);
            else
                ret = new Exercise(hOrL,false,exercises[arr.get(0)].getsScore(),false,false,arr.get(0),true);
        }
        return ret;
    }

    private void CheckExercise()
    {
        Toast.makeText(this, "Inside of CE", Toast.LENGTH_SHORT).show();
        EditText input;
        EditText input2;
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final String[] txt = new String[2];

        Toast.makeText(this, Integer.toString(workout[g_offset].getsOffset()), Toast.LENGTH_SHORT).show();


        Toast.makeText(this, Integer.toString(exercises[workout[g_offset].getsOffset()].getsScore()), Toast.LENGTH_SHORT).show();


        if(exercises[workout[g_offset].getsOffset()].getsScore() == 0)
        {
            Toast.makeText(this, "FIRST BUILDER", Toast.LENGTH_SHORT).show();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Reps");
            builder.setIcon(R.drawable.ic_launcher_foreground);
            if(workout[g_offset].getIntensity().get_Style()) {
                builder.setMessage("This is your first time preforming this exercise, how many reps did you preform and how much weight did you use?");
            } else {
                builder.setMessage("This is your first time preforming this exercise, how many reps did you preform and how long in seconds did you preform it for?");
            }

            Toast.makeText(this, "EDIT TEXT", Toast.LENGTH_SHORT).show();

            input = new EditText(this);
            input.setHint("Reps");
            input.setInputType(InputType.TYPE_CLASS_NUMBER);

            input2 = new EditText(this);
            input2.setHint("Weight");
            input2.setInputType(InputType.TYPE_CLASS_NUMBER);

            layout.addView(input);
            layout.addView(input2);

            builder.setView(layout);

            Toast.makeText(this, "SET BUTTON", Toast.LENGTH_SHORT).show();

            builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int value = 1;
                    int a = 1;
                    int b = 1;
                    if(input.getText() != null && !input.getText().toString().equals(""))
                        a = Integer.parseInt(input.getText().toString());
                    if(input2.getText() != null && !input.getText().toString().equals(""))
                        b = Integer.parseInt(input2.getText().toString());
                    //Integer.parseInt(txt[1]);
                    if(a>12)
                        a = 12;
                    else if (a < 1)
                        a = 1;
                    if(b< 1)
                        b = 1;

                    value = repTimeCalc(a, b);

                    exercises[workout[g_offset].getsOffset()].setsScore(value);
                }
            });
            Toast.makeText(this, "BUILDER SET UP", Toast.LENGTH_SHORT).show();
            AlertDialog ad = builder.create();

            Toast.makeText(this, "SHOW", Toast.LENGTH_SHORT).show();

            ad.show();

            /*AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
            builder2.setTitle("Sets");
            builder2.setIcon(R.drawable.ic_launcher_foreground);


            builder2.setView(input);
            builder2.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(input.getText() == null)
                        txt[1] = "0";
                    else
                        txt[1] = input2.getText().toString();
                    Toast.makeText(PreformExercise.this, "TIME", Toast.LENGTH_SHORT).show();
                }
            });*/
            //Toast.makeText(this, "BUILDER SET UP", Toast.LENGTH_SHORT).show();
            //AlertDialog ad2 = builder2.create();
            //ad2.show();

            //Toast.makeText(this, "AD SHOW RUN", Toast.LENGTH_SHORT).show();
        }
    }

    private int repTimeCalc(int rep, int time)
    {
        int ret = 1;

        double f = rep * 2.5;

        f = 100 - f;

        f = f / 100;

        ret = (int) (time / f);

        return ret;
    }
}
