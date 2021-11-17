package com.vrctech.aproundup.activities.epaper;

public interface PaperLoadImpl {

    void onPaperDownloadStart();
    void onPaperDownloadProgress(int progress);
    void onPaperDownloadFinish();

}
