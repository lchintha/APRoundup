package com.vrctech.aproundup.activities.epaper;

import java.io.Serializable;

public class PaperKey implements Serializable {

    private String pageNumber;
    private String pageKey;

    PaperKey(String pageNumber, String pageKey) {
        this.pageNumber = pageNumber;
        this.pageKey = pageKey;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getPageKey() {
        return pageKey;
    }

    public void setPageKey(String pageKey) {
        this.pageKey = pageKey;
    }
}
