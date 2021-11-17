package com.vrctech.aproundup.activities.epaper;

import java.io.Serializable;

public class Edition implements Serializable {

    private int name;
    private String id;
    private int icon;
    private String date;

    public Edition(int name, String id, int icon, String date) {
        this.name = name;
        this.id = id;
        this.icon = icon;
        this.date = date;
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
}
