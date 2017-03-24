package com.example.jeremy_et_val.tp_asynctask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private WebView webView_google;
    private Button button_date_heure;
    private Button button_html;
    private Button button_meteo_data;
    private Button button_google;
    private Button button_meteo_url;
    private TextView text_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView_google = (WebView) findViewById(R.id.webview_google);
        button_date_heure = (Button) findViewById(R.id.button_date_heure);
        button_html = (Button) findViewById(R.id.button_html);
        button_meteo_data = (Button) findViewById(R.id.button_meteo_data);
        button_google = (Button) findViewById(R.id.button_google);
        button_meteo_url = (Button) findViewById(R.id.button_meteo_url);
        text_time = (TextView) findViewById(R.id.text_time);

        button_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView_google.loadUrl("http://www.google.fr/");
                webView_google.setWebViewClient(new WebViewClient());
            }
        });

        button_html.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strHtml = "<html><body><b> Ceci est un texte au format HTML</b></br>qui s’affiche très simplement</body></html>";
                webView_google.loadData(strHtml , "text/html; charset=utf-8", "UTF-8");
            }
        });

        button_date_heure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask().execute("http://10.0.2.2:3402/time", text_time);
            }
        });

        button_meteo_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask().execute("http://api.openweathermap.org/data/2.5/forecast?q=Bedarieux,fr&APPID=910f0c05f62e5508a3428198252eed06&units=metric", webView_google);
            }
        });

        button_meteo_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask().execute("http://api.openweathermap.org/data/2.5/forecast?q=Bedarieux,fr&APPID=910f0c05f62e5508a3428198252eed06&units=metric", webView_google);
            }
        });
    }




}

