package com.vrctech.aproundup.activities.epaper;

import java.io.Serializable;
import java.util.ArrayList;

public class Edition implements Serializable {

    private int name;
    private String id;
    private int icon;
    private String date;
    private ArrayList<String> pages;

    public Edition(int name, String id, int icon, String date, ArrayList<String> pages) {
        this.name = name;
        this.id = id;
        this.icon = icon;
        this.date = date;
        this.pages = pages;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<String> getPages() {
        return pages;
    }

    public void setPages(ArrayList<String> pages) {
        this.pages = pages;
    }
}
