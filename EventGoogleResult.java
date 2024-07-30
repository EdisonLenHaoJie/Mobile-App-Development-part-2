package com.fit2081.assignment12081;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class EventGoogleResult extends AppCompatActivity {


    WebView webView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_google_result);

        WebView webView = findViewById(R.id.googlesearch);

        String EventName = getIntent().getExtras().getString("eventName");


        String Searchpage =  "https://www.google.com/search?q=" + EventName;


        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(Searchpage);



    }
}