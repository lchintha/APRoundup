package com.vrctech.aproundup.activities.epaper;

import java.io.Serializable;
import java.util.ArrayList;

public class Epaper implements Serializable {

    private int paperLogo;
    private int paperName;
    private String date;
    private ArrayList<Edition> editions;

    public Epaper(int paperLogo, int paperName, String date, ArrayList<Edition> editions) {
        this.paperLogo = paperLogo;
        this.paperName = paperName;
        this.date = date;
        this.editions = editions;
    }

    public int getPaperLogo() {
        return paperLogo;
    }

    public void setPaperLogo(int paperLogo) {
        this.paperLogo = paperLogo;
    }

    public int getPaperName() {
        return paperName;
    }

    public void setPaperName(int paperName) {
        this.paperName = paperName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Edition> getEditions() {
        return editions;
    }

    public void setEditions(ArrayList<Edition> editions) {
        this.editions = editions;
    }
}
