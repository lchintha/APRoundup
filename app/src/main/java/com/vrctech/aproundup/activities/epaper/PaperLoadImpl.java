package com.vrctech.aproundup.activities.epaper;

public interface PaperLoadImpl {

    void onPageDownloadStart(int pageNumber);
    void onPageDownloadProgress(int progress);
    void onPageDownloadFinish(int pageNumber);

}
