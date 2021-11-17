package com.vrctech.aproundup.activities.covid19;

public class CovidCase {

    private String name;
    private String active;
    private String deceased;
    private String recovered;
    private String total;

    public CovidCase(String name, String active, String deceased, String recovered, String total) {
        this.name = name;
        this.active = active;
        this.deceased = deceased;
        this.recovered = recovered;
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getDeceased() {
        return deceased;
    }

    public void setDeceased(String deceased) {
        this.deceased = deceased;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
