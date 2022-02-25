package com.example.instantworkout;

import java.io.Serializable;

public class Intensity implements Serializable {
    private boolean m_Style;
    private int m_Reps;
    private int m_Sets;
    private int m_Time;

    public Intensity()
    {
        m_Style = false;
        m_Reps = 0;
        m_Sets = 0;
        m_Time = 0;
    }
    public Intensity(int hOrL, int sScore, boolean type)
    {
        m_Style = type;
        double deff = 1;
        if(hOrL == 0)
        {
            //max reps 12 rep count 70%
            deff = 0.7;
            m_Reps = 12;
            m_Sets = 4;
        }
        else if(hOrL == 1)
        {
            // 10 rep 75%
            deff = 0.75;
            m_Reps = 10;
            m_Sets = 3;
        }
        else
        {
            //max weight 4 rep 90%
            deff = 0.9;
            m_Reps = 4;
            m_Sets = 4;
        }
        m_Time = (int) (sScore*deff);
    }

    public boolean get_Style() {
        return m_Style;
    }

    public void set_Style(boolean Style) {
        this.m_Style = Style;
    }

    public int get_Reps() {
        return m_Reps;
    }

    public void set_Reps(int Reps) {
        this.m_Reps = Reps;
    }

    public int get_Sets() {
        return m_Sets;
    }

    public void set_Sets(int Sets) {
        this.m_Sets = Sets;
    }

    public int get_Time() {
        return m_Time;
    }

    public void set_Time(int Time) {
        this.m_Time = Time;
    }
}
