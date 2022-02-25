package com.example.instantworkout;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Workout implements Serializable {
    private List<Exercise> m_ExerciseList;
    private int m_Current;
    private int m_Count;

    public Workout()
    {
        m_ExerciseList = null;
        m_Current = 0;
        m_Count = 0;
    }

    public int get_Current() {
        return m_Current;
    }

    public void set_Current(int Current) {
        this.m_Current = Current;
    }

    public int getM_Count() {
        return m_Count;
    }

    public void setM_Count(int m_Count) {
        this.m_Count = m_Count;
    }
}
