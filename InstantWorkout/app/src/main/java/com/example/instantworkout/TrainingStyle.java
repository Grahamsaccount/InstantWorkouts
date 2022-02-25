package com.example.instantworkout;
import java.io.Serializable;

public class TrainingStyle implements Serializable {
    private int m_TrainingStyleID;
    private String m_TrainingStyle;
    private int m_Cycle;

    public TrainingStyle()
    {

    }

    public int get_TrainingStyleID() {
        return m_TrainingStyleID;
    }

    public void set_TrainingStyleID(int m_TrainingStyleID) {
        this.m_TrainingStyleID = m_TrainingStyleID;
    }

    public String get_TrainingStyle() {
        return m_TrainingStyle;
    }

    public void set_TrainingStyle(String m_TrainingStyle) {
        this.m_TrainingStyle = m_TrainingStyle;
    }

    public int get_Cycle() {
        return m_Cycle;
    }

    public void set_Cycle(int m_Cycle) {
        this.m_Cycle = m_Cycle;
    }
}
