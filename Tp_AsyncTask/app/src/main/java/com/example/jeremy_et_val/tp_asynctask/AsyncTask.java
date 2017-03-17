package com.example.jeremy_et_val.tp_asynctask;

import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by jeremy on 10/03/17.
 */

public class AsyncTask extends android.os.AsyncTask<Object,Void,String> {
    String chaine, chaine2;
    BufferedReader in;
    URL url = null;
    TextView tv;

    @Override
    protected String doInBackground(Object... params) {
        chaine = (String) params[0];
        tv = (TextView) params[1];

        try {
                url = new URL(chaine);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream() ) );
                    chaine2 = in.readLine();
                    in.close();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return chaine2;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        tv.setText(result);
    }
}
