package com.example.instantworkout;

import java.io.Serializable;

public class Exercise implements Serializable {
    private Intensity m_Intensity;
    private boolean m_eType;
    private StrengthScore m_sScore;
    private boolean m_pass;
    private boolean m_skip;

    public Exercise()
    {
        m_Intensity = new Intensity();
        m_eType = false;
        m_sScore = new StrengthScore();
        m_pass = false;
        m_skip = false;
    }

    public Exercise(int Intense, boolean eType, int sScore, boolean pass,boolean skip, int offset, boolean type)
    {
        m_Intensity = new Intensity(Intense,sScore,type);
        m_eType = eType;
        m_sScore = new StrengthScore(sScore, offset);
        m_pass = pass;
        m_skip = skip;
    }

    public boolean geteType() {
        return m_eType;
    }
    public void seteType(boolean eType) {
        m_eType = eType;
    }

    public void setsOffset(int offset) {m_sScore.set_ExName(offset);}
    public int getsOffset() {return  m_sScore.get_ExName();}

    public int getsScore() {
        return m_sScore.get_Score();
    }

    public void setsScore(int sScore) {
        m_sScore.set_Score(sScore);
    }

    public boolean ispass() {
        return m_pass;
    }

    public void setpass(boolean pass) {
        m_pass = pass;
    }

    public Intensity getIntensity() {
        return m_Intensity;
    }

    public boolean isskip() {
        return m_skip;
    }
    public void setskip(boolean skip) {
        m_skip = skip;
    }
}
