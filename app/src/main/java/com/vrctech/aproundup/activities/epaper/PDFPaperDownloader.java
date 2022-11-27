package com.vrctech.aproundup.activities.epaper;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class PDFPaperDownloader extends AsyncTask<String, String, String>{

    static String EPAPER = "Epaper";

    private final PaperLoadImpl paperLoad;

    PDFPaperDownloader(PaperLoadImpl paperLoad) {
        this.paperLoad = paperLoad;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        paperLoad.onPaperDownloadStart();
    }

    @Override
    protected String doInBackground(String... strings) {
        int count;
        String fileUrl = strings[0];
        String fileName = strings[1];

        try {
            File folder = new File(((Context)paperLoad).getFilesDir(), EPAPER);
            if(!folder.exists()){
                folder.mkdir();
            }

            File pdfFile = new File(folder, fileName);
            pdfFile.createNewFile();

            URL url = new URL(fileUrl);
            URLConnection connection = url.openConnection();
            connection.connect();

            int lengthOfFile = connection.getContentLength();

            InputStream input = new BufferedInputStream(url.openStream(), 8192);
            OutputStream output = new FileOutputStream(pdfFile);

            byte[] data = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                publishProgress("" + (int) ((total * 100) / lengthOfFile));
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();

        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        paperLoad.onPaperDownloadProgress(Integer.parseInt(values[0]));
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        paperLoad.onPaperDownloadFinish();
    }
}
