package com.vrctech.aproundup.activities.mp;

public class Mps {

    private String mpPhotoUrl;
    private String mpName;
    private String mpAge;
    private String mpQualification;
    private String mpMajority;
    private String mpConstituency;
    private String district;

    public Mps(String mpPhotoUrl, String mpName, String mpAge, String mpQualification, String mpMajority, String mpConstituency, String district) {
        this.mpPhotoUrl = mpPhotoUrl;
        this.mpName = mpName;
        this.mpAge = mpAge;
        this.mpQualification = mpQualification;
        this.mpMajority = mpMajority;
        this.mpConstituency = mpConstituency;
        this.district = district;
    }

    public String getMpPhotoUrl() {
        return mpPhotoUrl;
    }

    public void setMpPhotoUrl(String mpPhotoUrl) {
        this.mpPhotoUrl = mpPhotoUrl;
    }

    public String getMpName() {
        return mpName;
    }

    public void setMpName(String mpName) {
        this.mpName = mpName;
    }

    public String getMpAge() {
        return mpAge;
    }

    public void setMpAge(String mpAge) {
        this.mpAge = mpAge;
    }

    public String getMpQualification() {
        return mpQualification;
    }

    public void setMpQualification(String mpQualification) {
        this.mpQualification = mpQualification;
    }

    public String getMpMajority() {
        return mpMajority;
    }

    public void setMpMajority(String mpMajority) {
        this.mpMajority = mpMajority;
    }

    public String getMpConstituency() {
        return mpConstituency;
    }

    public void setMpConstituency(String mpConstituency) {
        this.mpConstituency = mpConstituency;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
