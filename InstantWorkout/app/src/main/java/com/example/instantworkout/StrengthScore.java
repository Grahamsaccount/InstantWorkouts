package com.example.instantworkout;

import java.io.Serializable;

public class StrengthScore implements Serializable {
    private int m_Score;
    private int m_ExName;

    public StrengthScore()
    {
        m_Score = 0;
        m_ExName = 0;
    }
    public StrengthScore(int s, int e)
    {
        m_Score = s;
        m_ExName = e;
    }

    public int get_Score() {
        return m_Score;
    }

    public void set_Score(int Score) {
        this.m_Score = Score;
    }

    public int get_ExName() {
        return m_ExName;
    }

    public void set_ExName(int ExName) {
        this.m_ExName = ExName;
    }
}
