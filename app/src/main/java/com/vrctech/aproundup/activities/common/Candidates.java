package com.vrctech.aproundup.activities.common;

public class Candidates {

    private String name;
    private String age;
    private String qualification;
    private String majority;
    private String party;
    private String photo;
    private String status;
    private String voteShare;
    private String votes;
    private String portfolio;

    public Candidates(String name, String age, String qualification, String majority, String party, String photo, String status, String voteShare, String votes, String portfolio) {
        this.name = name;
        this.age = age;
        this.qualification = qualification;
        this.majority = majority;
        this.party = party;
        this.photo = photo;
        this.status = status;
        this.voteShare = voteShare;
        this.votes = votes;
        this.portfolio = portfolio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getMajority() {
        return majority;
    }

    public void setMajority(String majority) {
        this.majority = majority;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVoteShare() {
        return voteShare;
    }

    public void setVoteShare(String voteShare) {
        this.voteShare = voteShare;
    }

    public String getVotes() {
        return votes;
    }

    public void setVotes(String votes) {
        this.votes = votes;
    }

    public String getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(String portfolio) {
        this.portfolio = portfolio;
    }
}
