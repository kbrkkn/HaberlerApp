package com.example.kubra.haberlerapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        WebView webView= (WebView) findViewById(R.id.webView);
        String url=getIntent().getStringExtra("url");
        webView.setWebViewClient(new WebViewClient());//bu olmadığı zaman link browserda açılıyor.
        webView.loadUrl(url);
    }
}
