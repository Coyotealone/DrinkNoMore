package com.coyote.drinknomore;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Coyote on 22/01/15.
 */
public class WebService extends AsyncTask<String, Void, Void>{

    Activity context;

    public WebService(Activity context)
    {
        this.context = context;
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

    protected Void doInBackground(String... params)
    {
        URL u = null;
        InputStream is = null;

        try {
            u = new URL(params[0]);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(u.openStream()));
            //is = u.openStream();
            HttpURLConnection huc = (HttpURLConnection)u.openConnection();//to know the size of video
            int size = huc.getContentLength();

            if(huc != null){
                String fileName = "FILE.mp4";
                String storagePath = Environment.getExternalStorageDirectory().toString();
                File f = new File(storagePath,fileName);

                FileOutputStream fos = new FileOutputStream(f);
                byte[] buffer = new byte[1024];
                int len1 = 0;
                if(is != null){
                    while ((len1 = is.read(buffer)) > 0) {
                        fos.write(buffer,0, len1);
                    }
                }
                if(fos != null){
                    fos.close();
                }
            }
        }catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if(is != null){
                    is.close();
                }
            }catch (IOException ioe) {
                // just going to ignore this one
            }
        }
        return null;
        /*URL url = null;
        try {
            url = new URL("bim");
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            try {
                urlConn.setDoOutput(true); urlConn.setChunkedStreamingMode(0);
                OutputStream out = new BufferedOutputStream( urlConn.getOutputStream() );
                //writeStream(out);
                InputStream in = new BufferedInputStream( urlConn.getInputStream() );
                //readStream(in) ;
            }
            finally {
                urlConn.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // return a T
        return null;*/
    }
    protected void onProgressUpdate(Void... progress) { }
    protected void onPostExecute(Void ret)
    {
        // Make your UI update
    }
}
