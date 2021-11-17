package com.vrctech.aproundup.activities.mla;

public class Mlas {

    private String mlaPhotoUrl;
    private String mlaName;
    private String mlaAge;
    private String mlaQualification;
    private String mlaMajority;
    private String mlaConstituency;
    private String mlaDistrict;
    private String portfolio;

    public Mlas(String mlaPhotoUrl, String mlaName, String mlaAge, String mlaQualification, String mlaMajority, String mlaConstituency, String mlaDistrict, String portfolio) {
        this.mlaPhotoUrl = mlaPhotoUrl;
        this.mlaName = mlaName;
        this.mlaAge = mlaAge;
        this.mlaQualification = mlaQualification;
        this.mlaMajority = mlaMajority;
        this.mlaConstituency = mlaConstituency;
        this.mlaDistrict = mlaDistrict;
        this.portfolio = portfolio;
    }

    public String getMlaPhotoUrl() {
        return mlaPhotoUrl;
    }

    public void setMlaPhotoUrl(String mlaPhotoUrl) {
        this.mlaPhotoUrl = mlaPhotoUrl;
    }

    public String getMlaName() {
        return mlaName;
    }

    public void setMlaName(String mlaName) {
        this.mlaName = mlaName;
    }

    public String getMlaAge() {
        return mlaAge;
    }

    public void setMlaAge(String mlaAge) {
        this.mlaAge = mlaAge;
    }

    public String getMlaQualification() {
        return mlaQualification;
    }

    public void setMlaQualification(String mlaQualification) {
        this.mlaQualification = mlaQualification;
    }

    public String getMlaMajority() {
        return mlaMajority;
    }

    public void setMlaMajority(String mlaMajority) {
        this.mlaMajority = mlaMajority;
    }

    public String getMlaConstituency() {
        return mlaConstituency;
    }

    public void setMlaConstituency(String mlaConstituency) {
        this.mlaConstituency = mlaConstituency;
    }

    public String getMlaDistrict() {
        return mlaDistrict;
    }

    public void setMlaDistrict(String mlaDistrict) {
        this.mlaDistrict = mlaDistrict;
    }

    public String getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(String portfolio) {
        this.portfolio = portfolio;
    }
}
