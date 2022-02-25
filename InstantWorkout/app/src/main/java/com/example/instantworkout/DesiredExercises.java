package com.example.instantworkout;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.android.volley.Request;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;

import java.util.HashMap;

public class DesiredExercises extends AppCompatActivity {

    private static final int[] ArmIDs = {R.id.arms0, R.id.arms1, R.id.arms2, R.id.arms3, R.id.legs0, R.id.legs1, R.id.legs2, R.id.legs3, R.id.core0, R.id.core1, R.id.core2, R.id.core3, R.id.chest0, R.id.chest1, R.id.chest2, R.id.chest3};
    private String username = "graham";

    private Exercise[] exercises = null;
    private TrainingStyle ts = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_desired_exercises);

        exercises = (Exercise[]) getIntent().getSerializableExtra("Exercises");
        ts = (TrainingStyle) getIntent().getSerializableExtra("Training");

        Button[] Arms = new Button[ArmIDs.length];

        for(int i = 0; i < ArmIDs.length; i++)
        {
            Arms[i] = findViewById(ArmIDs[i]);
            if(exercises[i].getsScore() >= 0)
                Arms[i].setSelected(true);

            Button button = Arms[i];
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    button.setSelected(!button.isSelected());
                }
            });
        }

        Button update = findViewById(R.id.updatebtn);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mainURL = "https://instantworkoutsapi.herokuapp.com/users/updateScores";
                HashMap<String, String> uploadParams = new HashMap<>();
                String[] exerName = getResources().getStringArray(R.array.DBexerNames);
                uploadParams.put("username", username);

                for(int i = 0; i < ArmIDs.length; i++)
                {
                    if(!Arms[i].isSelected())
                        exercises[i].setsScore(-1);
                    else if (exercises[i].getsScore() == -1)
                    {
                        exercises[i].setsScore(0);
                    }
                    uploadParams.put(exerName[i], String.valueOf(exercises[i].getsScore()));
                }

                APIDataService service = new APIDataService(DesiredExercises.this);
                service.callAPIJSON(mainURL, uploadParams, Request.Method.PUT, responseUpload -> {
                    update.setEnabled(true);
                    try {
                        String success = responseUpload.getString("success");
                        if (success.equals("true")) {
                            Toast.makeText(DesiredExercises.this, "Scores Updated Successful", Toast.LENGTH_SHORT).show();
                            Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                            startIntent.putExtra("Training", ts);
                            startIntent.putExtra("Exercises", exercises);
                            startActivity(startIntent);
                        } else {
                            Toast.makeText(DesiredExercises.this, "Error updating scores", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                });
            }
        });
    }
}
