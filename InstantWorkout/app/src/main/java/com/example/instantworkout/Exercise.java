package com.example.instantworkout;

import java.io.Serializable;

public class Exercise implements Serializable {
    private int m_Intensity;
    private int m_eType;
    private int m_sScore;
    private boolean m_pass;
    private boolean m_skip;

    public Exercise()
    {
        m_Intensity = 0;
        m_eType = 0;
        m_sScore = 0;
        m_pass = false;
        m_skip = false;
    }

    public Exercise(int Intense, int eType, int sScore, boolean pass,boolean skip)
    {
        m_Intensity = Intense;
        m_eType = eType;
        m_sScore = sScore;
        m_pass = pass;
        m_skip = skip;
    }

    public int geteType() {
        return m_eType;
    }
    public void seteType(int eType) {
        m_eType = eType;
    }

    public int getsScore() {
        return m_sScore;
    }

    public void setsScore(int sScore) {
        m_sScore = sScore;
    }

    public boolean ispass() {
        return m_pass;
    }

    public void setpass(boolean pass) {
        m_pass = pass;
    }

    public int getIntensity() {
        return m_Intensity;
    }

    public void setIntensity(int Intensity) {
        m_Intensity = Intensity;
    }

    public boolean isskip() {
        return m_skip;
    }
    public void setskip(boolean skip) {
        m_skip = skip;
    }
}
