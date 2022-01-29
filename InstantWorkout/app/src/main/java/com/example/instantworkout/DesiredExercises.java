package com.example.instantworkout;

import android.content.Intent;
import android.graphics.drawable.Drawable;
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

public class DesiredExercises extends AppCompatActivity {

    private static final int[] ArmIDs = {R.id.arms0, R.id.arms1, R.id.arms2, R.id.arms3};

    Exercise[] exercises = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_desired_exercises);

        exercises = (Exercise[]) getIntent().getSerializableExtra("Exercises");

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
                for(int i = 0; i < ArmIDs.length; i++)
                {
                    if(!Arms[i].isSelected())
                        exercises[i].setsScore(-1);
                    else if (exercises[i].getsScore() == -1)
                    {
                        exercises[i].setsScore(0);
                    }
                }
                String txt = exercises[0].getsScore() + " " + exercises[1].getsScore() + " " + exercises[2].getsScore() + " " + exercises[3].getsScore();
                        Toast.makeText(DesiredExercises.this, txt, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
